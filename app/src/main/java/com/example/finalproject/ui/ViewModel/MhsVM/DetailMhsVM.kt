package com.example.finalproject.ui.ViewModel.MhsVM

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Mahasiswa
import com.example.finalproject.Repository.MahasiswaRepository
import com.example.finalproject.ui.View.MhsView.DestinasiDetailMhs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailMhsUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailMhsUiState()
    object Error : DetailMhsUiState()
    object Loading : DetailMhsUiState()
}
class DetailMhsVM(
    savedStateHandle: SavedStateHandle,
    private val mhs: MahasiswaRepository
) : ViewModel() {
    private val _idMahasiswa: String = checkNotNull(savedStateHandle[DestinasiDetailMhs.IDMhs])
    // StateFlow untuk menyimpan status UI
    private val _detailMhsUiState = MutableStateFlow<DetailMhsUiState>(DetailMhsUiState.Loading)
    val detailMhsUiState: StateFlow<DetailMhsUiState> = _detailMhsUiState
    init {
        getDetailMahasiswa()
    }
    fun getDetailMahasiswa() {
        viewModelScope.launch {
            try {
                // Set loading state
                _detailMhsUiState.value = DetailMhsUiState.Loading
                // Fetch mahasiswa data dari repository
                val mahasiswa = mhs.getMahasiswabyid(_idMahasiswa)
                if (mahasiswa != null) {
                    // Jika data ditemukan, emit sukses
                    _detailMhsUiState.value = DetailMhsUiState.Success(mahasiswa)
                } else {
                    // Jika data tidak ditemukan, emit error
                    _detailMhsUiState.value = DetailMhsUiState.Error
                }
            } catch (e: Exception) {
                // Emit error jika terjadi exception
                _detailMhsUiState.value = DetailMhsUiState.Error
            }
        }
    }
}
//memindahkan data dari entity ke ui
fun Mahasiswa.toDetailMhsUiEvent(): MahasiswaUiEvent {
    return MahasiswaUiEvent(
        idMahasiswa = idMahasiswa,
        nama = nama,
        nim = nim,
        email = email,
        telp = telp,
        idKamar = idKamar
    )
}