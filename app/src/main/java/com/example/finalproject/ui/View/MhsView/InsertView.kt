package com.example.finalproject.ui.View.MhsView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.Model.Kamar
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.ui.CostumeTopAppBar
import com.example.finalproject.ui.ViewModel.MhsVM.InsertMhsVM
import com.example.finalproject.ui.ViewModel.MhsVM.MahasiswaUiEvent
import com.example.finalproject.ui.ViewModel.MhsVM.MahasiswaUiState
import com.example.finalproject.ui.ViewModel.PenyediaViewModel
import kotlinx.coroutines.launch

// Destinasi navigasi untuk EntryMhs
object DestinasiEntryMhs : DestinasiNavigasi {
    override val route = "entryMhs"
    override val titleRes = "Entry Mhs"
}

// EntryMhsScreen untuk UI utama
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMhsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: InsertMhsVM = viewModel(factory = PenyediaViewModel.Factory)

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val kamarList by viewModel.kamarList.collectAsState()
    val mahasiswaUiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadKamarList()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryMhs.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyMhs(
            mahasiswaUiState = mahasiswaUiState,
            kamarList = kamarList,
            onMahasiswaValueChange = viewModel::updateInsertMhsState,
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

// EntryBodyMhs untuk form input mahasiswa
@Composable
fun EntryBodyMhs(
    mahasiswaUiState: MahasiswaUiState,
    kamarList: List<Kamar>,
    onMahasiswaValueChange: (MahasiswaUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        if (kamarList.isEmpty()) {
            Text(text = "Data Kamar belum tersedia", color = MaterialTheme.colorScheme.error)
        } else {
            FormInputMhs(
                mahasiswaUiEvent = mahasiswaUiState.mahasiswaUiEvent,
                kamarList = kamarList,
                onValueChange = onMahasiswaValueChange,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

// FormInputMhs untuk input form mahasiswa
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputMhs(
    mahasiswaUiEvent: MahasiswaUiEvent,
    kamarList: List<Kamar>,
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaUiEvent) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = mahasiswaUiEvent.idMahasiswa,
            onValueChange = { onValueChange(mahasiswaUiEvent.copy(idMahasiswa = it)) },
            label = { Text("ID Mahasiswa") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = mahasiswaUiEvent.nim,
            onValueChange = { onValueChange(mahasiswaUiEvent.copy(nim = it)) },
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = mahasiswaUiEvent.nama,
            onValueChange = { onValueChange(mahasiswaUiEvent.copy(nama = it)) },
            label = { Text("Nama Mahasiswa") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = mahasiswaUiEvent.email,
            onValueChange = { onValueChange(mahasiswaUiEvent.copy(email = it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = mahasiswaUiEvent.telp,
            onValueChange = { onValueChange(mahasiswaUiEvent.copy(telp = it)) },
            label = { Text("No. Telepon") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = mahasiswaUiEvent.idKamar,
                onValueChange = { onValueChange(mahasiswaUiEvent.copy(idKamar = it)) },
                label = { Text("ID Kamar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                singleLine = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                }
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                kamarList.forEach { kamar ->
                    DropdownMenuItem(
                        text = { Text("ID Kamar: ${kamar.idKamar}") },
                        onClick = {
                            onValueChange(mahasiswaUiEvent.copy(idKamar = kamar.idKamar))
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}
