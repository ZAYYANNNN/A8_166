package com.example.finalproject.ui.ViewModel.KamarVM

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Bangunan
import com.example.finalproject.Model.Kamar
import com.example.finalproject.Repository.BangunanRepository
import com.example.finalproject.Repository.KamarRepository
import com.example.finalproject.ui.View.KmrView.DestinasiUpdateKmr
import kotlinx.coroutines.launch

class UpdateKamarVM(
    savedStateHandle: SavedStateHandle,
    private val kmr: KamarRepository,
    private val bangunanRepository: BangunanRepository // Menambahkan dependensi untuk repository Bangunan
) : ViewModel() {

    val idKamar: String = checkNotNull(savedStateHandle[DestinasiUpdateKmr.IDKmr])
    var uiState = mutableStateOf(KamarUiState())
        private set

    var bangunanList = mutableStateOf<List<Bangunan>>(emptyList()) // Menambahkan bangunanList

    init {
        ambilKamar()
        loadBangunanList() // Memuat data bangunan saat ViewModel diinisialisasi
    }

    // Fetch data kamar berdasarkan ID
    private fun ambilKamar() {
        viewModelScope.launch {
            try {
                val kamar = kmr.getKamarbyid(idKamar)
                kamar?.let {
                    uiState.value = it.toKamarUIEvent() // Update UI dengan data kamar yang diambil
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fetch data bangunan untuk bangunanList
    private fun loadBangunanList() {
        viewModelScope.launch {
            try {
                val response = bangunanRepository.getBangunan()
                bangunanList.value = response.data // Update bangunanList dengan data dari repository
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk update data kamar
    fun updateKmr(idKamar: String, kamar: Kamar) {
        viewModelScope.launch {
            try {
                kmr.updateKamar(idKamar, kamar)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk update state UI dengan event kamar yang baru
    fun updateKmrState(kamarUiEvent: KamarUiEvent) {
        uiState.value = uiState.value.copy(kamarUiEvent = kamarUiEvent)
    }
}

// Extension function untuk konversi dari Kamar ke KamarUiState
fun Kamar.toKamarUIEvent(): KamarUiState = KamarUiState(
    kamarUiEvent = this.toDetailKmrUiEvent()
)
