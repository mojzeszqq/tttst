package com.xb88.rider.ui.garage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.xb88.rider.R
import com.xb88.rider.domain.model.formatDecimal
import com.xb88.rider.domain.model.parseDecimal
import com.xb88.rider.domain.usecase.CounterInput
import com.xb88.rider.ui.theme.Dimens
import java.text.NumberFormat
import java.util.Locale

private val polishNumber: NumberFormat =
    NumberFormat.getNumberInstance(Locale.forLanguageTag("pl-PL")).apply { maximumFractionDigits = 1 }

/** Polish display format: 1.3 -> "1,3", 12345.0 -> "12 345". */
fun formatPl(value: Double): String = polishNumber.format(value)

@Composable
fun SpecEditDialog(
    label: String,
    initialValue: String,
    isDecimal: Boolean,
    onDismiss: () -> Unit,
    /** Returns false if the value was rejected. */
    onSave: (String) -> Boolean,
) {
    var text by rememberSaveable { mutableStateOf(initialValue) }
    var invalid by rememberSaveable { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.dialog_edit_title, label)) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it; invalid = false },
                modifier = Modifier.fillMaxWidth(),
                singleLine = isDecimal,
                isError = invalid,
                supportingText = if (invalid) {
                    {
                        Text(
                            stringResource(
                                if (isDecimal) R.string.dialog_invalid_number else R.string.dialog_invalid_text,
                            ),
                        )
                    }
                } else {
                    null
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isDecimal) KeyboardType.Decimal else KeyboardType.Text,
                ),
            )
        },
        confirmButton = {
            Button(
                onClick = { invalid = !onSave(text) },
                modifier = Modifier.heightIn(min = Dimens.MinTouch),
            ) { Text(stringResource(R.string.dialog_save)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, modifier = Modifier.heightIn(min = Dimens.MinTouch)) {
                Text(stringResource(R.string.dialog_cancel))
            }
        },
    )
}

private val QUICK_ADD_MTH = listOf(0.5, 1.0, 2.0)

@Composable
fun CountersDialog(
    currentMth: Double,
    currentKm: Double,
    onDismiss: () -> Unit,
    onSave: (mth: String, km: String) -> CounterInput.Result,
) {
    var mth by rememberSaveable { mutableStateOf(formatDecimal(currentMth)) }
    var km by rememberSaveable { mutableStateOf(formatDecimal(currentKm)) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    val invalidMth = stringResource(R.string.mth_dialog_invalid_mth)
    val invalidKm = stringResource(R.string.mth_dialog_invalid_km)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.mth_dialog_title)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = mth,
                    onValueChange = { mth = it; error = null },
                    label = { Text(stringResource(R.string.garage_mth_label)) },
                    singleLine = true,
                    isError = error == invalidMth,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    textStyle = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(stringResource(R.string.mth_dialog_quick_add), style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    QUICK_ADD_MTH.forEach { delta ->
                        OutlinedButton(
                            onClick = {
                                val base = parseDecimal(mth) ?: currentMth
                                mth = formatDecimal(CounterInput.addMth(base, delta))
                                error = null
                            },
                            modifier = Modifier.weight(1f).heightIn(min = Dimens.MinTouch),
                        ) { Text(stringResource(R.string.mth_dialog_add, formatPl(delta))) }
                    }
                }
                OutlinedTextField(
                    value = km,
                    onValueChange = { km = it; error = null },
                    label = { Text(stringResource(R.string.garage_km_label)) },
                    singleLine = true,
                    isError = error == invalidKm,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                )
                error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    error = when (onSave(mth, km)) {
                        is CounterInput.Result.Valid -> null
                        CounterInput.Result.InvalidMth -> invalidMth
                        CounterInput.Result.InvalidKm -> invalidKm
                    }
                },
                modifier = Modifier.heightIn(min = Dimens.MinTouch),
            ) { Text(stringResource(R.string.dialog_save)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, modifier = Modifier.heightIn(min = Dimens.MinTouch)) {
                Text(stringResource(R.string.dialog_cancel))
            }
        },
    )
}
