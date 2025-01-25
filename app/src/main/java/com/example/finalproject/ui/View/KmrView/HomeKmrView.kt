package com.example.finalproject.ui.View.KmrView

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.Model.Kamar
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.ui.CustomTopAppBar
import com.example.finalproject.ui.View.OnError
import com.example.finalproject.ui.View.OnLoading
import com.example.finalproject.ui.ViewModel.KamarVM.HomeKamarVM
import com.example.finalproject.ui.ViewModel.KamarVM.HomeKmrUiState
import com.example.finalproject.ui.ViewModel.PenyediaViewModel

object DestinasiHomeKmr : DestinasiNavigasi {
    override val route = "homekmr"
    override val titleRes = "Home Kamar"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenKmr(
    navigateToItemEntry: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeKamarVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomeKmr.titleRes,
                canNavigateBack = true,  // Izinkan navigasi kembali
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getKmr()
                },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kamar")
            }
        },
    ) { innerPadding ->
        HomeStatusKmr(
            homeKmrUiState = viewModel.kmrUIState,
            retryAction = { viewModel.getKmr() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { kamar ->
                viewModel.deleteKmr(kamar.idKamar)
                viewModel.getKmr()
            }
        )
    }
}

@Composable
fun HomeStatusKmr(
    homeKmrUiState: HomeKmrUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kamar) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeKmrUiState) {
        is HomeKmrUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeKmrUiState.Success ->
            if (homeKmrUiState.kamar.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data kamar")
                }
            } else {
                KmrLayout(
                    kamar = homeKmrUiState.kamar,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idKamar) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }

        is HomeKmrUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun KmrLayout(
    kamar: List<Kamar>,
    modifier: Modifier = Modifier,
    onDetailClick: (Kamar) -> Unit,
    onDeleteClick: (Kamar) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kamar) { kamar ->
            KmrCard(
                kamar = kamar,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(kamar) },
                onDeleteClick = { onDeleteClick(kamar) }
            )
        }
    }
}

@Composable
fun KmrCard(
    kamar: Kamar,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kamar) -> Unit = {}
) {
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
                    text = kamar.idKamar,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(kamar) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            Text(
                text = "No. Kamar: ${kamar.noKamar}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Kapasitas: ${kamar.kapasitas}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Status: ${kamar.statusKamar}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}