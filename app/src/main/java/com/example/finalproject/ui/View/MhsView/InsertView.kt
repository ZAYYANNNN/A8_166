package com.example.finalproject.ui.View.MhsView

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
import com.example.finalproject.ui.CostumeTopAppBar
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.ui.ViewModel.MhsVM.InsertMhsVM
import com.example.finalproject.ui.ViewModel.MhsVM.MahasiswaUiEvent
import com.example.finalproject.ui.ViewModel.MhsVM.MahasiswaUiState
import com.example.finalproject.ui.ViewModel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntry:DestinasiNavigasi{
    override val route = "item_entry"
    override val titleRes = "Entry Mhs"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMhsScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertMhsVM = viewModel(factory = PenyediaViewModel.Factory)
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
        EntryBody(
            mahasiswaUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateInsertMhsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertMhs()
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
fun EntryBody(
    mahasiswaUiState: MahasiswaUiState,
    onSiswaValueChange:(MahasiswaUiEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier=Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            mahasiswaUiEvent = mahasiswaUiState.mahasiswaUiEvent,
            onValueChange = onSiswaValueChange,
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
    mahasiswaUiEvent: MahasiswaUiEvent,
    modifier: Modifier =Modifier,
    onValueChange:(MahasiswaUiEvent)->Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = mahasiswaUiEvent.idMahasiswa,
            onValueChange = {onValueChange(mahasiswaUiEvent.copy(idMahasiswa = it))},
            label = { Text("ID Mahasiswa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = mahasiswaUiEvent.idKamar,
            onValueChange = {onValueChange(mahasiswaUiEvent.copy(idKamar= it))},
            label = { Text("ID Kamar") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,



        )
        OutlinedTextField(
            value = mahasiswaUiEvent.nim,
            onValueChange = {onValueChange(mahasiswaUiEvent.copy(nim = it))},
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = mahasiswaUiEvent.nama,
            onValueChange = {onValueChange(mahasiswaUiEvent.copy(nama = it))},
            label = { Text("Jenis Kelamin") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = mahasiswaUiEvent.email,
            onValueChange = {onValueChange(mahasiswaUiEvent.copy(email = it))},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = mahasiswaUiEvent.telp,
            onValueChange = {onValueChange(mahasiswaUiEvent.copy(telp = it))},
            label = { Text("No Telepon") },
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