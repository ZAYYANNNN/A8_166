package com.example.finalproject.ui.ViewModel.BangunanVM

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Bangunan
import com.example.finalproject.Repository.BangunanRepository
import com.example.finalproject.ui.View.BgnView.DestinasiDetailBgn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailBgnUiState {
    data class Success(val bangunan: Bangunan) : DetailBgnUiState()
    object Error : DetailBgnUiState()
    object Loading : DetailBgnUiState()
}
class DetailBangunanVM(
    savedStateHandle: SavedStateHandle,
    private val bgn: BangunanRepository
) : ViewModel() {
    private val _idBangunan: String = checkNotNull(savedStateHandle[DestinasiDetailBgn.IDBgn])
    // StateFlow untuk menyimpan status UI
    private val _detailBgnUiState = MutableStateFlow<DetailBgnUiState>(DetailBgnUiState.Loading)
    val detailBgnUiState: StateFlow<DetailBgnUiState> = _detailBgnUiState

    init {
        getDetailBangunan()
    }
    fun getDetailBangunan() {
        viewModelScope.launch {
            try {
                _detailBgnUiState.value = DetailBgnUiState.Loading

                val bangunan = bgn.getBangunanbyid(_idBangunan)
                if (bangunan != null) {
                    // Jika data ditemukan, emit sukses
                    _detailBgnUiState.value = DetailBgnUiState.Success(bangunan)
                } else {
                    // Jika data tidak ditemukan, emit error
                    _detailBgnUiState.value = DetailBgnUiState.Error
                }
            } catch (e: Exception) {
                // Emit error jika terjadi exception
                _detailBgnUiState.value = DetailBgnUiState.Error
            }
        }
    }
}
//memindahkan data dari entity ke ui
fun Bangunan.toDetailBgnUiEvent(): BangunanUiEvent {
    return BangunanUiEvent(
        idBangunan = idBangunan,
        namaBangunan = namaBangunan,
        jumlahLantai = jumlahLantai,
        alamat = alamat
    )
}