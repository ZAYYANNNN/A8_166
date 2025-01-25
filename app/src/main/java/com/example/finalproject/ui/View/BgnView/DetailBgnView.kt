package com.example.finalproject.ui.View.BgnView

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
import com.example.finalproject.Model.Bangunan
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.ui.CustomTopAppBar
import com.example.finalproject.ui.View.OnError
import com.example.finalproject.ui.View.OnLoading
import com.example.finalproject.ui.ViewModel.BangunanVM.DetailBangunanVM
import com.example.finalproject.ui.ViewModel.BangunanVM.DetailBgnUiState
import com.example.finalproject.ui.ViewModel.PenyediaViewModel

object DestinasiDetailBgn : DestinasiNavigasi {
    override val route = "detailBgn" // base route
    const val IDBgn = "idBangunan" // Nama parameter untuk nim
    val routesWithArg = "$route/{$IDBgn}" // Route yang menerima nim sebagai argumen
    override val titleRes = "Detail Bangunan" // Title untuk halaman ini
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBgnView(
    idBangunan: String,
    modifier: Modifier = Modifier,
    viewModel: DetailBangunanVM = viewModel(factory = PenyediaViewModel .Factory),
    onEditClick: (String) -> Unit = {},
    navigateBack:()->Unit,
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailBgn.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailBangunan() } // Trigger refresh action on refresh
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idBangunan) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Bangunan"
                )
            }
        }
    ) { innerPadding ->
        val detailBgnUiState by viewModel.detailBgnUiState.collectAsState()
        BodyDetailBgn(
            modifier = Modifier.padding(innerPadding),
            detailBgnUiState = detailBgnUiState,
            retryAction = { viewModel.getDetailBangunan() }
        )
    }
}
@Composable
fun BodyDetailBgn(
    modifier: Modifier = Modifier,
    detailBgnUiState: DetailBgnUiState,
    retryAction: () -> Unit = {}
) {
    when (detailBgnUiState) {
        is DetailBgnUiState.Loading -> {
            // Menampilkan gambar loading saat data sedang dimuat
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailBgnUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailBgn(bangunan = detailBgnUiState.bangunan)
            }
        }
        is DetailBgnUiState.Error -> {
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
fun ItemDetailBgn(
    bangunan: Bangunan
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailBgn(judul = "ID Bangunan", isinya = bangunan.idBangunan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailBgn(judul = "Nama Bangunan", isinya = bangunan.namaBangunan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailBgn(judul = "Jumlah Lantai", isinya = bangunan.jumlahLantai)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailBgn(judul = "Alamat", isinya = bangunan.alamat)

        }
    }
}
@Composable
fun ComponentDetailBgn(
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