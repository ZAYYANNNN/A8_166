package com.example.finalproject.Model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class Bangunan(
    val idBangunan : String,
    val namaBangunan : String,
    val jumlahLantai : String,
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