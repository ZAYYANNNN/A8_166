package com.example.finalproject.ui.ViewModel.BangunanVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalproject.Model.Bangunan
import com.example.finalproject.Repository.BangunanRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeBgnUiState {
    data class Success(val bangunan: List<Bangunan>) : HomeBgnUiState()
    object  Error : HomeBgnUiState()
    object Loading : HomeBgnUiState()
}

class HomeBangunanVM (private val bgn: BangunanRepository): ViewModel(){
    var bgnUIState: HomeBgnUiState by mutableStateOf(HomeBgnUiState.Loading)
        private set

    init {
        getBgn()
    }

    fun getBgn(){
        viewModelScope.launch {
            bgnUIState = HomeBgnUiState.Loading
            bgnUIState = try {
                HomeBgnUiState.Success(bgn.getBangunan().data)
            }catch (e:IOException){
                HomeBgnUiState.Error
            }catch (e: HttpException){
                HomeBgnUiState.Error
            }
        }
    }
    fun deleteBgn(idBangunan:String){
        viewModelScope.launch {
            try {
                bgn.deleteBangunan(idBangunan)
            }catch (e: IOException){
                HomeBgnUiState.Error
            }catch (e:HttpException){
                HomeBgnUiState.Error
            }
        }
    }
}
