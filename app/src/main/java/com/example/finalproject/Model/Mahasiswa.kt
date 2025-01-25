package com.example.finalproject.Model

import kotlinx.serialization.*

@OptIn(InternalSerializationApi::class)
@Serializable
data class Mahasiswa(

    @SerialName("id_mahasiswa")
    val idMahasiswa : String,

    @SerialName("nama_mahasiswa")
    val nama : String,

    @SerialName("nomor_identitas")
    val nim : String,

    @SerialName("email")
    val email: String,

    @SerialName("nomor_telepon")
    val telp: String,

    @SerialName("id_kamar")
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