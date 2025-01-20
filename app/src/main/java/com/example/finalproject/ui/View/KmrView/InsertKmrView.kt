package com.example.finalproject.ui.View.KmrView

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
import com.example.finalproject.ui.ViewModel.KamarVM.InsertKamarVM
import com.example.finalproject.ui.ViewModel.KamarVM.KamarUiEvent
import com.example.finalproject.ui.ViewModel.KamarVM.KamarUiState
import com.example.finalproject.ui.ViewModel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntry:DestinasiNavigasi{
    override val route = "item_entry"
    override val titleRes = "Entry Kmr"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryKmrScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertKamarVM = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyKmr(
            kamarUiState = viewModel.uiState,
            onKamarValueChange = viewModel::updateInsertKmrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertKmr()
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
fun EntryBodyKmr(
    kamarUiState: KamarUiState,
    onKamarValueChange:(KamarUiEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier=Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputKmr(
            kamarUiEvent = kamarUiState.kamarUiEvent,
            onValueChange = onKamarValueChange,
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
fun FormInputKmr(
    kamarUiEvent: KamarUiEvent,
    modifier: Modifier =Modifier,
    onValueChange:(KamarUiEvent)->Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = kamarUiEvent.idKamar,
            onValueChange = {onValueChange(kamarUiEvent.copy(idKamar = it))},
            label = { Text("ID Kamar") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = kamarUiEvent.idBangunan,
            onValueChange = {onValueChange(kamarUiEvent.copy(idBangunan= it))},
            label = { Text("ID Bangunan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,



            )
        OutlinedTextField(
            value = kamarUiEvent.noKamar,
            onValueChange = {onValueChange(kamarUiEvent.copy(noKamar = it))},
            label = { Text("No Kamar") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = kamarUiEvent.kapasitas,
            onValueChange = {onValueChange(kamarUiEvent.copy(kapasitas = it))},
            label = { Text("Kapasitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value =kamarUiEvent.statusKamar,
            onValueChange = {onValueChange(kamarUiEvent.copy(statusKamar = it))},
            label = { Text("Email") },
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