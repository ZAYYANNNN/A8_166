package com.example.finalproject.ui.View.KmrView

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
import com.example.finalproject.ui.ViewModel.KamarVM.UpdateKamarVM
import com.example.finalproject.ui.ViewModel.KamarVM.toKmr
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
    // Collect the UI state from the ViewModel
    val uiState = viewModel.uiState.value
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
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
            // Pass the UI state to the EntryBody
            EntryBodyKmr(
                kamarUiState = uiState,
                onKamarValueChange = { updatedValue ->
                    viewModel.updateKmrState(updatedValue) // Update ViewModel state
                },
                onSaveClick = {
                    uiState.kamarUiEvent?.let { kamarUiEvent ->
                        coroutineScope.launch {
                            // Call ViewModel update method
                            viewModel.updateKmr(
                                idKamar = viewModel.idKamar,
                                kamar = kamarUiEvent.toKmr()
                            )
                            navigateBack() // Navigate back after saving
                        }
                    }
                }
            )
        }
    }
}