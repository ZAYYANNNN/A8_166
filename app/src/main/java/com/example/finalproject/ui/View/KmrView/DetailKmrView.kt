package com.example.finalproject.ui.View.KmrView

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
import com.example.finalproject.Model.Kamar
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.ui.CostumeTopAppBar
import com.example.finalproject.ui.View.OnError
import com.example.finalproject.ui.View.OnLoading
import com.example.finalproject.ui.ViewModel.KamarVM.DetailKamarVM
import com.example.finalproject.ui.ViewModel.KamarVM.DetailKmrUiState
import com.example.finalproject.ui.ViewModel.PenyediaViewModel

object DestinasiDetailKmr : DestinasiNavigasi {
    override val route = "detailKmr" // base route
    const val IDKmr = "idKamar" // Nama parameter untuk nim
    val routesWithArg = "$route/{$IDKmr}" // Route yang menerima nim sebagai argumen
    override val titleRes = "Detail Kamar" // Title untuk halaman ini
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKmrView(
    idKamar: String,
    modifier: Modifier = Modifier,
    viewModel: DetailKamarVM = viewModel(factory = PenyediaViewModel .Factory),
    onEditClick: (String) -> Unit = {},
    navigateBack:()->Unit,
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailKmr.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailKamar() } // Trigger refresh action on refresh
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idKamar) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Kamar"
                )
            }
        }
    ) { innerPadding ->
        val detailKmrUiState by viewModel.detailKmrUiState.collectAsState()
        BodyDetailKmr(
            modifier = Modifier.padding(innerPadding),
            detailKmrUiState = detailKmrUiState,
            retryAction = { viewModel.getDetailKamar() }
        )
    }
}
@Composable
fun BodyDetailKmr(
    modifier: Modifier = Modifier,
    detailKmrUiState: DetailKmrUiState,
    retryAction: () -> Unit = {}
) {
    when (detailKmrUiState) {
        is DetailKmrUiState.Loading -> {
            // Menampilkan gambar loading saat data sedang dimuat
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailKmrUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailKmr(kamar = detailKmrUiState.kamar)
            }
        }
        is DetailKmrUiState.Error -> {
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
fun ItemDetailKmr(
    kamar: Kamar
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailKmr(judul = "ID Kamar", isinya = kamar.idKamar)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailKmr(judul = "ID Bangunan", isinya = kamar.idBangunan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailKmr(judul = "No Kamar", isinya = kamar.noKamar)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailKmr(judul = "Kapasitas", isinya = kamar.kapasitas)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailKmr(judul = "Status", isinya = kamar.statusKamar)
        }
    }
}
@Composable
fun ComponentDetailKmr(
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