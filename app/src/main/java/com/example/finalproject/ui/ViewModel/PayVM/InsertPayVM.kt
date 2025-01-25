package com.example.finalproject.ui.ViewModel.PayVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Mahasiswa
import com.example.finalproject.Model.Sewa
import com.example.finalproject.Repository.MahasiswaRepository
import com.example.finalproject.Repository.SewaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InsertPayVM(
    private val swa: SewaRepository,
    private val mhs: MahasiswaRepository
) : ViewModel(){

    var uiState by mutableStateOf(SewaUiState())
        private set

    // StateFlow untuk menyimpan daftar kamar yang diambil dari repository
    private val _mahasiswaList = MutableStateFlow<List<Mahasiswa>>(emptyList())
    val mahasiswaList: StateFlow<List<Mahasiswa>> get() = _mahasiswaList

    fun loadMahasiswaList() {
        viewModelScope.launch {
            try {
                val response = mhs.getMahasiswa()
                _mahasiswaList.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun updateInsertSwaState(sewaUiEvent: SewaUiEvent) {
        uiState = uiState.copy(sewaUiEvent = sewaUiEvent)
    }


    fun insertSwa() {
        viewModelScope.launch {
            try {
                val sewa = uiState.sewaUiEvent.toSwa()
                swa.insertPembayaran(sewa)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class SewaUiState(
    val sewaUiEvent: SewaUiEvent = SewaUiEvent(),
    val mahasiswaList: List<Mahasiswa> = emptyList()
)

data class SewaUiEvent(
    val idPembayaran:String = "",
    val idMahasiswa:String = "",
    val jumlah:String = "",
    val tglPembayaran:String = "",
    val statusPembayaran:String = "",
)

fun SewaUiEvent.toSwa(): Sewa = Sewa(
    idPembayaran = idPembayaran,
    idMahasiswa = idMahasiswa,
    jumlah = jumlah,
    tglPembayaran = tglPembayaran,
    statusPembayaran = statusPembayaran
)
