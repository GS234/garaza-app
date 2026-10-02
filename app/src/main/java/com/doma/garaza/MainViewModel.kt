package com.doma.garaza

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.doma.garaza.data.DeviceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

sealed class RequestState {
    object Idle : RequestState()
    object Sending : RequestState()
    object Success : RequestState()
    data class Error(val message: String) : RequestState()
}

sealed class UiEvent{
    data class ShowSnackbar(val message: String): UiEvent()
}
class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = DeviceRepository(application)
    private val _state = MutableStateFlow<RequestState>(RequestState.Idle)
    val state: StateFlow<RequestState> = _state

    private var currentJob: Job? = null

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    fun sendRequest() {
        currentJob?.cancel()
//        if(currentJob?.isActive == true) return
        currentJob = viewModelScope.launch {
            _state.value = RequestState.Sending
            try {
                withTimeout(2_000) {
                    repository.sendToggleRequest()
                }
                _state.value = RequestState.Success
                _events.emit(UiEvent.ShowSnackbar("poslano"))
                delay(2_000)
                _state.value = RequestState.Idle
            } catch (e: TimeoutCancellationException) {
                _state.value = RequestState.Error("Timeout")
                _events.emit(UiEvent.ShowSnackbar("napaka"))
            } catch (e: Exception) {
                _state.value = RequestState.Error("Network error")
                _events.emit(UiEvent.ShowSnackbar("napaka"))
            }
            _state.value = RequestState.Idle
        }
    }
}