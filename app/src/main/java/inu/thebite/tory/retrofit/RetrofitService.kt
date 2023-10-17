package inu.thebite.tory.retrofit

import inu.thebite.tory.model.center.CenterRequest
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassRequest
import inu.thebite.tory.model.childClass.ChildClassResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {

    @POST("/center/add")
    suspend fun addCenter(@Body addCenterRequest: CenterRequest): Call<CenterRequest>

    @GET("/center/list")
    suspend fun getCenterList(): List<CenterResponse>

    @DELETE("/center/{centerId}/delete")
    suspend fun deleteCenter(@Path("centerId") centerId: Long): Response<Void>

    @POST("/{centerId}/class/add")
    suspend fun addClass(@Path("centerId") centerId: Long, @Body AddChildClassRequest: ChildClassRequest): Call<ChildClassRequest>

    @GET("/class/list")
    suspend fun getAllClassList(): List<ChildClassResponse>

    @DELETE("/{centerId}/class/{classId}/delete")
    suspend fun deleteClass(@Path("centerId") centerId: Long, @Path("classId") classId: Long): Response<Void>
}