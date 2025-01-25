package com.example.finalproject.ui.View.PayView

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.Model.Mahasiswa
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.ui.CustomTopAppBar
import com.example.finalproject.ui.ViewModel.PayVM.InsertPayVM
import com.example.finalproject.ui.ViewModel.PayVM.SewaUiEvent
import com.example.finalproject.ui.ViewModel.PayVM.SewaUiState
import com.example.finalproject.ui.ViewModel.PenyediaViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

object DestinasiEntryPay :DestinasiNavigasi{
    override val route = "insertpay"
    override val titleRes = "Entry Pay"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySwaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: InsertPayVM = viewModel(factory = PenyediaViewModel.Factory)

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val mahasiswaList by viewModel.mahasiswaList.collectAsState()
    val sewaUiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadMahasiswaList()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiEntryPay.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPay(
            sewaUiState = sewaUiState,
            mahasiswaList = mahasiswaList,
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
    mahasiswaList: List<Mahasiswa>,
    onPayValueChange: (SewaUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        if (mahasiswaList.isEmpty()) {
            Text(text = "Data Mahasiswa belum tersedia", color = MaterialTheme.colorScheme.error)
        } else {
            FormInputPay(
                sewaUiEvent = sewaUiState.sewaUiEvent,
                mahasiswaList = mahasiswaList,
                onValueChange = onPayValueChange,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPay(
    sewaUiEvent: SewaUiEvent,
    mahasiswaList: List<Mahasiswa>,
    modifier: Modifier = Modifier,
    onValueChange: (SewaUiEvent) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(sewaUiEvent.tglPembayaran) }

    // Fungsi untuk menampilkan DatePickerDialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // Menyimpan tanggal yang dipilih dalam format yyyy/MM/dd
            val formattedDate =
                "%04d/%02d/%02d".format(year, month + 1, dayOfMonth) // Format yyyy/MM/dd
            selectedDate = formattedDate
            onValueChange(sewaUiEvent.copy(tglPembayaran = formattedDate)) // Update tglPembayaran di ViewModel
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    var statusPembayaranExpanded by remember { mutableStateOf(false) }
    val statusPembayaranOptions = listOf("Lunas", "Belum Lunas")
    var selectedStatusPembayaran by remember { mutableStateOf(sewaUiEvent.statusPembayaran) }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = sewaUiEvent.idPembayaran,
            onValueChange = { onValueChange(sewaUiEvent.copy(idPembayaran = it)) },
            label = { Text("ID Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = sewaUiEvent.idMahasiswa,
                onValueChange = { onValueChange(sewaUiEvent.copy(idMahasiswa = it)) },
                label = { Text("ID Mahasiswa") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(), // Gunakan menuAnchor di sini
                singleLine = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                }
            )

            // ExposedDropdownMenu harus ditempatkan di dalam ExposedDropdownMenuBox
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                mahasiswaList.forEach { mahasiswa ->
                    DropdownMenuItem(
                        text = { Text("ID Mahasiswa: ${mahasiswa.idMahasiswa}") },
                        onClick = {
                            onValueChange(sewaUiEvent.copy(idMahasiswa = mahasiswa.idMahasiswa))
                            isExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = sewaUiEvent.jumlah,
            onValueChange = { onValueChange(sewaUiEvent.copy(jumlah = it)) },
            label = { Text("Jumlah") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        ExposedDropdownMenuBox(
            expanded = statusPembayaranExpanded,
            onExpandedChange = { statusPembayaranExpanded = !statusPembayaranExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedStatusPembayaran,
                onValueChange = { selectedStatusPembayaran = it },
                label = { Text("Status Pembayaran") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(), // Gunakan menuAnchor di sini
                singleLine = true,
                readOnly = true, // Biarkan readOnly agar pengguna tidak bisa mengetik manual
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusPembayaranExpanded)
                }
            )

            // ExposedDropdownMenu untuk status pembayaran
            ExposedDropdownMenu(
                expanded = statusPembayaranExpanded,
                onDismissRequest = { statusPembayaranExpanded = false }
            ) {
                statusPembayaranOptions.forEach { status ->
                    DropdownMenuItem(
                        text = { Text(status) },
                        onClick = {
                            selectedStatusPembayaran = status
                            onValueChange(sewaUiEvent.copy(statusPembayaran = status))
                            statusPembayaranExpanded = false
                        }
                    )
                }
            }
        }
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Tanggal Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Pilih Tanggal")
                }
            }
        )
    }
}