package com.example.finalproject.Navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.View.MhsView.DestinasiDetailMhs
import com.example.finalproject.ui.View.MhsView.DestinasiEntry
import com.example.finalproject.ui.View.MhsView.DestinasiHomeMhs
import com.example.finalproject.ui.View.MhsView.EntryMhsScreen
import com.example.finalproject.ui.View.MhsView.HomeScreen

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeMhs.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHomeMhs.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { idMahasiswa ->
                    // Navigasi ke destinasi Detail dengan menyertakan nim
                    navController.navigate("${DestinasiDetailMhs.route}/$idMahasiswa") {
                        // Menggunakan popUpTo untuk memastikan navigasi ke Detail dan menghapus stack sebelumnya jika perlu
                        popUpTo(DestinasiHomeMhs.route) {
                            inclusive = true  // Termasuk juga destinasi yang akan dipopUp
                        }
                    }
                    println("PengelolaHalaman: idMahasiswa = $idMahasiswa")
                }
            )
        }
        composable(DestinasiEntry.route){
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHomeMhs.route){
                    popUpTo(DestinasiHomeMhs.route){
                        inclusive = true
                    }
                }
            })
        }


    }
}