package com.example.finalproject.ui.View.PayView

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
import com.example.finalproject.ui.ViewModel.PayVM.UpdatePayVM
import com.example.finalproject.ui.ViewModel.PayVM.toSwa
import com.example.finalproject.ui.ViewModel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePay : DestinasiNavigasi {
    override val route = "updatepay"
    const val IDPay = "idPembayaran"
    val routesWithArg = "$route/{$IDPay}"
    override val titleRes = "Update Pay"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePayView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePayVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    // Collect the UI state from the ViewModel
    val uiState = viewModel.uiState.value
    val mahasiswaList = viewModel.mahasiswaList.value
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdatePay.titleRes,
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
            EntryBodyPay(
                sewaUiState = uiState,
                mahasiswaList = mahasiswaList,
                onPayValueChange = { updatedValue ->
                    viewModel.updateSewaState(updatedValue)
                },
                onSaveClick = {
                    uiState.sewaUiEvent?.let { sewaUiEvent ->
                        coroutineScope.launch {
                            viewModel.updateSewa(
                                idPembayaran = viewModel.idPembayaran,
                                sewa = sewaUiEvent.toSwa()
                            )
                            navigateBack()
                        }
                    }
                }
            )
        }
    }
}