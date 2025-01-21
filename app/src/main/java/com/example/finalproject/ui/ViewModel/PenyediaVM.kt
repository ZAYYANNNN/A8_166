package com.example.finalproject.ui.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject.BangunanApplications
import com.example.finalproject.KamarApplications
import com.example.finalproject.MahasiswaApplications
import com.example.finalproject.SewaApplications
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

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeMhsVM(aplikasiMahasiswa().container.mahasiswaRepository) }
        initializer { InsertMhsVM(aplikasiMahasiswa().container.mahasiswaRepository) }
        initializer { DetailMhsVM(createSavedStateHandle(),aplikasiMahasiswa().container.mahasiswaRepository) }
        initializer { UpdateMhsVM(createSavedStateHandle(),aplikasiMahasiswa().container.mahasiswaRepository) }

        initializer { HomeKamarVM(aplikasiKamar().container.kamarRepository) }
        initializer { InsertKamarVM(aplikasiKamar().container.kamarRepository) }
        initializer { DetailKamarVM(createSavedStateHandle(),aplikasiKamar().container.kamarRepository) }
        initializer { UpdateKamarVM(createSavedStateHandle(),aplikasiKamar().container.kamarRepository) }

        initializer { HomeBangunanVM(aplikasiBangunan().container.bangunanRepository) }
        initializer { InsertBangunanVM(aplikasiBangunan().container.bangunanRepository) }
        initializer { DetailBangunanVM(createSavedStateHandle(),aplikasiBangunan().container.bangunanRepository) }
        initializer { UpdateBangunanVM(createSavedStateHandle(),aplikasiBangunan().container.bangunanRepository) }

        initializer { HomePayVM(aplikasiSewa().container.sewaRepository) }
        initializer { InsertPayVM(aplikasiSewa().container.sewaRepository) }
        initializer { DetailPayVM(createSavedStateHandle(),aplikasiSewa().container.sewaRepository) }
        initializer { UpdatePayVM(createSavedStateHandle(),aplikasiSewa().container.sewaRepository) }

    }
}
fun CreationExtras.aplikasiMahasiswa():MahasiswaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as MahasiswaApplications)

fun CreationExtras.aplikasiKamar():KamarApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as KamarApplications)

fun CreationExtras.aplikasiBangunan():BangunanApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as BangunanApplications)

fun CreationExtras.aplikasiSewa(): SewaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as SewaApplications)