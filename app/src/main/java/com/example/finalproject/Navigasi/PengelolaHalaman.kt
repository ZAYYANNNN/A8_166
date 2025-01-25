package com.example.finalproject.Navigasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalproject.ui.View.BgnView.DestinasiDetailBgn
import com.example.finalproject.ui.View.BgnView.DestinasiEntryBgn
import com.example.finalproject.ui.View.BgnView.DestinasiHomeBgn
import com.example.finalproject.ui.View.BgnView.DestinasiUpdateBgn
import com.example.finalproject.ui.View.BgnView.DetailBgnView
import com.example.finalproject.ui.View.BgnView.EntryBgnScreen
import com.example.finalproject.ui.View.BgnView.HomeScreenBgn
import com.example.finalproject.ui.View.BgnView.UpdateBgnView
import com.example.finalproject.ui.View.KmrView.DestinasiDetailKmr
import com.example.finalproject.ui.View.KmrView.DestinasiEntryKmr
import com.example.finalproject.ui.View.KmrView.DestinasiHomeKmr
import com.example.finalproject.ui.View.KmrView.DestinasiUpdateKmr
import com.example.finalproject.ui.View.KmrView.DetailKmrView
import com.example.finalproject.ui.View.KmrView.EntryKmrScreen
import com.example.finalproject.ui.View.KmrView.HomeScreenKmr
import com.example.finalproject.ui.View.KmrView.UpdateKmrView
import com.example.finalproject.ui.View.MhsView.DestinasiDetailMhs
import com.example.finalproject.ui.View.MhsView.DestinasiEntryMhs
import com.example.finalproject.ui.View.MhsView.DestinasiHomeMhs
import com.example.finalproject.ui.View.MhsView.DestinasiUpdateMhs
import com.example.finalproject.ui.View.MhsView.DetailMhsView
import com.example.finalproject.ui.View.MhsView.EntryMhsScreen
import com.example.finalproject.ui.View.MhsView.HomeScreen
import com.example.finalproject.ui.View.MhsView.UpdateMhsView
import com.example.finalproject.ui.ViewModel.MhsVM.HomeMhsVM
import com.example.finalproject.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.finalproject.ui.View.PayView.DestinasiDetailPay
import com.example.finalproject.ui.View.PayView.DestinasiEntryPay
import com.example.finalproject.ui.View.PayView.DestinasiHomePay
import com.example.finalproject.ui.View.PayView.DestinasiUpdatePay
import com.example.finalproject.ui.View.PayView.DetailPayView
import com.example.finalproject.ui.View.PayView.EntrySwaScreen
import com.example.finalproject.ui.View.PayView.HomeScreenPay
import com.example.finalproject.ui.View.PayView.UpdatePayView

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "HalamanMenu",
        modifier = Modifier
    ) {
        composable(
            route = "HalamanMenu"
        ) {
           HalamanMenu(navController = navController)
        }

        // Menu Mahasiswa
        composable(DestinasiHomeMhs.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryMhs.route) },
                onDetailClick = { idMahasiswa ->
                    navController.navigate("${DestinasiDetailMhs.route}/$idMahasiswa") {
                        popUpTo(DestinasiHomeMhs.route) { inclusive = true }
                    }
                },
                navigateBack = { navController.navigate("HalamanMenu") }
            )
        }
        composable(DestinasiEntryMhs.route) {
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHomeMhs.route) {
                    popUpTo(DestinasiHomeMhs.route) { inclusive = true }
                }
            })
        }
        composable(DestinasiDetailMhs.routesWithArg) { backStackEntry ->
            val idMahasiswa = backStackEntry.arguments?.getString(DestinasiDetailMhs.IDMhs)
            idMahasiswa?.let {
                DetailMhsView(
                    idMahasiswa = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomeMhs.route) {
                            popUpTo(DestinasiHomeMhs.route) { inclusive = true }
                        }
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateMhs.route}/$it")
                    }
                )
            }
        }
        composable(
            DestinasiUpdateMhs.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdateMhs.IDMhs) { type = NavType.StringType })
        ) { backStackEntry ->
            val idMahasiswa = backStackEntry.arguments?.getString(DestinasiUpdateMhs.IDMhs)
            idMahasiswa?.let {
                UpdateMhsView(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

        // Menu Kamar
        composable(DestinasiHomeKmr.route) {
            HomeScreenKmr(
                navigateToItemEntry = { navController.navigate(DestinasiEntryKmr.route) },
                onDetailClick = { idKamar ->
                    navController.navigate("${DestinasiDetailKmr.route}/$idKamar") {
                        popUpTo(DestinasiHomeKmr.route) { inclusive = true }
                    }
                },
                navigateBack = { navController.navigate("HalamanMenu") }
            )
        }
        composable(DestinasiEntryKmr.route) {
            EntryKmrScreen(navigateBack = {
                navController.navigate(DestinasiHomeKmr.route) {
                    popUpTo(DestinasiHomeKmr.route) { inclusive = true }
                }
            })
        }
        composable(DestinasiDetailKmr.routesWithArg) { backStackEntry ->
            val idKamar = backStackEntry.arguments?.getString(DestinasiDetailKmr.IDKmr)
            idKamar?.let {
                DetailKmrView(
                    idKamar = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomeKmr.route) {
                            popUpTo(DestinasiHomeKmr.route) { inclusive = true }
                        }
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateKmr.route}/$it")
                    }
                )
            }
        }
        composable(
            DestinasiUpdateKmr.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdateKmr.IDKmr) { type = NavType.StringType })
        ) { backStackEntry ->
            val idKamar = backStackEntry.arguments?.getString(DestinasiUpdateKmr.IDKmr)
            idKamar?.let {
                UpdateKmrView(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

        // Menu Bangunan
        composable(DestinasiHomeBgn.route) {
            HomeScreenBgn(
                navigateToItemEntry = { navController.navigate(DestinasiEntryBgn.route) },
                onDetailClick = { idBangunan ->
                    navController.navigate("${DestinasiDetailBgn.route}/$idBangunan") {
                        popUpTo(DestinasiHomeBgn.route) { inclusive = true }
                    }
                },
                navigateBack = { navController.navigate("HalamanMenu") }
            )
        }
        composable(DestinasiEntryBgn.route) {
            EntryBgnScreen(navigateBack = {
                navController.navigate(DestinasiHomeBgn.route) {
                    popUpTo(DestinasiHomeBgn.route) { inclusive = true }
                }
            })
        }
        composable(DestinasiDetailBgn.routesWithArg) { backStackEntry ->
            val idBangunan = backStackEntry.arguments?.getString(DestinasiDetailBgn.IDBgn)
            idBangunan?.let {
                DetailBgnView(
                    idBangunan = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomeBgn.route) {
                            popUpTo(DestinasiHomeBgn.route) { inclusive = true }
                        }
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateBgn.route}/$it")
                    }
                )
            }
        }
        composable(
            DestinasiUpdateBgn.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdateBgn.IDBgn) { type = NavType.StringType })
        ) { backStackEntry ->
            val idBangunan = backStackEntry.arguments?.getString(DestinasiUpdateBgn.IDBgn)
            idBangunan?.let {
                UpdateBgnView(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

        // Menu Pembayaran
        composable(DestinasiHomePay.route) {
            HomeScreenPay(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPay.route) },
                onDetailClick = { idPembayaran ->
                    navController.navigate("${DestinasiDetailPay.route}/$idPembayaran") {
                        popUpTo(DestinasiHomePay.route) { inclusive = true }
                    }
                },
                navigateBack = { navController.navigate("HalamanMenu") }            )
        }
        composable(DestinasiEntryPay.route) {
            EntrySwaScreen(navigateBack = {
                navController.navigate(DestinasiHomePay.route) {
                    popUpTo(DestinasiHomePay.route) { inclusive = true }
                }
            })
        }
        composable(DestinasiDetailPay.routesWithArg) { backStackEntry ->
            val idPembayaran = backStackEntry.arguments?.getString(DestinasiDetailPay.IDPay)
            idPembayaran?.let {
                DetailPayView(
                    idPembayaran = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomePay.route) {
                            popUpTo(DestinasiHomePay.route) { inclusive = true }
                        }
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdatePay.route}/$it")
                    }
                )
            }
        }
        composable(
            DestinasiUpdatePay.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdatePay.IDPay) { type = NavType.StringType })
        ) { backStackEntry ->
            val idPembayaran = backStackEntry.arguments?.getString(DestinasiUpdatePay.IDPay)
            idPembayaran?.let {
                UpdatePayView(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun HalamanMenu(navController: NavHostController) {
    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Box untuk latar belakang logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = androidx.compose.ui.graphics.Color.White) // Background putih
                    .padding(vertical = 32.dp) // Jarak vertikal untuk menempatkan logo lebih ke bawah
            ) {
                // Logo Gundar di tengah
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo Gundar",
                    modifier = Modifier
                        .size(300.dp) // Memperbesar ukuran gambar
                        .align(Alignment.Center) // Menempatkan gambar di tengah
                )
            }

            // Spacer untuk menambahkan jarak antara logo dan box menu
            Spacer(modifier = Modifier.height(15.dp)) // Atur jarak sesuai kebutuhan

            // Box untuk Menu Mahasiswa, Kamar, Bangunan, Pembayaran
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight() // Mengisi seluruh tinggi layar
                    .clip(RoundedCornerShape(16.dp)) // Membuat sudut rounded untuk seluruh box
                    .background(color = androidx.compose.ui.graphics.Color(0xFFF0F0F0)) // Background cream untuk box menu
                    .padding(16.dp) // Padding dalam box
            ) {
                // Column untuk menampung tombol-tombol dalam box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(), // Membuat kolom mengisi seluruh tinggi box
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Jarak antar tombol
                ) {
                    Button(
                        onClick = { navController.navigate(DestinasiHomeMhs.route) },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = androidx.compose.ui.graphics.Color(0xFF001F54) // Warna navy
                        )
                    ) {
                        Text(
                            text = "Menu Mahasiswa",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.ui.graphics.Color.White // Warna teks putih
                        )
                    }
                    Button(
                        onClick = { navController.navigate(DestinasiHomeKmr.route) },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = androidx.compose.ui.graphics.Color(0xFF001F54) // Warna navy
                        )
                    ) {
                        Text(
                            text = "Menu Kamar",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.ui.graphics.Color.White // Warna teks putih
                        )
                    }
                    Button(
                        onClick = { navController.navigate(DestinasiHomeBgn.route) },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = androidx.compose.ui.graphics.Color(0xFF001F54) // Warna navy
                        )
                    ) {
                        Text(
                            text = "Menu Bangunan",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.ui.graphics.Color.White // Warna teks putih
                        )
                    }
                    Button(
                        onClick = { navController.navigate(DestinasiHomePay.route) },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = androidx.compose.ui.graphics.Color(0xFF001F54) // Warna navy
                        )
                    ) {
                        Text(
                            text = "Menu Pembayaran",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.ui.graphics.Color.White // Warna teks putih
                        )
                    }
                }
            }
        }
    }
}
