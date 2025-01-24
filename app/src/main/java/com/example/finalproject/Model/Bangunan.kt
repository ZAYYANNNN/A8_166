package com.example.finalproject.Model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class Bangunan(
    @SerialName("id_bangunan")
    val idBangunan : String,

    @SerialName("nama_bangunan")
    val namaBangunan : String,

    @SerialName("jumlah_lantai")
    val jumlahLantai : String,

    @SerialName("alamat")
    val alamat: String,

)

@OptIn(InternalSerializationApi::class)
@Serializable
data class AllBangunanResponse(
    val status : Boolean,
    val message : String,
    val data: List<Bangunan>
)

@OptIn(InternalSerializationApi::class)
@Serializable
data class BangunanDetailResponse(
    val status : Boolean,
    val message : String,
    val data: Bangunan
)