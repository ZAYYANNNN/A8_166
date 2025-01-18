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


interface MhsContainer {
    val mahasiswaRepository: MahasiswaRepository
}
interface KmrContainer {
    val kamarRepository: KamarRepository
}
interface BgnContainer {
    val bangunanRepository: BangunanRepository
}
interface PayContainer {
    val sewaRepository: SewaRepository
}

class MahasiswaContainer:MhsContainer{
    private val baseUrl = "http://10.0.2.2:3000/api/mahasiswa/"//ganti localhost
    private val json = Json{ignoreUnknownKeys = true}
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()
    private val mahasiswaService: MahasiswaService by lazy {
        retrofit.create(MahasiswaService::class.java)
    }
    override val mahasiswaRepository: MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(mahasiswaService)
    }
}

class KamarContainer:KmrContainer{
    private val baseUrl = "http://10.0.2.2:3000/api/kamar/"//ganti localhost
    private val json = Json{ignoreUnknownKeys = true}
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()
    private val kamarService: KamarService by lazy {
        retrofit.create(KamarService::class.java)
    }
    override val kamarRepository: KamarRepository by lazy {
        NetworkKamarRepository(kamarService)
    }
}

class BangunanContainer:BgnContainer{
    private val baseUrl = "http://10.0.2.2:3000/api/bangunan/"//ganti localhost
    private val json = Json{ignoreUnknownKeys = true}
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()
    private val bangunanService: BangunanService by lazy {
        retrofit.create(BangunanService::class.java)
    }
    override val bangunanRepository: BangunanRepository by lazy {
        NetworkBangunanRepository(bangunanService)
    }
}

class SewaContainer:PayContainer{
    private val baseUrl = "http://10.0.2.2:3000/api/sewa/"//ganti localhost
    private val json = Json{ignoreUnknownKeys = true}
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()
    private val sewaService: SewaService by lazy {
        retrofit.create(SewaService::class.java)
    }
    override val sewaRepository: SewaRepository by lazy {
        NetworkSewaRepository(sewaService)
    }
}



