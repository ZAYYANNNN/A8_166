package com.example.finalproject.ui.View.KmrView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.ui.CustomTopAppBar
import com.example.finalproject.ui.ViewModel.KamarVM.UpdateKamarVM
import com.example.finalproject.ui.ViewModel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateKmr : DestinasiNavigasi {
    override val route = "updateKmr"
    const val IDKmr = "idKamar"
    val routesWithArg = "$route/{$IDKmr}"
    override val titleRes = "Update Kamar"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKmrView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateKamarVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Ambil state kamar dan bangunanList dari ViewModel
    val uiState = viewModel.uiState.value
    val bangunanList = viewModel.bangunanList.value // Ambil .value untuk mendapatkan List<Bangunan>

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateKmr.titleRes,
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
            // Pass kamarUiState dan bangunanList ke EntryBodyKmr
            EntryBodyKmr(
                kamarUiState = uiState,
                bangunanList = bangunanList, // Sekarang kita passing List<Bangunan> yang benar
                onKamarValueChange = { updatedValue ->
                    viewModel.updateKmrState(updatedValue)
                },
                onSaveClick = {
                    uiState.kamarUiEvent?.let { kamarUiEvent ->
                        coroutineScope.launch {
                            viewModel.updateKmr(idKamar = uiState.kamarUiEvent.idKamar, kamar = kamarUiEvent.toKmr())
                            navigateBack()
                        }
                    }
                }
            )
        }
    }
}
