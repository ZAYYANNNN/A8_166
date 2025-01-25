package com.example.finalproject.ui.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject.StayApplications
import com.example.finalproject.ui.ViewModel.BangunanVM.DetailBangunanVM
import com.example.finalproject.ui.ViewModel.BangunanVM.HomeBangunanVM
import com.example.finalproject.ui.ViewModel.BangunanVM.InsertBangunanVM
import com.example.finalproject.ui.ViewModel.BangunanVM.UpdateBangunanVM
import com.example.finalproject.ui.ViewModel.KamarVM.DetailKamarVM
import com.example.finalproject.ui.ViewModel.KamarVM.HomeKamarVM
import com.example.finalproject.ui.ViewModel.KamarVM.InsertKamarVM
import com.example.finalproject.ui.ViewModel.KamarVM.UpdateKamarVM
import com.example.finalproject.ui.ViewModel.MhsVM.DetailMhsVM
import com.example.finalproject.ui.ViewModel.MhsVM.HomeMhsVM
import com.example.finalproject.ui.ViewModel.MhsVM.InsertMhsVM
import com.example.finalproject.ui.ViewModel.MhsVM.UpdateMhsVM
import com.example.finalproject.ui.ViewModel.PayVM.DetailPayVM
import com.example.finalproject.ui.ViewModel.PayVM.HomePayVM
import com.example.finalproject.ui.ViewModel.PayVM.InsertPayVM
import com.example.finalproject.ui.ViewModel.PayVM.UpdatePayVM

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // ViewModel untuk Mahasiswa
        initializer { HomeMhsVM(aplikasiStay().container.mahasiswaRepository) }
        initializer { InsertMhsVM(aplikasiStay().container.mahasiswaRepository, aplikasiStay().container.kamarRepository) }  // Tambahkan KamarRepository
        initializer { DetailMhsVM(createSavedStateHandle(), aplikasiStay().container.mahasiswaRepository) }
        initializer { UpdateMhsVM(createSavedStateHandle(), aplikasiStay().container.mahasiswaRepository, aplikasiStay().container.kamarRepository) }

        // ViewModel untuk Kamar
        initializer { HomeKamarVM(aplikasiStay().container.kamarRepository) }
        initializer { InsertKamarVM(aplikasiStay().container.kamarRepository, aplikasiStay().container.bangunanRepository) }
        initializer { DetailKamarVM(createSavedStateHandle(), aplikasiStay().container.kamarRepository) }
        initializer { UpdateKamarVM(createSavedStateHandle(), aplikasiStay().container.kamarRepository, aplikasiStay().container.bangunanRepository) }

        // ViewModel untuk Bangunan
        initializer { HomeBangunanVM(aplikasiStay().container.bangunanRepository) }
        initializer { InsertBangunanVM(aplikasiStay().container.bangunanRepository) }
        initializer { DetailBangunanVM(createSavedStateHandle(), aplikasiStay().container.bangunanRepository) }
        initializer { UpdateBangunanVM(createSavedStateHandle(), aplikasiStay().container.bangunanRepository) }

        // ViewModel untuk Pembayaran
        initializer { HomePayVM(aplikasiStay().container.sewaRepository) }
        initializer { InsertPayVM(aplikasiStay().container.sewaRepository, aplikasiStay().container.mahasiswaRepository) }
        initializer { DetailPayVM(createSavedStateHandle(), aplikasiStay().container.sewaRepository) }
        initializer { UpdatePayVM(createSavedStateHandle(), aplikasiStay().container.sewaRepository, aplikasiStay().container.mahasiswaRepository) }
    }
}

fun CreationExtras.aplikasiStay(): StayApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as StayApplications)