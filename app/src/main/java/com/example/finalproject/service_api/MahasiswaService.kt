package com.example.finalproject.service_api

import com.example.finalproject.Model.AllMahasiswaResponse
import com.example.finalproject.Model.Mahasiswa
import com.example.finalproject.Model.MahasiswaDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MahasiswaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getAllMahasiswa(): AllMahasiswaResponse

    @GET("{idMahasiswa}")
    suspend fun getMahasiswabyid(@Path("idMahasiswa") idMahasiswa:String): MahasiswaDetailResponse

    @POST("store")
    suspend fun insertMahasiswa(@Body mahasiswa:Mahasiswa)

    @PUT("{idMahasiswa}")
    suspend fun updateMahasiswa(@Path("idMahasiswa")idMahasiswa: String, @Body mahasiswa:Mahasiswa)

    @DELETE("{idMahasiswa}")
    suspend fun deleteMahasiswa(@Path("idMahasiswa") idMahasiswa:String): Response<Void>

}