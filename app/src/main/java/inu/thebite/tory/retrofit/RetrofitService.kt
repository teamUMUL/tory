package inu.thebite.tory.retrofit

import inu.thebite.tory.database.Center.CenterEntity
import inu.thebite.tory.model.Center
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {

    @POST("/center/add")
    suspend fun addCenter(@Body center: Center): Call<Center>

    @GET("/center/list")
    suspend fun getCenterList(): List<Center>

    @DELETE("/center/{centerName}/delete")
    suspend fun deleteCenter(@Path("centerName") name: String): Response<Void>
}