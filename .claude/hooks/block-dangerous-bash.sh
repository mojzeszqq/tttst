#!/usr/bin/env bash
# PreToolUse hook for Bash: blocks the same commands as the permissions.deny
# rules in .claude/settings.json, but also catches forms the prefix rules
# miss (rm -r -f, chained commands, sudo after a pipe, dd of=/dev/sda).
# Exit 2 blocks the tool call and shows stderr to Claude. Needs only bash, grep, sed, tr.

input=$(cat)

# Pull the "command" string out of the tool_input JSON (best effort, no jq).
cmd=$(printf '%s' "$input" | sed -E 's/.*"command"[[:space:]]*:[[:space:]]*"(([^"\\]|\\.)*)".*/\1/')
[ -z "$cmd" ] && exit 0

block() { echo "Blocked by .claude/hooks/block-dangerous-bash.sh: $1" >&2; exit 2; }

start='(^|[;&|(`[:space:]])'
disk='/dev/(sd|hd|vd|xvd|nvme|disk|mmcblk)'

# rm: check each command segment (split on ; & | and newlines) for both
# a recursive flag and a force flag, in any order or combination.
while IFS= read -r seg; do
  if printf '%s' "$seg" | grep -Eq "${start}rm([[:space:]]|$)"; then
    if printf '%s' "$seg" | grep -Eq '[[:space:]](-[a-zA-Z]*[rR][a-zA-Z]*|--recursive)([[:space:]]|$)' &&
       printf '%s' "$seg" | grep -Eq '[[:space:]](-[a-zA-Z]*f[a-zA-Z]*|--force)([[:space:]]|$)'; then
      block "recursive forced rm (rm -rf)"
    fi
  fi
done < <(printf '%s\n' "$cmd" | sed 's/\\n/\n/g' | tr ';&|' '\n\n\n')

printf '%s' "$cmd" | grep -Eq "${start}sudo([[:space:]]|$)" && block "sudo"
printf '%s' "$cmd" | grep -Eq "${start}chmod[[:space:]]+(-[a-zA-Z]+[[:space:]]+)*0?777([[:space:]]|$)" && block "chmod 777"
printf '%s' "$cmd" | grep -Eq "${start}ssh([[:space:]]|$)" && block "ssh"
printf '%s' "$cmd" | grep -Eq ">[[:space:]]*${disk}" && block "redirect onto a disk device"
printf '%s' "$cmd" | grep -Eq "of=${disk}" && block "dd onto a disk device"

exit 0
