package com.example.finalproject.service_api

import com.example.finalproject.Model.AllSewaResponse
import com.example.finalproject.Model.Sewa
import com.example.finalproject.Model.SewaDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SewaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getAllPembayaran(): AllSewaResponse

    @GET("{idPembayaran}")
    suspend fun getPembayaranbyid(@Path("idPembayaran") idPembayaran:String): SewaDetailResponse

    @POST("store")
    suspend fun insertPembayaran(@Body sewa: Sewa)

    @PUT("{idPembayaran}")
    suspend fun updatePembayaran(@Path("idPembayaran")idPembayaran: String, @Body sewa: Sewa)

    @DELETE("{idPembayaran}")
    suspend fun deletePembayaran(@Path("idPembayaran") idPembayaran: String): Response<Void>

}