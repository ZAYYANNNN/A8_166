package com.example.finalproject.service_api

import com.example.finalproject.Model.AllKamarResponse
import com.example.finalproject.Model.Kamar
import com.example.finalproject.Model.KamarDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KamarService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getAllKamar(): AllKamarResponse

    @GET("{idKamar}")
    suspend fun getKamarbyid(@Path("idKamar") idKamar:String): KamarDetailResponse

    @POST("store")
    suspend fun insertKamar(@Body kamar: Kamar)

    @PUT("{idKamar}")
    suspend fun updateKamar(@Path("idKamar")idKamar: String, @Body kamar: Kamar)

    @DELETE("{idKamar}")
    suspend fun deleteKamar(@Path("idKamar") idKamar:String): Response<Void>

}