package com.example.finalproject.ui.ViewModel.MhsVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Kamar
import com.example.finalproject.Model.Mahasiswa
import com.example.finalproject.Repository.KamarRepository
import com.example.finalproject.Repository.MahasiswaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InsertMhsVM(
    private val mhs: MahasiswaRepository,
    private val kmr: KamarRepository
) : ViewModel() {

    // State untuk menyimpan data mahasiswa
    var uiState by mutableStateOf(MahasiswaUiState())
        private set

    // StateFlow untuk menyimpan daftar kamar yang diambil dari repository
    private val _kamarList = MutableStateFlow<List<Kamar>>(emptyList())
    val kamarList: StateFlow<List<Kamar>> get() = _kamarList

    fun loadKamarList() {
        viewModelScope.launch {
            try {
                val response = kmr.getKamar()
                _kamarList.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk mengupdate state berdasarkan input dari UI
    fun updateInsertMhsState(mahasiswaUiEvent: MahasiswaUiEvent) {
        uiState = uiState.copy(mahasiswaUiEvent = mahasiswaUiEvent)
    }

    // Fungsi untuk menyimpan data mahasiswa ke repository
    fun insertMhs() {
        viewModelScope.launch {
            try {
                val mahasiswa = uiState.mahasiswaUiEvent.toMhs()
                mhs.insertMahasiswa(mahasiswa)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

// State class untuk UI
data class MahasiswaUiState(
    val mahasiswaUiEvent: MahasiswaUiEvent = MahasiswaUiEvent(),
    val kamarList: List<Kamar> = emptyList()
)
// Data class untuk event yang dikirim dari UI
data class MahasiswaUiEvent(
    val idMahasiswa: String = "",
    val nim: String = "",
    val nama: String = "",
    val email: String = "",
    val telp: String = "",
    val idKamar: String = ""
)

// Fungsi untuk mengonversi MahasiswaUiEvent ke entity Mahasiswa
fun MahasiswaUiEvent.toMhs(): Mahasiswa = Mahasiswa(
    idMahasiswa = idMahasiswa,
    nama = nama,
    nim = nim,
    email = email,
    telp = telp,
    idKamar = idKamar
)
