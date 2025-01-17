package com.example.finalproject.Model

import kotlinx.serialization.*

@OptIn(InternalSerializationApi::class)
@Serializable
data class Kamar(
    val idKamar : String,
    val noKamar : String,
    val kapasitas : String,
    val statusKamar: String,
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
