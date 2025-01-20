package com.example.finalproject.ui.ViewModel.KamarVM

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Kamar
import com.example.finalproject.Repository.KamarRepository
import kotlinx.coroutines.launch

class UpdateKamarVM(
    savedStateHandle: SavedStateHandle,
    private val kmr: KamarRepository
) : ViewModel() {

    val idKamar: String = checkNotNull(savedStateHandle[DestinasiUpdateKmr.IDMhs])
    var uiState = mutableStateOf(KamarUiState())
        private set

    init {
        ambilKamar()
    }
    // Fetch the student data using NIM
    private fun ambilKamar() {
        viewModelScope.launch {
            try {
                val kamar = kmr.getKamarbyid(idKamar)
                kamar?.let {
                    uiState.value = it.toKamarUIEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    // Update the mahasiswa information
    fun updateKmr(idKamar: String, kamar: Kamar) {
        viewModelScope.launch {
            try {
                kmr.updateKamar(idKamar, kamar)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    // Update the UI state with a new InsertUiEvent
    fun updateKmrState(kamarUiEvent: KamarUiEvent) {
        uiState.value = uiState.value.copy(kamarUiEvent = kamarUiEvent)
    }
}
fun Kamar.toKamarUIEvent(): KamarUiState = KamarUiState(
    kamarUiEvent = this.toDetailKmrUiEvent()
)