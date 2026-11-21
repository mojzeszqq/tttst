package com.xb88.rider.ui.garage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xb88.rider.domain.model.BikeProfile
import com.xb88.rider.domain.model.SpecField
import com.xb88.rider.domain.model.withValue
import com.xb88.rider.domain.repository.BikeRepository
import com.xb88.rider.domain.usecase.CounterInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GarageViewModel @Inject constructor(
    private val bikeRepository: BikeRepository,
) : ViewModel() {

    val bike: StateFlow<BikeProfile?> = bikeRepository.observeBike()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    /** Returns false (and saves nothing) if [raw] is not a valid value for [field]. */
    fun saveSpec(field: SpecField, raw: String): Boolean {
        val updated = bike.value?.withValue(field, raw) ?: return false
        viewModelScope.launch { bikeRepository.updateBike(updated) }
        return true
    }

    fun saveCounters(mthInput: String, kmInput: String): CounterInput.Result {
        val current = bike.value ?: return CounterInput.Result.InvalidMth
        val result = CounterInput.validate(mthInput, kmInput)
        if (result is CounterInput.Result.Valid) {
            viewModelScope.launch { bikeRepository.updateCounters(current.id, result.mth, result.km) }
        }
        return result
    }
}
