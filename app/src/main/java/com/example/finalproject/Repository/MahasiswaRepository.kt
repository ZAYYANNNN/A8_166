package com.example.finalproject.Repository

import com.example.finalproject.Model.AllMahasiswaResponse
import com.example.finalproject.Model.Mahasiswa
import com.example.finalproject.service_api.MahasiswaService
import java.io.IOException

interface MahasiswaRepository{
    suspend fun getMahasiswa(): AllMahasiswaResponse

    suspend fun  insertMahasiswa(mahasiswa: Mahasiswa)

    suspend fun updateMahasiswa(idMahasiswa: String, mahasiswa: Mahasiswa)

    suspend fun  deleteMahasiswa(idMahasiswa: String)

    suspend fun getMahasiswabyid(idMahasiswa: String): Mahasiswa
}

class NetworkMahasiswaRepository(
    private val mahasiswaApiService: MahasiswaService
) : MahasiswaRepository {
    override suspend fun getMahasiswa(): AllMahasiswaResponse =
        mahasiswaApiService.getAllMahasiswa()


    override suspend fun insertMahasiswa(mahasiswa: Mahasiswa) {
        mahasiswaApiService.insertMahasiswa(mahasiswa)
    }

    override suspend fun updateMahasiswa(idMahasiswa: String, mahasiswa: Mahasiswa) {
        mahasiswaApiService.updateMahasiswa(idMahasiswa, mahasiswa)
    }

    override suspend fun deleteMahasiswa(idMahasiswa: String) {
        try {
            val response = mahasiswaApiService.deleteMahasiswa(idMahasiswa)
            if (!response.isSuccessful) { // Corrected spelling
                throw IOException(
                    "Failed to delete Mahasiswa. HTTP Status Code: " + "${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun getMahasiswabyid(idMahasiswa: String): Mahasiswa {
        return mahasiswaApiService.getMahasiswabyid(idMahasiswa).data
    }

}