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

    @GET("mahasiswa")
    suspend fun getAllMahasiswa(): AllMahasiswaResponse


    @GET("mahasiswa/{id_mahasiswa}")
    suspend fun getMahasiswabyid(@Path("id_mahasiswa") idMahasiswa: String): MahasiswaDetailResponse


    @POST("mahasiswa/store")
    suspend fun insertMahasiswa(@Body mahasiswa: Mahasiswa)


    @PUT("mahasiswa/{id_mahasiswa}")
    suspend fun updateMahasiswa(
        @Path("id_mahasiswa") idMahasiswa: String,
        @Body mahasiswa: Mahasiswa
    )


    @DELETE("mahasiswa/{id_mahasiswa}")
    suspend fun deleteMahasiswa(@Path("id_mahasiswa") idMahasiswa: String): Response<Void>
}