package com.example.finalproject.ui.ViewModel.PayVM

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Mahasiswa
import com.example.finalproject.Model.Sewa
import com.example.finalproject.Repository.MahasiswaRepository
import com.example.finalproject.Repository.SewaRepository
import com.example.finalproject.ui.View.PayView.DestinasiUpdatePay

import kotlinx.coroutines.launch

class UpdatePayVM(
    savedStateHandle: SavedStateHandle,
    private val swa: SewaRepository,
    private val mhs: MahasiswaRepository
) : ViewModel() {

    val idPembayaran: String = checkNotNull(savedStateHandle[DestinasiUpdatePay.IDPay])
    var uiState = mutableStateOf(SewaUiState())
        private set

    var mahasiswaList = mutableStateOf<List<Mahasiswa>>(emptyList())

    init {
        ambilSewa()
        loadMahasiswaList()
    }

    private fun ambilSewa() {
        viewModelScope.launch {
            try {
                val sewa = swa.getPembayaranbyid(idPembayaran)
                sewa?.let {
                    uiState.value = it.toSewaUIEvent()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun loadMahasiswaList() {
        viewModelScope.launch {
            try {
                val response = mhs.getMahasiswa()
                mahasiswaList.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateSewa(idPembayaran: String, sewa: Sewa) {
        viewModelScope.launch {
            try {
                swa.updatePembayaran(idPembayaran, sewa)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    // Update the UI state with a new InsertUiEvent
    fun updateSewaState(sewaUiEvent: SewaUiEvent) {
        uiState.value = uiState.value.copy(sewaUiEvent = sewaUiEvent)
    }
}
fun Sewa.toSewaUIEvent(): SewaUiState = SewaUiState(
    sewaUiEvent = this.toDetailSewaUiEvent()
)