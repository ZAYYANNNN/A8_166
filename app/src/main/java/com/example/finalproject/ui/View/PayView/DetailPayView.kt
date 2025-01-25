package com.example.finalproject.ui.View.PayView

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
import com.example.finalproject.Model.Sewa
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.ui.CustomTopAppBar
import com.example.finalproject.ui.ViewModel.PayVM.DetailPayVM
import com.example.finalproject.ui.ViewModel.PayVM.DetailSewaUiState
import com.example.finalproject.ui.ViewModel.PenyediaViewModel

object DestinasiDetailPay : DestinasiNavigasi {
    override val route = "detailpay" // base route
    const val IDPay = "idPembayaran" // Nama parameter untuk nim
    val routesWithArg = "$route/{$IDPay}" // Route yang menerima nim sebagai argumen
    override val titleRes = "Detail Pay" // Title untuk halaman ini
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPayView(
    idPembayaran: String,
    modifier: Modifier = Modifier,
    viewModel: DetailPayVM = viewModel(factory = PenyediaViewModel .Factory),
    onEditClick: (String) -> Unit = {},
    navigateBack:()->Unit,
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailPay.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailPay() } // Trigger refresh action on refresh
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idPembayaran) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xFF0D47A1)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Pembayaran",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        val detailSewaUiState by viewModel.detailSewaUiState.collectAsState()
        BodyDetailPay(
            modifier = Modifier.padding(innerPadding),
            detailSewaUiState = detailSewaUiState,
            retryAction = { viewModel.getDetailPay() }
        )
    }
}
@Composable
fun BodyDetailPay(
    modifier: Modifier = Modifier,
    detailSewaUiState: DetailSewaUiState,
    retryAction: () -> Unit = {}
) {
    when (detailSewaUiState) {
        is DetailSewaUiState.Loading -> {
            // Menampilkan gambar loading saat data sedang dimuat
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailSewaUiState.Success -> {
            // Menampilkan detail mahasiswa jika berhasil
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailPay(sewa = detailSewaUiState.sewa)
            }
        }
        is DetailSewaUiState.Error -> {
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
fun ItemDetailPay(
    sewa: Sewa
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0D47A1), // Warna biru tua
            contentColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailPay(judul = "ID Pembayaran", isinya = sewa.idPembayaran)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPay(judul = "ID Mahasiswa", isinya = sewa.idMahasiswa)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPay(judul = "Jumlah", isinya = sewa.jumlah)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPay(judul = "Tanggal Pembayaran", isinya = sewa.tglPembayaran)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPay(judul = "Status Pembayaran", isinya = sewa.statusPembayaran)
        }
    }
}

@Composable
fun ComponentDetailPay(
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