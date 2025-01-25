package com.example.finalproject.ui.View.BgnView

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import com.example.finalproject.Model.Bangunan
import com.example.finalproject.Navigasi.DestinasiNavigasi
import com.example.finalproject.ui.CustomTopAppBar
import com.example.finalproject.ui.View.OnError
import com.example.finalproject.ui.View.OnLoading
import com.example.finalproject.ui.ViewModel.BangunanVM.HomeBangunanVM
import com.example.finalproject.ui.ViewModel.BangunanVM.HomeBgnUiState
import com.example.finalproject.ui.ViewModel.PenyediaViewModel

object DestinasiHomeBgn : DestinasiNavigasi {
    override val route = "homebgn"
    override val titleRes = "Home Bangunan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenBgn(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navigateBack: () -> Unit = {},
    viewModel: HomeBangunanVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomeBgn.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getBgn()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Bangunan")
            }
        },
    ) { innerPadding ->
        HomeStatusBgn(
            homeBgnUiState = viewModel.bgnUIState,
            retryAction = { viewModel.getBgn() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { bangunan ->
                viewModel.deleteBgn(bangunan.idBangunan)
                viewModel.getBgn()
            }
        )
    }
}

@Composable
fun HomeStatusBgn(
    homeBgnUiState: HomeBgnUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Bangunan) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeBgnUiState) {
        is HomeBgnUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeBgnUiState.Success ->
            if (homeBgnUiState.bangunan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data bangunan")
                }
            } else {
                BgnLayout(
                    bangunan = homeBgnUiState.bangunan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idBangunan) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }

        is HomeBgnUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun BgnLayout(
    bangunan: List<Bangunan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Bangunan) -> Unit,
    onDeleteClick: (Bangunan) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(bangunan) { bangunan ->
            BgnCard(
                bangunan = bangunan,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(bangunan) },
                onDeleteClick = { onDeleteClick(bangunan) }
            )
        }
    }
}

@Composable
fun BgnCard(
    bangunan: Bangunan,
    modifier: Modifier = Modifier,
    onDeleteClick: (Bangunan) -> Unit = {}
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
                    text = bangunan.idBangunan,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(bangunan) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            Text(
                text = "Nama Bangunan: ${bangunan.namaBangunan}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Jumlah Lantai: ${bangunan.jumlahLantai}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Alamat: ${bangunan.alamat}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}