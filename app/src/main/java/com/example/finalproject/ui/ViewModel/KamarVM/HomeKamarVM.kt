package com.example.finalproject.ui.ViewModel.KamarVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalproject.Model.Kamar
import com.example.finalproject.Repository.KamarRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeKmrUiState {
    data class Success(val kamar: List<Kamar>) : HomeKmrUiState()
    object  Error : HomeKmrUiState()
    object Loading : HomeKmrUiState()
}

class HomeKamarVM (private val kmr: KamarRepository): ViewModel(){
    var kmrUIState: HomeKmrUiState by mutableStateOf(HomeKmrUiState.Loading)
        private set

    init {
        getKmr()
    }
    fun getKmr(){
        viewModelScope.launch {
            kmrUIState = HomeKmrUiState.Loading
            kmrUIState = try {
                HomeKmrUiState.Success(kmr.getKamar().data)
            }catch (e:IOException){
                HomeKmrUiState.Error
            }catch (e: HttpException){
                HomeKmrUiState.Error
            }
        }
    }
    fun deleteKmr(idKamar:String){
        viewModelScope.launch {
            try {
                kmr.deleteKamar(idKamar)
            }catch (e: IOException){
                HomeKmrUiState.Error
            }catch (e:HttpException){
                HomeKmrUiState.Error
            }
        }
    }
}
