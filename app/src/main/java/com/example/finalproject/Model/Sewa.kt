package com.example.finalproject.Model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class Sewa(
    val idPembayaran : String,
    val idMahasiswa : String,
    val jumlah : String,
    val tglPembayaran: String,
    val statusPembayaran : String

)

@OptIn(InternalSerializationApi::class)
@Serializable
data class AllSewaResponse(
    val status : Boolean,
    val message : String,
    val data: List<Sewa>
)

@OptIn(InternalSerializationApi::class)
@Serializable
data class SewaDetailResponse(
    val status : Boolean,
    val message : String,
    val data: Sewa
)
