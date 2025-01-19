package com.example.finalproject.ui.View.MhsView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.ui.CostumeTopAppBar
import com.example.finalproject.ui.ViewModel.MhsVM.MahasiswaUiState
import com.example.finalproject.ui.ViewModel.MhsVM.UpdateMhsVM
import com.example.finalproject.ui.ViewModel.MhsVM.toMhs
import com.example.finalproject.ui.ViewModel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateMhs : DestinasiNavigasi {
    override val route = "update"
    const val IDMhs = "idMahasiswa"
    val routesWithArg = "$route/{$IDMhs}"
    override val titleRes = "Update Mhs"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMhsView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMhsVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    // Collect the UI state from the ViewModel
    val uiState = viewModel.uiState.value
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateMhs.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Pass the UI state to the EntryBody
            EntryBody(
                mahasiswaUiState = uiState,
                onSiswaValueChange = { updatedValue ->
                    viewModel.updateMhsState(updatedValue) // Update ViewModel state
                },
                onSaveClick = {
                    uiState.mahasiswaUiEvent?.let { mahasiswaUiEvent ->
                        coroutineScope.launch {
                            // Call ViewModel update method
                            viewModel.updateMhs(
                                idMahasiswa = viewModel.idMahasiswa,
                                mahasiswa = mahasiswaUiEvent.toMhs()
                            )
                            navigateBack() // Navigate back after saving
                        }
                    }
                }
            )
        }
    }
}