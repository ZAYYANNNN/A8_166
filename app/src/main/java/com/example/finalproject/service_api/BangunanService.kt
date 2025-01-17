package com.example.finalproject.service_api

import com.example.finalproject.Model.AllBangunanResponse
import com.example.finalproject.Model.Bangunan
import com.example.finalproject.Model.BangunanDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BangunanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getAllBangunan(): AllBangunanResponse

    @GET("{idBangunan}")
    suspend fun getBangunanbyid(@Path("idBangunan") idBangunan:String): BangunanDetailResponse

    @POST("store")
    suspend fun insertBangunan(@Body bangunan: Bangunan)

    @PUT("{idBangunan}")
    suspend fun updateBangunan(@Path("idBangunan")idBangunan: String, @Body bangunan: Bangunan)

    @DELETE("{idBangunan}")
    suspend fun deleteBangunan(@Path("idBangunan") idBangunan:String): Response<Void>

}