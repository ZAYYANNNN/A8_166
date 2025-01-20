package com.example.finalproject.ui.View.PayView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.ui.CostumeTopAppBar
import com.example.finalproject.ui.ViewModel.PayVM.InsertPayVM
import com.example.finalproject.ui.ViewModel.PayVM.SewaUiEvent
import com.example.finalproject.ui.ViewModel.PayVM.SewaUiState
import com.example.finalproject.ui.ViewModel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntryPay :DestinasiNavigasi{
    override val route = "insertpay"
    override val titleRes = "Entry Pay"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPayScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPayVM = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPay.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPay(
            sewaUiState = viewModel.uiState,
            onPayValueChange = viewModel::updateInsertSwaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertSwa()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyPay(
    sewaUiState: SewaUiState,
    onPayValueChange:(SewaUiEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier=Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            sewaUiEvent = sewaUiState.sewaUiEvent,
            onValueChange = onPayValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    sewaUiEvent: SewaUiEvent,
    modifier: Modifier =Modifier,
    onValueChange:(SewaUiEvent)->Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = sewaUiEvent.idPembayaran,
            onValueChange = {onValueChange(sewaUiEvent.copy(idPembayaran = it))},
            label = { Text("ID Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = sewaUiEvent.idMahasiswa,
            onValueChange = {onValueChange(sewaUiEvent.copy(idMahasiswa = it))},
            label = { Text("ID Mahasiswa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
        )
        OutlinedTextField(
            value = sewaUiEvent.jumlah,
            onValueChange = {onValueChange(sewaUiEvent.copy(jumlah = it))},
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = sewaUiEvent.tglPembayaran,
            onValueChange = {onValueChange(sewaUiEvent.copy(tglPembayaran = it))},
            label = { Text("Tanggal Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = sewaUiEvent.statusPembayaran,
            onValueChange = {onValueChange(sewaUiEvent.copy(statusPembayaran = it))},
            label = { Text("Status Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled){
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}