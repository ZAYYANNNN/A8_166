package com.example.finalproject.ui.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Mahasiswa
import com.example.finalproject.Repository.MahasiswaRepository
import kotlinx.coroutines.launch

class InsertMhsVM(private val mhs: MahasiswaRepository) : ViewModel(){

    var uiState by mutableStateOf(InsertUiState())
        private set


    fun updateInsertMhsState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }


    suspend fun insertMhs() {
        viewModelScope.launch {
            try {
                mhs.insertMahasiswa(uiState.insertUiEvent.toMhs())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent:InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val nim:String = "",
    val nama:String = "",
    val idMahasiswa:String = "",
    val email:String = "",
    val telp:String = "",
    val idKamar:String = "",
)

fun InsertUiEvent.toMhs():Mahasiswa = Mahasiswa(
    idMahasiswa = idMahasiswa,
    nama = nama,
    nim = nim,
    email = email,
    telp = telp,
    idKamar = idKamar
)

fun  Mahasiswa.toUiStateMhs():InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Mahasiswa.toInsertUiEvent():InsertUiEvent = InsertUiEvent(
    idMahasiswa = idMahasiswa,
    nama = nama,
    nim = nim,
    email = email,
    telp = telp,
    idKamar = idKamar
)