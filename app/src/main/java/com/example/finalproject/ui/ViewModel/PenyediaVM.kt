package com.example.finalproject.ui.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject.KamarApplications
import com.example.finalproject.MahasiswaApplications
import com.example.finalproject.ui.ViewModel.KamarVM.DetailKamarVM
import com.example.finalproject.ui.ViewModel.KamarVM.HomeKamarVM
import com.example.finalproject.ui.ViewModel.KamarVM.InsertKamarVM
import com.example.finalproject.ui.ViewModel.KamarVM.UpdateKamarVM
import com.example.finalproject.ui.ViewModel.MhsVM.DetailMhsVM
import com.example.finalproject.ui.ViewModel.MhsVM.HomeMhsVM
import com.example.finalproject.ui.ViewModel.MhsVM.InsertMhsVM
import com.example.finalproject.ui.ViewModel.MhsVM.UpdateMhsVM

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

    }
}
fun CreationExtras.aplikasiMahasiswa():MahasiswaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as MahasiswaApplications)

fun CreationExtras.aplikasiKamar():KamarApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as KamarApplications)