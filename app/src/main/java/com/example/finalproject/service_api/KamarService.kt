package com.example.finalproject.service_api

import com.example.finalproject.Model.AllKamarResponse
import com.example.finalproject.Model.Kamar
import com.example.finalproject.Model.KamarDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KamarService {
    // Mengambil semua data kamar
    @GET("kamar")  // Sesuaikan dengan route di backend
    suspend fun getAllKamar(): AllKamarResponse

    // Mengambil data kamar berdasarkan ID
    @GET("kamar/{id_kamar}")  // Sesuaikan dengan route di backend
    suspend fun getKamarbyid(@Path("id_kamar") idKamar: String): KamarDetailResponse

    // Menambahkan data kamar
    @POST("kamar/store")  // Sesuaikan dengan route di backend
    suspend fun insertKamar(@Body kamar: Kamar)

    // Mengupdate data kamar
    @PUT("kamar/{id_kamar}")  // Sesuaikan dengan route di backend
    suspend fun updateKamar(
        @Path("id_kamar") idKamar: String,
        @Body kamar: Kamar
    )

    // Menghapus data kamar
    @DELETE("kamar/{id_kamar}")  // Sesuaikan dengan route di backend
    suspend fun deleteKamar(@Path("id_kamar") idKamar: String): Response<Void>
}