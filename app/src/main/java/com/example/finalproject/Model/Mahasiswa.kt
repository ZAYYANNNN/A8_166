package com.example.finalproject.Model

import kotlinx.serialization.*

@OptIn(InternalSerializationApi::class)
@Serializable
data class Mahasiswa(
    val idMahasiswa : String,
    val nama : String,
    val nim : String,
    val email: String,
    val telp: String,
    val idKamar : String

)

@OptIn(InternalSerializationApi::class)
@Serializable
data class AllMahasiswaResponse(
    val status : Boolean,
    val message : String,
    val data: List<Mahasiswa>
)

@OptIn(InternalSerializationApi::class)
@Serializable
data class MahasiswaDetailResponse(
    val status : Boolean,
    val message : String,
    val data: Mahasiswa
)