package inu.thebite.tory.retrofit

import inu.thebite.tory.model.center.CenterRequest
import inu.thebite.tory.model.childClass.ChildClassRequest
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
    suspend fun getCenterList(): List<CenterRequest>

    @DELETE("/center/{centerName}/delete")
    suspend fun deleteCenter(@Path("centerName") name: String): Response<Void>

    @POST("/{centerId}/class/add")
    suspend fun addClass(@Path("centerId") centerId: Long, @Body AddChildClassRequest: ChildClassRequest): Call<ChildClassRequest>

    @GET("/{centerId}/class/list")
    suspend fun getClassList(@Path("centerId") centerId: Long): List<ChildClassRequest>

    @DELETE("/{centerId}/class/{classId}/delete")
    suspend fun deleteClass(@Path("centerId") centerId: Long, @Path("classId") classId: Long)
}