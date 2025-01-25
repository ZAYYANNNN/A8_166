package com.example.finalproject.Repository

import com.example.finalproject.Model.AllMahasiswaResponse
import com.example.finalproject.Model.AllSewaResponse
import com.example.finalproject.Model.Sewa
import com.example.finalproject.service_api.SewaService
import java.io.IOException


    interface SewaRepository{
        suspend fun getPembayaran(): AllSewaResponse

        suspend fun  insertPembayaran(sewa: Sewa)

        suspend fun updatePembayaran(idPembayaran: String, sewa: Sewa)

        suspend fun  deletePembayaran(idPembayaran: String)

        suspend fun getPembayaranbyid(idPembayaran: String): Sewa
    }

    class NetworkSewaRepository(
        private val SewaApiService: SewaService
    ) : SewaRepository {
        override suspend fun getPembayaran(): AllSewaResponse {
            return SewaApiService.getAllPembayaran()
        }


        override suspend fun insertPembayaran(sewa: Sewa) {
            SewaApiService.insertPembayaran(sewa)
        }

        override suspend fun updatePembayaran(idPembayaran: String, sewa: Sewa) {
            SewaApiService.updatePembayaran(idPembayaran, sewa)
        }

        override suspend fun deletePembayaran(idPembayaran: String) {
            try {
                val response = SewaApiService.deletePembayaran(idPembayaran)
                if (!response.isSuccessful) { // Corrected spelling
                    throw IOException(
                        "Failed to delete Pembayaran. HTTP Status Code: " + "${response.code()}"
                    )
                } else {
                    response.message()
                    println(response.message())
                }
            } catch (e: Exception) {
                throw e
            }
        }


        override suspend fun getPembayaranbyid(idPembayaran: String): Sewa {
            return SewaApiService.getPembayaranbyid(idPembayaran).data
        }

    }