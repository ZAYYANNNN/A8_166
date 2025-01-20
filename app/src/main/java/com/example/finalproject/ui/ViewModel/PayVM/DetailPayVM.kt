package com.example.finalproject.ui.ViewModel.PayVM

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Sewa
import com.example.finalproject.Repository.SewaRepository
import com.example.finalproject.ui.View.PayView.DestinasiDetailPay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailSewaUiState {
    data class Success(val sewa: Sewa) : DetailSewaUiState()
    object Error : DetailSewaUiState()
    object Loading : DetailSewaUiState()
}
class DetailPayVM(
    savedStateHandle: SavedStateHandle,
    private val swa: SewaRepository
) : ViewModel() {
    private val _idPembayaran: String = checkNotNull(savedStateHandle[DestinasiDetailPay.IDPay])
    // StateFlow untuk menyimpan status UI
    private val _detailSewaUiState = MutableStateFlow<DetailSewaUiState>(DetailSewaUiState.Loading)
    val detailSewaUiState: StateFlow<DetailSewaUiState> = _detailSewaUiState

    init {
        getDetailPay()
    }
    fun getDetailPay() {
        viewModelScope.launch {
            try {
                // Set loading state
                _detailSewaUiState.value = DetailSewaUiState.Loading

                val sewa = swa.getPembayaranbyid(_idPembayaran)
                if (sewa != null) {
                    // Jika data ditemukan, emit sukses
                    _detailSewaUiState.value = DetailSewaUiState.Success(sewa)
                } else {
                    // Jika data tidak ditemukan, emit error
                    _detailSewaUiState.value = DetailSewaUiState.Error
                }
            } catch (e: Exception) {
                // Emit error jika terjadi exception
                _detailSewaUiState.value = DetailSewaUiState.Error
            }
        }
    }
}
//memindahkan data dari entity ke ui
fun Sewa.toDetailSewaUiEvent(): SewaUiEvent {
    return SewaUiEvent(
        idPembayaran = idPembayaran,
        idMahasiswa = idMahasiswa,
        jumlah = jumlah,
        tglPembayaran = tglPembayaran,
        statusPembayaran= statusPembayaran
    )
}