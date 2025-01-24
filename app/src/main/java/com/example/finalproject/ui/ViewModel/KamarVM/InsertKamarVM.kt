package com.example.finalproject.ui.ViewModel.KamarVM

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.Model.Bangunan
import com.example.finalproject.Model.Kamar
import com.example.finalproject.Repository.BangunanRepository
import com.example.finalproject.Repository.KamarRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// ViewModel untuk InsertKamar
class InsertKamarVM(
    private val kmr: KamarRepository,
    private val bangunanRepository: BangunanRepository
) : ViewModel() {

    var uiState by mutableStateOf(KamarUiState())
        private set

    // StateFlow untuk menyimpan list bangunan
    private val _bangunanList = MutableStateFlow<List<Bangunan>>(emptyList())
    val bangunanList: StateFlow<List<Bangunan>> get() = _bangunanList

    // Fungsi untuk load list bangunan
    fun loadBangunanList() {
        viewModelScope.launch {
            try {
                // Ambil response dari repository
                val response = bangunanRepository.getBangunan()

                // Cek jika response memiliki properti data yang berisi list bangunan
                val bangunanList = response.data  // Ambil data bangunan dari response
                _bangunanList.value = bangunanList // Update stateFlow

            } catch (e: Exception) {
                // Handle error jika gagal mengambil data bangunan
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk update state kamar
    fun updateInsertKmrState(kamarUiEvent: KamarUiEvent) {
        uiState = KamarUiState(kamarUiEvent = kamarUiEvent)
    }

    // Fungsi untuk insert kamar ke repository
    suspend fun insertKmr() {
        viewModelScope.launch {
            try {
                val kamar = uiState.kamarUiEvent.toKmr()  // Konversi 'KamarUiEvent' ke 'Kamar'
                println("Data yang dikirim: $kamar") // Cetak data sebelum dikirim
                kmr.insertKamar(kamar)  // Kirim data kamar ke repository
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

// KamarUiState untuk menyimpan data state UI
data class KamarUiState(
    val kamarUiEvent: KamarUiEvent = KamarUiEvent(),
    val bangunanList: List<Bangunan> = emptyList()  // Menambahkan list bangunan
)

// KamarUiEvent untuk menyimpan data inputan kamar
data class KamarUiEvent(
    val idKamar: String = "",
    val idBangunan: String = "",
    val noKamar: String = "",
    val kapasitas: String = "",
    val statusKamar: String = ""
) {
    // Fungsi untuk validasi data
    fun isValid(): Boolean {
        return idKamar.isNotEmpty() && idBangunan.isNotEmpty() && noKamar.isNotEmpty() && kapasitas.isNotEmpty() && statusKamar.isNotEmpty()
    }

    // Fungsi untuk konversi 'KamarUiEvent' ke objek 'Kamar'
    fun toKmr(): Kamar = Kamar(
        idKamar = idKamar,
        idBangunan = idBangunan,
        noKamar = noKamar,
        kapasitas = kapasitas,
        statusKamar = statusKamar
    )
}

// Convert Kamar to KamarUiEvent
fun Kamar.toKamarUiEvent(): KamarUiEvent = KamarUiEvent(
    idKamar = idKamar,
    idBangunan = idBangunan,
    noKamar = noKamar,
    kapasitas = kapasitas,
    statusKamar = statusKamar
)