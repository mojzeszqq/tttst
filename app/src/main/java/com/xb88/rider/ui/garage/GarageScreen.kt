package com.xb88.rider.ui.garage

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xb88.rider.R
import com.xb88.rider.domain.model.BikeProfile
import com.xb88.rider.domain.model.SpecField
import com.xb88.rider.domain.model.valueOf
import com.xb88.rider.domain.usecase.CounterInput
import com.xb88.rider.ui.theme.Dimens

@Composable
fun GarageScreen(viewModel: GarageViewModel = hiltViewModel()) {
    val bike by viewModel.bike.collectAsStateWithLifecycle()
    val current = bike
    if (current == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.garage_loading), style = MaterialTheme.typography.bodyLarge)
        }
        return
    }

    var editingField by rememberSaveable { mutableStateOf<SpecField?>(null) }
    var showCounters by rememberSaveable { mutableStateOf(false) }

    GarageContent(
        bike = current,
        onUpdateCounters = { showCounters = true },
        onEditField = { editingField = it },
    )

    editingField?.let { field ->
        SpecEditDialog(
            label = stringResource(field.labelRes()),
            initialValue = current.valueOf(field),
            isDecimal = field.kind == SpecField.Kind.DECIMAL,
            onDismiss = { editingField = null },
            onSave = { raw ->
                viewModel.saveSpec(field, raw).also { saved -> if (saved) editingField = null }
            },
        )
    }

    if (showCounters) {
        CountersDialog(
            currentMth = current.currentMth,
            currentKm = current.currentKm,
            onDismiss = { showCounters = false },
            onSave = { mth, km ->
                viewModel.saveCounters(mth, km).also { result ->
                    if (result is CounterInput.Result.Valid) {
                        showCounters = false
                    }
                }
            },
        )
    }
}

@Composable
private fun GarageContent(
    bike: BikeProfile,
    onUpdateCounters: () -> Unit,
    onEditField: (SpecField) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimens.ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.CardSpacing),
    ) {
        item { HeaderCard(bike, onUpdateCounters) }
        item { CheatSheetCard(bike) }
        item {
            Column(Modifier.padding(top = 8.dp)) {
                Text(stringResource(R.string.garage_specs_title), style = MaterialTheme.typography.titleLarge)
                Text(
                    stringResource(R.string.garage_specs_hint),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        items(SpecField.entries, key = { it.name }) { field ->
            SpecRow(
                label = stringResource(field.labelRes()),
                value = bike.valueOf(field).ifBlank { stringResource(R.string.spec_empty) },
                onClick = { onEditField(field) },
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
        }
    }
}

@Composable
private fun HeaderCard(bike: BikeProfile, onUpdateCounters: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
    ) {
        Column(Modifier.padding(Dimens.ScreenPadding)) {
            Text(bike.model, style = MaterialTheme.typography.headlineSmall)
            Text(
                "${bike.platformCode} • ${bike.power}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.heightIn(min = 12.dp))
            Row(Modifier.fillMaxWidth()) {
                CounterTile(
                    label = stringResource(R.string.garage_mth_label),
                    value = formatPl(bike.currentMth),
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(12.dp))
                CounterTile(
                    label = stringResource(R.string.garage_km_label),
                    value = formatPl(bike.currentKm),
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(Modifier.heightIn(min = 12.dp))
            Button(
                onClick = onUpdateCounters,
                modifier = Modifier.fillMaxWidth().heightIn(min = Dimens.PrimaryTouch),
            ) {
                Text(stringResource(R.string.garage_update_mth), style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
private fun CounterTile(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.displayMedium)
    }
}

@Composable
private fun CheatSheetCard(bike: BikeProfile) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
    ) {
        Column(Modifier.padding(Dimens.ScreenPadding), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.garage_cheat_sheet_title), style = MaterialTheme.typography.titleLarge)
            }
            Text(stringResource(R.string.garage_cheat_sheet_subtitle), style = MaterialTheme.typography.bodyMedium)

            CheatFuseRow(bike.mainFuse)
            CheatRow(R.string.cheat_battery, bike.batterySpec)
            CheatRow(R.string.cheat_oil, bike.oilSpec)
            CheatRow(R.string.cheat_tank, stringResource(R.string.cheat_tank_value, formatPl(bike.tankCapacityL)))
            CheatRow(
                R.string.cheat_tires_pressure,
                stringResource(
                    R.string.cheat_tires_pressure_value,
                    formatPl(bike.frontPressureBar),
                    formatPl(bike.rearPressureBar),
                ),
            )
            CheatRow(R.string.cheat_tire_sizes, stringResource(R.string.cheat_tire_sizes_value, bike.frontTire, bike.rearTire))
            CheatRow(R.string.cheat_chain, bike.chainSpec)
            CheatRow(R.string.cheat_carb, bike.carburetor)
        }
    }
}

/** The 10A main fuse is the most common trail-side failure — render it prominently. */
@Composable
private fun CheatFuseRow(fuse: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    ) {
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(stringResource(R.string.cheat_fuse), style = MaterialTheme.typography.titleMedium)
                Text(stringResource(R.string.cheat_fuse_hint), style = MaterialTheme.typography.bodyMedium)
            }
            Text(fuse, style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Composable
private fun CheatRow(@StringRes label: Int, value: String) {
    Column {
        Text(stringResource(label), style = MaterialTheme.typography.labelLarge)
        Text(value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SpecRow(label: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = Dimens.MinTouch)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, style = MaterialTheme.typography.bodyLarge)
        }
        Icon(Icons.Filled.Edit, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
    }
}

@StringRes
fun SpecField.labelRes(): Int = when (this) {
    SpecField.MODEL -> R.string.spec_model
    SpecField.PLATFORM_CODE -> R.string.spec_platform_code
    SpecField.ENGINE -> R.string.spec_engine
    SpecField.POWER -> R.string.spec_power
    SpecField.GEARBOX -> R.string.spec_gearbox
    SpecField.CARBURETOR -> R.string.spec_carburetor
    SpecField.IGNITION -> R.string.spec_ignition
    SpecField.OIL_SPEC -> R.string.spec_oil
    SpecField.TANK_CAPACITY_L -> R.string.spec_tank
    SpecField.ELECTRICAL_SYSTEM -> R.string.spec_electrical
    SpecField.BATTERY -> R.string.spec_battery
    SpecField.MAIN_FUSE -> R.string.spec_main_fuse
    SpecField.FRONT_TIRE -> R.string.spec_front_tire
    SpecField.REAR_TIRE -> R.string.spec_rear_tire
    SpecField.FRONT_PRESSURE_BAR -> R.string.spec_front_pressure
    SpecField.REAR_PRESSURE_BAR -> R.string.spec_rear_pressure
    SpecField.CHAIN -> R.string.spec_chain
    SpecField.DIMENSIONS -> R.string.spec_dimensions
    SpecField.VALVE_CLEARANCE -> R.string.spec_valve_clearance
    SpecField.NOTES -> R.string.spec_notes
}
