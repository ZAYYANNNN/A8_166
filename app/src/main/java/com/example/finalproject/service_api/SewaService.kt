package com.example.finalproject.service_api

import com.example.finalproject.Model.AllSewaResponse
import com.example.finalproject.Model.Sewa
import com.example.finalproject.Model.SewaDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SewaService {

    @GET("pembayaran")
    suspend fun getAllPembayaran(): AllSewaResponse


    @GET("pembayaran/{id_pembayaran}")
    suspend fun getPembayaranbyid(@Path("id_pembayaran") idPembayaran: String): SewaDetailResponse

    @POST("pembayaran/store")
    suspend fun insertPembayaran(@Body sewa: Sewa)

    @PUT("pembayaran/{id_pembayaran}")
    suspend fun updatePembayaran(
        @Path("id_pembayaran") idPembayaran: String,
        @Body sewa: Sewa
    )

    @DELETE("pembayaran/{id_pembayaran}")
    suspend fun deletePembayaran(@Path("id_pembayaran") idPembayaran: String): Response<Void>
}