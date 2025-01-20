package com.example.finalproject.ui.ViewModel.KamarVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Kamar
import com.example.finalproject.Repository.KamarRepository
import kotlinx.coroutines.launch

class InsertKamarVM(private val kmr: KamarRepository) : ViewModel(){

    var uiState by mutableStateOf(KamarUiState())
        private set


    fun updateInsertKmrState(kamarUiEvent: KamarUiEvent) {
        uiState = KamarUiState(kamarUiEvent = kamarUiEvent)
    }


    suspend fun insertKmr() {
        viewModelScope.launch {
            try {
                kmr.insertKamar(uiState.kamarUiEvent.toKmr())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class KamarUiState(
    val kamarUiEvent:KamarUiEvent = KamarUiEvent()
)

data class KamarUiEvent(
    val idKamar:String = "",
    val noKamar:String = "",
    val kapasitas:String = "",
    val statusKamar:String = "",
    val idBangunan:String = ""
)

fun KamarUiEvent.toKmr():Kamar = Kamar(
    idKamar = idKamar,
    noKamar = noKamar,
    kapasitas = kapasitas,
    statusKamar= statusKamar,
    idBangunan = idBangunan,
)

fun Kamar.toUiStateKmr():KamarUiState = KamarUiState(
    kamarUiEvent = toKamarUiEvent()
)

fun Kamar.toKamarUiEvent():KamarUiEvent = KamarUiEvent(
    idKamar = idKamar,
    noKamar = noKamar,
    kapasitas = kapasitas,
    statusKamar= statusKamar,
    idBangunan = idBangunan,
)