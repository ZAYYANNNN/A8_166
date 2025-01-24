package com.example.finalproject.ui.View.KmrView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.Model.Bangunan
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.ui.CostumeTopAppBar
import com.example.finalproject.ui.ViewModel.KamarVM.InsertKamarVM
import com.example.finalproject.ui.ViewModel.KamarVM.KamarUiEvent
import com.example.finalproject.ui.ViewModel.KamarVM.KamarUiState
import com.example.finalproject.ui.ViewModel.PenyediaViewModel
import kotlinx.coroutines.launch

// Destinasi navigasi untuk EntryKmr
object DestinasiEntryKmr : DestinasiNavigasi {
    override val route = "entryKmr"
    override val titleRes = "Entry Kmr"
}

// EntryKmrScreen untuk UI utama
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryKmrScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: InsertKamarVM = viewModel(factory = PenyediaViewModel.Factory)

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val bangunanList by viewModel.bangunanList.collectAsState()
    val kamarUiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadBangunanList()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryKmr.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyKmr(
            kamarUiState = kamarUiState,
            bangunanList = bangunanList,
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


// EntryBodyKmr untuk form input kamar
@Composable
fun EntryBodyKmr(
    kamarUiState: KamarUiState,
    bangunanList: List<Bangunan>,  // Terima bangunanList di sini
    onKamarValueChange: (KamarUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        if (bangunanList.isEmpty()) {
            Text(text = "Data Bangunan belum tersedia", color = MaterialTheme.colorScheme.error)
        } else {
            FormInputKmr(
                kamarUiEvent = kamarUiState.kamarUiEvent,
                bangunanList = bangunanList,  // Pass bangunanList ke FormInputKmr
                onValueChange = onKamarValueChange,
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

// FormInputKmr untuk input form kamar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputKmr(
    kamarUiEvent: KamarUiEvent,
    bangunanList: List<Bangunan>,
    modifier: Modifier = Modifier,
    onValueChange: (KamarUiEvent) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = kamarUiEvent.idKamar,
            onValueChange = { onValueChange(kamarUiEvent.copy(idKamar = it)) },
            label = { Text("ID Kamar") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = kamarUiEvent.idBangunan,
                onValueChange = { onValueChange(kamarUiEvent.copy(idBangunan = it)) },
                label = { Text("ID Bangunan") },
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
                bangunanList.forEach { bangunan ->
                    DropdownMenuItem(
                        text = { Text("ID Bangunan: ${bangunan.idBangunan}") },
                        onClick = {
                            onValueChange(kamarUiEvent.copy(idBangunan = bangunan.idBangunan))
                            isExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = kamarUiEvent.noKamar,
            onValueChange = { onValueChange(kamarUiEvent.copy(noKamar = it)) },
            label = { Text("No Kamar") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = kamarUiEvent.kapasitas,
            onValueChange = { onValueChange(kamarUiEvent.copy(kapasitas = it)) },
            label = { Text("Kapasitas") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = kamarUiEvent.statusKamar,
            onValueChange = { onValueChange(kamarUiEvent.copy(statusKamar = it)) },
            label = { Text("Status Kamar") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}