package com.example.finalproject.ui.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject.MahasiswaApplications
import com.example.finalproject.ui.ViewModel.MhsVM.HomeMhsVM
import com.example.finalproject.ui.ViewModel.MhsVM.InsertMhsVM

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeMhsVM(aplikasiMahasiswa().container.mahasiswaRepository) }
        initializer { InsertMhsVM(aplikasiMahasiswa().container.mahasiswaRepository) }

    }
}
fun CreationExtras.aplikasiMahasiswa():MahasiswaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as MahasiswaApplications)