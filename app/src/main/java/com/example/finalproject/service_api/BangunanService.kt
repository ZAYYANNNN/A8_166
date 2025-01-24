package com.example.finalproject.service_api

import com.example.finalproject.Model.AllBangunanResponse
import com.example.finalproject.Model.Bangunan
import com.example.finalproject.Model.BangunanDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BangunanService {

    @GET("bangunan")
    suspend fun getAllBangunan(): AllBangunanResponse

    @GET("bangunan/{id_bangunan}")
    suspend fun getBangunanbyid(@Path("id_bangunan") idBangunan: String): BangunanDetailResponse

    // Menambahkan data bangunan
    @POST("bangunan/store")  // Sesuaikan dengan route di backend
    suspend fun insertBangunan(@Body bangunan: Bangunan)

    // Mengupdate data bangunan
    @PUT("bangunan/{id_bangunan}")  // Sesuaikan dengan route di backend
    suspend fun updateBangunan(
        @Path("id_bangunan") idBangunan: String,
        @Body bangunan: Bangunan
    )

    // Menghapus data bangunan
    @DELETE("bangunan/{id_bangunan}")  // Sesuaikan dengan route di backend
    suspend fun deleteBangunan(@Path("id_bangunan") idBangunan: String): Response<Void>
}