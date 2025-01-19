package com.example.finalproject.ui.View.MhsView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.Model.Mahasiswa
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.ui.CostumeTopAppBar
import com.example.finalproject.ui.ViewModel.MhsVM.DetailMhsUiState
import com.example.finalproject.ui.ViewModel.MhsVM.DetailMhsVM
import com.example.finalproject.ui.ViewModel.PenyediaViewModel

object DestinasiDetailMhs : DestinasiNavigasi {
    override val route = "detail" // base route
    const val IDMhs = "idMahasiswa" // Nama parameter untuk nim
    val routesWithArg = "$route/{$IDMhs}" // Route yang menerima nim sebagai argumen
    override val titleRes = "Detail Mhs" // Title untuk halaman ini
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMhsView(
    idMahasiswa: String,
    modifier: Modifier = Modifier,
    viewModel: DetailMhsVM = viewModel(factory = PenyediaViewModel .Factory),
    onEditClick: (String) -> Unit = {},
    navigateBack:()->Unit,
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailMhs.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailMahasiswa() } // Trigger refresh action on refresh
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idMahasiswa) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mahasiswa"
                )
            }
        }
    ) { innerPadding ->
        val detailMhsUiState by viewModel.detailMhsUiState.collectAsState()
        BodyDetailMhs(
            modifier = Modifier.padding(innerPadding),
            detailMhsUiState = detailMhsUiState,
            retryAction = { viewModel.getDetailMahasiswa() }
        )
    }
}
@Composable
fun BodyDetailMhs(
    modifier: Modifier = Modifier,
    detailMhsUiState: DetailMhsUiState,
    retryAction: () -> Unit = {}
) {
    when (detailMhsUiState) {
        is DetailMhsUiState.Loading -> {
            // Menampilkan gambar loading saat data sedang dimuat
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailMhsUiState.Success -> {
            // Menampilkan detail mahasiswa jika berhasil
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailMhs(mahasiswa = detailMhsUiState.mahasiswa)
            }
        }
        is DetailMhsUiState.Error -> {
            // Menampilkan error jika data gagal dimuat
            OnError(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
        else -> {
            // Menangani kasus yang tidak terduga (optional, jika Anda ingin menangani hal ini)
            // Anda bisa menambahkan logika untuk menangani kesalahan yang tidak diketahui
            Text("Unexpected state encountered")
        }
    }
}
@Composable
fun ItemDetailMhs(
    mahasiswa: Mahasiswa
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailMhs(judul = "Nama", isinya = mahasiswa.nama)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "ID Mahasiswa", isinya = mahasiswa.idMahasiswa)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "NIM", isinya = mahasiswa.nim)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "ID Kamar", isinya = mahasiswa.idKamar)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Email", isinya = mahasiswa.email)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "No Telepon", isinya = mahasiswa.telp)
        }
    }
}
@Composable
fun ComponentDetailMhs(
    judul: String,
    isinya: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul :",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}