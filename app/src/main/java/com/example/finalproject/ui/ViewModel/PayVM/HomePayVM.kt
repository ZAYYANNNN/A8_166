package com.example.finalproject.ui.ViewModel.PayVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalproject.Model.Sewa
import com.example.finalproject.Repository.SewaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeSewaUiState {
    data class Success(val sewa: List<Sewa>) : HomeSewaUiState()
    object  Error : HomeSewaUiState()
    object Loading : HomeSewaUiState()
}

class HomePayVM (private val swa: SewaRepository): ViewModel(){
    var sewaUIState: HomeSewaUiState by mutableStateOf(HomeSewaUiState.Loading)
        private set

    init {
        getSwa()
    }
    fun getSwa(){
        viewModelScope.launch {
            sewaUIState = HomeSewaUiState.Loading
            sewaUIState = try {
                HomeSewaUiState.Success(swa.getPembayaran().data)
            }catch (e:IOException){
                HomeSewaUiState.Error
            }catch (e: HttpException){
                HomeSewaUiState.Error
            }
        }
    }
    fun deleteSewa(idPembayaran:String){
        viewModelScope.launch {
            try {
                swa.deletePembayaran(idPembayaran)
            }catch (e: IOException){
                HomeSewaUiState.Error
            }catch (e:HttpException){
                HomeSewaUiState.Error
            }
        }
    }
}
