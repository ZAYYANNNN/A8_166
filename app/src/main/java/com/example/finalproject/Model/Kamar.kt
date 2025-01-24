package com.example.finalproject.Model

import kotlinx.serialization.*

@OptIn(InternalSerializationApi::class)
@Serializable
data class Kamar(

    @SerialName("id_kamar")
    val idKamar : String,

    @SerialName("no_kamar")
    val noKamar : String,

    @SerialName("kapasitas")
    val kapasitas : String,

    @SerialName("status_kamar")
    val statusKamar: String,

    @SerialName("id_bangunan")
    val idBangunan : String

)

@OptIn(InternalSerializationApi::class)
@Serializable
data class AllKamarResponse(
    val status : Boolean,
    val message : String,
    val data: List<Kamar>
)

@OptIn(InternalSerializationApi::class)
@Serializable
data class KamarDetailResponse(
    val status : Boolean,
    val message : String,
    val data: Kamar
)
