package com.example.finalproject.ui.View.BgnView

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
import com.example.finalproject.ui.ViewModel.BangunanVM.UpdateBangunanVM
import com.example.finalproject.ui.ViewModel.BangunanVM.toBgn
import com.example.finalproject.ui.ViewModel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateBgn : DestinasiNavigasi {
    override val route = "updateBgn"
    const val IDBgn = "idBangunan"
    val routesWithArg = "$route/{$IDBgn}"
    override val titleRes = "Update Bangunan"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBgnView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateBangunanVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    // Collect the UI state from the ViewModel
    val uiState = viewModel.uiState.value
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateBgn.titleRes,
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
            EntryBodyBgn(
                bangunanUiState = uiState,
                onBangunanValueChange = { updatedValue ->
                    viewModel.updateBgnState(updatedValue) // Update ViewModel state
                },
                onSaveClick = {
                    uiState.bangunanUiEvent?.let { bangunanUiEvent ->
                        coroutineScope.launch {
                            // Call ViewModel update method
                            viewModel.updateBgn(
                                idBangunan = viewModel.idBangunan,
                                bangunan = bangunanUiEvent.toBgn()
                            )
                            navigateBack() // Navigate back after saving
                        }
                    }
                }
            )
        }
    }
}