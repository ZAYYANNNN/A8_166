package com.example.finalproject.ui.View.BgnView

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
import com.example.finalproject.ui.ViewModel.BangunanVM.BangunanUiEvent
import com.example.finalproject.ui.ViewModel.BangunanVM.BangunanUiState
import com.example.finalproject.ui.ViewModel.BangunanVM.InsertBangunanVM
import com.example.finalproject.ui.ViewModel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntryBgn:DestinasiNavigasi{
    override val route = "item_entry"
    override val titleRes = "Entry Bangunan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBgnScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertBangunanVM = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryBgn.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyBgn(
            bangunanUiState = viewModel.uiState,
            onBangunanValueChange = viewModel::updateInsertBgnState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertBgn()
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
fun EntryBodyBgn(
    bangunanUiState: BangunanUiState,
    onBangunanValueChange:(BangunanUiEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier=Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputBgn(
            bangunanUiEvent = bangunanUiState.bangunanUiEvent,
            onValueChange = onBangunanValueChange,
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
fun FormInputBgn(
    bangunanUiEvent: BangunanUiEvent,
    modifier: Modifier =Modifier,
    onValueChange:(BangunanUiEvent)->Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = bangunanUiEvent.idBangunan,
            onValueChange = {onValueChange(bangunanUiEvent.copy(idBangunan = it))},
            label = { Text("ID Bangunan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = bangunanUiEvent.namaBangunan,
            onValueChange = {onValueChange(bangunanUiEvent.copy(namaBangunan = it))},
            label = { Text("Nama Bangunan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,



            )
        OutlinedTextField(
            value = bangunanUiEvent.jumlahLantai,
            onValueChange = {onValueChange(bangunanUiEvent.copy(jumlahLantai = it))},
            label = { Text("Jumlah Lantai") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = bangunanUiEvent.alamat,
            onValueChange = {onValueChange(bangunanUiEvent.copy(alamat = it))},
            label = { Text("Jumlah Lantai") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}