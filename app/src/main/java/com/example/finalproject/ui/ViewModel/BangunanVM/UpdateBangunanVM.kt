package com.example.finalproject.ui.ViewModel.BangunanVM

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Bangunan
import com.example.finalproject.Repository.BangunanRepository
import com.example.finalproject.ui.View.BgnView.DestinasiUpdateBgn
import kotlinx.coroutines.launch

class UpdateBangunanVM(
    savedStateHandle: SavedStateHandle,
    private val bgn: BangunanRepository
) : ViewModel() {

    val idBangunan: String = checkNotNull(savedStateHandle[DestinasiUpdateBgn.IDBgn])
    var uiState = mutableStateOf(BangunanUiState())
        private set

    init {
        ambilBangunan()
    }
    // Fetch the student data using NIM
    private fun ambilBangunan() {
        viewModelScope.launch {
            try {
                val bangunan = bgn.getBangunanbyid(idBangunan)
                bangunan?.let {
                    uiState.value = it.toBangunanUIEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateBgn(idBangunan: String, bangunan: Bangunan) {
        viewModelScope.launch {
            try {
                bgn.updateBangunan(idBangunan, bangunan)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    // Update the UI state with a new InsertUiEvent
    fun updateBgnState(bangunanUiEvent: BangunanUiEvent) {
        uiState.value = uiState.value.copy(bangunanUiEvent = bangunanUiEvent)
    }
}
fun Bangunan.toBangunanUIEvent(): BangunanUiState = BangunanUiState(
    bangunanUiEvent = this.toDetailBgnUiEvent()
)