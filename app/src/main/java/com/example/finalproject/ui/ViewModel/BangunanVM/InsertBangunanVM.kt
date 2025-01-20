package com.example.finalproject.ui.ViewModel.BangunanVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Bangunan
import com.example.finalproject.Repository.BangunanRepository
import kotlinx.coroutines.launch

class InsertBangunanVM(private val bgn: BangunanRepository) : ViewModel(){

    var uiState by mutableStateOf(BangunanUiState())
        private set


    fun updateInsertBgnState(bangunanUiEvent: BangunanUiEvent) {
        uiState = BangunanUiState(bangunanUiEvent = bangunanUiEvent)
    }


    suspend fun insertBgn() {
        viewModelScope.launch {
            try {
                bgn.insertBangunan(uiState.bangunanUiEvent.toBgn())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class BangunanUiState(
    val bangunanUiEvent:BangunanUiEvent = BangunanUiEvent()
)

data class BangunanUiEvent(
    val idBangunan:String = "",
    val namaBangunan:String = "",
    val jumlahLantai:String = "",
    val alamat:String = ""
)

fun BangunanUiEvent.toBgn():Bangunan = Bangunan(
    idBangunan = idBangunan,
    namaBangunan = namaBangunan,
    jumlahLantai = jumlahLantai,
    alamat = alamat
)

fun Bangunan.toUiStateBgn(): BangunanUiState = BangunanUiState(
    bangunanUiEvent = toBangunanUiEvent()
)

fun Bangunan.toBangunanUiEvent(): BangunanUiEvent = BangunanUiEvent(
    idBangunan = idBangunan,
    namaBangunan = namaBangunan,
    jumlahLantai = jumlahLantai,
    alamat = alamat
)