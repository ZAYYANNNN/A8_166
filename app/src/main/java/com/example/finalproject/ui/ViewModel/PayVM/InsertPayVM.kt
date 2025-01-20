package com.example.finalproject.ui.ViewModel.PayVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Mahasiswa
import com.example.finalproject.Model.Sewa
import com.example.finalproject.Repository.SewaRepository
import kotlinx.coroutines.launch

class InsertPayVM(private val swa: SewaRepository) : ViewModel(){

    var uiState by mutableStateOf(SewaUiState())
        private set


    fun updateInsertSwaState(sewaUiEvent: SewaUiEvent) {
        uiState = SewaUiState(sewaUiEvent = sewaUiEvent)
    }


    suspend fun insertSwa() {
        viewModelScope.launch {
            try {
                swa.insertPembayaran(uiState.sewaUiEvent.toSwa())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class SewaUiState(
    val sewaUiEvent:SewaUiEvent = SewaUiEvent(),
    val mahasiswaList:List<Mahasiswa> = emptyList()
)

data class SewaUiEvent(
    val idPembayaran:String = "",
    val idMahasiswa:String = "",
    val jumlah:String = "",
    val tglPembayaran:String = "",
    val statusPembayaran:String = "",
)

fun SewaUiEvent.toSwa():Sewa = Sewa(
    idPembayaran = idPembayaran,
    idMahasiswa = idMahasiswa,
    jumlah = jumlah,
    tglPembayaran = tglPembayaran,
    statusPembayaran= statusPembayaran
)

fun Sewa.toUiStateSewa():SewaUiState = SewaUiState(
    sewaUiEvent = toSewaUiEvent()
)

fun Sewa.toSewaUiEvent(): SewaUiEvent = SewaUiEvent(
    idPembayaran = idPembayaran,
    idMahasiswa = idMahasiswa,
    jumlah = jumlah,
    tglPembayaran = tglPembayaran,
    statusPembayaran= statusPembayaran
)