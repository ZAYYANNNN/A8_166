package com.example.finalproject.ui.ViewModel.MhsVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalproject.Model.Mahasiswa
import com.example.finalproject.Repository.MahasiswaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeMhsUiState {
    data class Success(val mahasiswa: List<Mahasiswa>) : HomeMhsUiState()
    object  Error : HomeMhsUiState()
    object Loading : HomeMhsUiState()
}

class HomeMhsVM (private val mhs: MahasiswaRepository): ViewModel(){
    var mhsUIState: HomeMhsUiState by mutableStateOf(HomeMhsUiState.Loading)
        private set

    init {
        getMhs()
    }
    fun getMhs(){
        viewModelScope.launch {
            mhsUIState = HomeMhsUiState.Loading
            mhsUIState = try {
                HomeMhsUiState.Success(mhs.getMahasiswa().data)
            }catch (e:IOException){
                HomeMhsUiState.Error
            }catch (e: HttpException){
                HomeMhsUiState.Error
            }
        }
    }
    fun deleteMhs(idMahasiswa:String){
        viewModelScope.launch {
            try {
                mhs.deleteMahasiswa(idMahasiswa)
            }catch (e: IOException){
                HomeMhsUiState.Error
            }catch (e:HttpException){
                HomeMhsUiState.Error
            }
        }
    }
}
