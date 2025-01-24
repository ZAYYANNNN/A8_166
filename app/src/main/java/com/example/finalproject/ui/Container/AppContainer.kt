package com.example.finalproject.ui.Container

import com.example.finalproject.Repository.BangunanRepository
import com.example.finalproject.Repository.KamarRepository
import com.example.finalproject.Repository.MahasiswaRepository
import com.example.finalproject.Repository.NetworkBangunanRepository
import com.example.finalproject.Repository.NetworkKamarRepository
import com.example.finalproject.Repository.NetworkMahasiswaRepository
import com.example.finalproject.Repository.NetworkSewaRepository
import com.example.finalproject.Repository.SewaRepository
import com.example.finalproject.service_api.BangunanService
import com.example.finalproject.service_api.KamarService
import com.example.finalproject.service_api.MahasiswaService
import com.example.finalproject.service_api.SewaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface AppContainer {
    val mahasiswaRepository: MahasiswaRepository
    val kamarRepository: KamarRepository
    val bangunanRepository: BangunanRepository
    val sewaRepository: SewaRepository
}

class StayContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/" // Base URL untuk semua endpoint
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // Mahasiswa
    private val mahasiswaService: MahasiswaService by lazy {
        retrofit.create(MahasiswaService::class.java)
    }
    override val mahasiswaRepository: MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(mahasiswaService)
    }

    // Kamar
    private val kamarService: KamarService by lazy {
        retrofit.create(KamarService::class.java)
    }
    override val kamarRepository: KamarRepository by lazy {
        NetworkKamarRepository(kamarService)
    }

    // Bangunan
    private val bangunanService: BangunanService by lazy {
        retrofit.create(BangunanService::class.java)
    }
    override val bangunanRepository: BangunanRepository by lazy {
        NetworkBangunanRepository(bangunanService)
    }

    private val sewaService: SewaService by lazy {
        retrofit.create(SewaService::class.java)
    }
    override val sewaRepository: SewaRepository by lazy {
        NetworkSewaRepository(sewaService)
    }

    

}