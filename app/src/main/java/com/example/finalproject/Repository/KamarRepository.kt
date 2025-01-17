package com.example.finalproject.Repository

import com.example.finalproject.Model.AllKamarResponse
import com.example.finalproject.Model.Kamar
import com.example.finalproject.service_api.KamarService
import java.io.IOException

interface KamarRepository{
    suspend fun getKamar(): AllKamarResponse

    suspend fun  insertKamar(kamar: Kamar)

    suspend fun updateKamar(idKamar: String, kamar: Kamar)

    suspend fun  deleteKamar(idKamar: String)

    suspend fun getKamarbyid(idKamar: String): Kamar
}

class NetworkKamarRepository(
    private val kamarApiService: KamarService
) : KamarRepository {
    override suspend fun getKamar(): AllKamarResponse =
        kamarApiService.getAllKamar()


    override suspend fun insertKamar(kamar: Kamar) {
        kamarApiService.insertKamar(kamar)
    }

    override suspend fun updateKamar(idKamar: String, kamar: Kamar) {
        kamarApiService.updateKamar(idKamar, kamar)
    }

    override suspend fun deleteKamar(idKamar: String) {
        try {
            val response = kamarApiService.deleteKamar(idKamar)
            if (!response.isSuccessful) { // Corrected spelling
                throw IOException(
                    "Failed to delete Kamar. HTTP Status Code: " + "${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun getKamarbyid(idKamar: String): Kamar {
        return kamarApiService.getKamarbyid(idKamar).data
    }

}