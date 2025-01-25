package com.example.finalproject.ui.View.PayView

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.Model.Sewa
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.R
import com.example.finalproject.ui.CustomTopAppBar
import com.example.finalproject.ui.ViewModel.PayVM.HomePayVM
import com.example.finalproject.ui.ViewModel.PayVM.HomeSewaUiState
import com.example.finalproject.ui.ViewModel.PenyediaViewModel
import java.text.SimpleDateFormat
import java.util.Locale

object DestinasiHomePay : DestinasiNavigasi {
    override val route = "homepay"
    override val titleRes = "Home Pay"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPay(
    navigateToItemEntry: () -> Unit,
    navigateBack: () -> Unit, // Tambahkan fungsi navigateBack
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomePayVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomePay.titleRes,
                canNavigateBack = true, // Aktifkan opsi kembali
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getSwa()
                },
                navigateUp = navigateBack // Menggunakan fungsi navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pembayaran")
            }
        },
    ) { innerPadding ->
        HomeStatusPay(
            homeSewaUiState = viewModel.sewaUIState,
            retryAction = { viewModel.getSwa() }, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = {
                viewModel.deleteSewa(it.idPembayaran)
                viewModel.getSwa()
            }
        )
    }
}

@Composable
fun HomeStatusPay(
    homeSewaUiState: HomeSewaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Sewa) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeSewaUiState) {
        is HomeSewaUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeSewaUiState.Success ->
            if (homeSewaUiState.sewa.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data kontak")
                }
            } else {
                PayLayout(
                    sewa = homeSewaUiState.sewa, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idPembayaran)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }

        is HomeSewaUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )

        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PayLayout(
    sewa: List<Sewa>,
    modifier: Modifier = Modifier,
    onDetailClick: (Sewa) -> Unit,
    onDeleteClick: (Sewa) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(sewa) { sewa -> // Perbaikan di sini
            PayCard(
                sewa = sewa,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(sewa) },
                onDeleteClick = {
                    onDeleteClick(sewa)
                }
            )
        }
    }
}

@Composable
fun PayCard(
    sewa: Sewa,
    modifier: Modifier = Modifier,
    onDeleteClick: (Sewa) -> Unit = {}
) {
    val formattedDate = try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(sewa.tglPembayaran)
        outputFormat.format(date ?: "")
    } catch (e: Exception) {
        sewa.tglPembayaran // Jika terjadi error, tampilkan data asli
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = sewa.idPembayaran,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(sewa) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                Text(
                    text = sewa.idMahasiswa,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = sewa.jumlah,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = formattedDate,  // Tampilkan tanggal dengan format yyyy-MM-dd
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = sewa.statusPembayaran,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}