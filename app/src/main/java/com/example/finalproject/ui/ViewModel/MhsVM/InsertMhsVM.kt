package com.example.finalproject.ui.ViewModel.MhsVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Mahasiswa
import com.example.finalproject.Repository.MahasiswaRepository
import kotlinx.coroutines.launch

class InsertMhsVM(private val mhs: MahasiswaRepository) : ViewModel(){

    var uiState by mutableStateOf(MahasiswaUiState())
        private set


    fun updateInsertMhsState(mahasiswaUiEvent: MahasiswaUiEvent) {
        uiState = MahasiswaUiState(mahasiswaUiEvent = mahasiswaUiEvent)
    }


    suspend fun insertMhs() {
        viewModelScope.launch {
            try {
                mhs.insertMahasiswa(uiState.mahasiswaUiEvent.toMhs())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class MahasiswaUiState(
    val mahasiswaUiEvent:MahasiswaUiEvent = MahasiswaUiEvent()
)

data class MahasiswaUiEvent(
    val nim:String = "",
    val nama:String = "",
    val idMahasiswa:String = "",
    val email:String = "",
    val telp:String = "",
    val idKamar:String = "",
)

fun MahasiswaUiEvent.toMhs():Mahasiswa = Mahasiswa(
    idMahasiswa = idMahasiswa,
    nama = nama,
    nim = nim,
    email = email,
    telp = telp,
    idKamar = idKamar
)

fun  Mahasiswa.toUiStateMhs():MahasiswaUiState = MahasiswaUiState(
    mahasiswaUiEvent = toMahasiswaUiEvent()
)

fun Mahasiswa.toMahasiswaUiEvent():MahasiswaUiEvent = MahasiswaUiEvent(
    idMahasiswa = idMahasiswa,
    nama = nama,
    nim = nim,
    email = email,
    telp = telp,
    idKamar = idKamar
)