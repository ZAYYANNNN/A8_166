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
import com.example.finalproject.ui.CustomTopAppBar
import com.example.finalproject.ui.ViewModel.MhsVM.UpdateMhsVM
import com.example.finalproject.ui.ViewModel.MhsVM.toMhs
import com.example.finalproject.ui.ViewModel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateMhs : DestinasiNavigasi {
    override val route = "updateMhs"
    const val IDMhs = "idMahasiswa"
    val routesWithArg = "$route/{$IDMhs}"
    override val titleRes = "Update Mahasiswa"
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
    val kamarList = viewModel.kamarList.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
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
            EntryBodyMhs(
                mahasiswaUiState = uiState,
                kamarList = kamarList,
                onMahasiswaValueChange = { updatedValue ->
                    viewModel.updateMhsState(updatedValue)
                },
                onSaveClick = {
                    uiState.mahasiswaUiEvent?.let { mahasiswaUiEvent ->
                        coroutineScope.launch {
                            viewModel.updateMhs(
                                idMahasiswa = viewModel.idMahasiswa,
                                mahasiswa = mahasiswaUiEvent.toMhs()
                            )
                            navigateBack()
                        }
                    }
                }
            )
        }
    }
}
