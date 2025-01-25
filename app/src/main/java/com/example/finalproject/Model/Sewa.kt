package com.example.finalproject.Model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class Sewa(

    @SerialName("id_pembayaran")
    val idPembayaran : String,

    @SerialName("id_mahasiswa")
    val idMahasiswa : String,

    @SerialName("jumlah")
    val jumlah : String,

    @SerialName("tgl_pembayaran")
    val tglPembayaran: String,

    @SerialName("status_pembayaran")
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
