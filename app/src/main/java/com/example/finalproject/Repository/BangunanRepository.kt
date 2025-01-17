package com.example.finalproject.Repository

import com.example.finalproject.Model.AllBangunanResponse
import com.example.finalproject.Model.Bangunan
import com.example.finalproject.service_api.BangunanService
import java.io.IOException

interface BangunanRepository{
    suspend fun getBangunan(): AllBangunanResponse

    suspend fun  insertBangunan(Bangunan: Bangunan)

    suspend fun updateBangunan(idBangunan: String, bangunan: Bangunan)

    suspend fun  deleteBangunan(idBangunan: String)

    suspend fun getBangunanbyid(idBangunan: String): Bangunan
}

class NetworkBangunanRepository(
    private val BangunanApiService: BangunanService
) : BangunanRepository {
    override suspend fun getBangunan(): AllBangunanResponse =
        BangunanApiService.getAllBangunan()


    override suspend fun insertBangunan(bangunan: Bangunan) {
        BangunanApiService.insertBangunan(bangunan)
    }

    override suspend fun updateBangunan(idBangunan: String, bangunan: Bangunan) {
        BangunanApiService.updateBangunan(idBangunan, bangunan)
    }

    override suspend fun deleteBangunan(idBangunan: String) {
        try {
            val response = BangunanApiService.deleteBangunan(idBangunan)
            if (!response.isSuccessful) { // Corrected spelling
                throw IOException(
                    "Failed to delete Bangunan. HTTP Status Code: " + "${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun getBangunanbyid(idBangunan: String): Bangunan {
        return BangunanApiService.getBangunanbyid(idBangunan).data
    }

}