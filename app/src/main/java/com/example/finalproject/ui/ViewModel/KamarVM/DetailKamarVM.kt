package com.example.finalproject.ui.ViewModel.KamarVM

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Kamar
import com.example.finalproject.Repository.KamarRepository
import com.example.finalproject.ui.View.KmrView.DestinasiDetailKmr
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailKmrUiState {
    data class Success(val kamar: Kamar) : DetailKmrUiState()
    object Error : DetailKmrUiState()
    object Loading : DetailKmrUiState()
}
class DetailKamarVM(
    savedStateHandle: SavedStateHandle,
    private val kmr: KamarRepository
) : ViewModel() {
    private val _idKamar: String = checkNotNull(savedStateHandle[DestinasiDetailKmr.IDKmr])
    // StateFlow untuk menyimpan status UI
    private val _detailKmrUiState = MutableStateFlow<DetailKmrUiState>(DetailKmrUiState.Loading)
    val detailKmrUiState: StateFlow<DetailKmrUiState> = _detailKmrUiState

    init {
        getDetailKamar()
    }
    fun getDetailKamar() {
        viewModelScope.launch {
            try {
                // Set loading state
                _detailKmrUiState.value = DetailKmrUiState.Loading
                // Fetch mahasiswa data dari repository
                val kamar = kmr.getKamarbyid(_idKamar)
                if (kamar != null) {
                    // Jika data ditemukan, emit sukses
                    _detailKmrUiState.value = DetailKmrUiState.Success(kamar)
                } else {
                    // Jika data tidak ditemukan, emit error
                    _detailKmrUiState.value = DetailKmrUiState.Error
                }
            } catch (e: Exception) {
                // Emit error jika terjadi exception
                _detailKmrUiState.value = DetailKmrUiState.Error
            }
        }
    }
}
//memindahkan data dari entity ke ui
fun Kamar.toDetailKmrUiEvent(): KamarUiEvent {
    return KamarUiEvent(
        idKamar = idKamar,
        noKamar = noKamar,
        kapasitas = kapasitas,
        statusKamar= statusKamar,
        idBangunan = idBangunan,
    )
}