package com.example.finalproject.ui.ViewModel.MhsVM

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Mahasiswa
import com.example.finalproject.Repository.MahasiswaRepository
import com.example.finalproject.ui.View.MhsView.DestinasiUpdateMhs
import kotlinx.coroutines.launch

class UpdateMhsVM(
    savedStateHandle: SavedStateHandle,
    private val mhs: MahasiswaRepository
) : ViewModel() {

    val idMahasiswa: String = checkNotNull(savedStateHandle[DestinasiUpdateMhs.IDMhs])
    var uiState = mutableStateOf(MahasiswaUiState())
        private set
    init {
        ambilMahasiswa()
    }
    // Fetch the student data using NIM
    private fun ambilMahasiswa() {
        viewModelScope.launch {
            try {
                val mahasiswa = mhs.getMahasiswabyid(idMahasiswa)
                mahasiswa?.let {
                    uiState.value = it.toMahasiswaUIEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    // Update the mahasiswa information
    fun updateMhs(idMahasiswa: String, mahasiswa: Mahasiswa) {
        viewModelScope.launch {
            try {
                mhs.updateMahasiswa(idMahasiswa, mahasiswa)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    // Update the UI state with a new InsertUiEvent
    fun updateMhsState(mahasiswaUiEvent: MahasiswaUiEvent) {
        uiState.value = uiState.value.copy(mahasiswaUiEvent = mahasiswaUiEvent)
    }
}
fun Mahasiswa.toMahasiswaUIEvent(): MahasiswaUiState = MahasiswaUiState(
    mahasiswaUiEvent = this.toDetailMhsUiEvent()
)