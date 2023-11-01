package inu.thebite.tory.retrofit

import inu.thebite.tory.model.center.CenterRequest
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassRequest
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.model.domain.AddDomainRequest
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.model.image.UpdateImageListRequest
import inu.thebite.tory.model.lto.LtoGraphResponse
import inu.thebite.tory.model.lto.LtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest
import inu.thebite.tory.model.point.AddPointRequest
import inu.thebite.tory.model.point.PointResponse
import inu.thebite.tory.model.point.UpdatePointRequest
import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoRequest
import inu.thebite.tory.model.sto.UpdateStoStatusRequest
import inu.thebite.tory.model.student.AddStudentRequest
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.model.student.UpdateStudentDateRequest
import inu.thebite.tory.model.student.UpdateStudentRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {

    // center
    @POST("/centers")
    suspend fun addCenter(@Body addCenterRequest: CenterRequest): Response<CenterResponse>

    @PATCH("/centers/{centerId}")
    suspend fun updateCenter(@Path("centerId")  centerId: Long, @Body updateCenterRequest: CenterRequest): Response<CenterResponse>

    @GET("/centers")
    suspend fun getCenterList(): List<CenterResponse>

    @DELETE("/centers/{centerId}")
    suspend fun deleteCenter(@Path("centerId") centerId: Long): Response<Void>

    // class
    @POST("/{centerId}/classes")
    suspend fun addClass(@Path("centerId") centerId: Long, @Body addChildClassRequest: ChildClassRequest): Response<ChildClassResponse>

    @PATCH("/classes/{classId}")
    suspend fun updateChildClass(@Path("classId") classId: Long, @Body updateChildClass: ChildClassRequest): Response<ChildClassResponse>

    @GET("/classes")
    suspend fun getAllClassList(): List<ChildClassResponse>

    @DELETE("/classes/{classId}")
    suspend fun deleteClass(@Path("classId") classId: Long): Response<Void>

    // student
    @POST("/{classId}/students")
    suspend fun addStudent(@Path("classId") classId: Long, @Body addStudentRequest: AddStudentRequest) : Response<StudentResponse>

    @PATCH("/students/{studentId}")
    suspend fun updateStudent(@Path("studentId") studentId: Long, @Body updateStudentRequest: UpdateStudentRequest): Response<StudentResponse>

    @PATCH("/students/{studentId}/startDate")
    suspend fun updateStudentStartDate(@Path("studentId") studentId: Long, @Body updateStudentDateRequest: UpdateStudentDateRequest) : Response<StudentResponse>

    @PATCH("/students/{studentId}/endDate")
    suspend fun updateStudentEndDate(@Path("studentId") studentId: Long, @Body updateStudentDateRequest: UpdateStudentDateRequest) : Response<StudentResponse>

    @GET("/students")
    suspend fun getStudentList() : List<StudentResponse>

    @DELETE("/students/{studentId}")
    suspend fun deleteStudent(@Path("studentId") studentId: Long) : Response<Void>

    // domain
    @POST("/domains")
    suspend fun addDomain(@Body addDomainRequest: AddDomainRequest) : Response<DomainResponse>

    @GET("/domains")
    suspend fun getDomainList() : List<DomainResponse>

    @DELETE("/domains/{domainId}")
    suspend fun deleteDomain(@Path("domainId") domainId: Long) : Response<Void>

    // lto
    @POST("/{domainId}/ltos")
    suspend fun addLto(@Path("domainId") domainId: Long, @Body ltoRequest: LtoRequest): Response<LtoResponse>

    @PATCH("/ltos/{ltoId}/status")
    suspend fun updateLtoStatus(@Path("ltoId") ltoId: Long, @Body updateLtoStatusRequest: UpdateLtoStatusRequest): Response<LtoResponse>

    @PATCH("/ltos/{ltoId}/hit/status")
    suspend fun updateLtoHitStatus(@Path("ltoId") ltoId: Long, @Body updateLtoStatusRequest: UpdateLtoStatusRequest): Response<LtoResponse>

    @PATCH("/ltos/{ltoId}")
    suspend fun updateLto(@Path("ltoId") ltoId: Long, @Body ltoRequest: LtoRequest) : Response<LtoResponse>

    @GET("/ltos")
    suspend fun getLtoList(): List<LtoResponse>

    @GET("/ltos/{ltoId}/graphs")
    suspend fun getLtoGraph() : List<LtoGraphResponse>

    @DELETE("/ltos/{ltoId}")
    suspend fun deleteLto(@Path("ltoId") ltoId: Long): Response<Void>

    // sto
    @POST("/{ltoId}/stos")
    suspend fun addSto(@Path("ltoId") ltoId: Long, @Body addStoRequest: AddStoRequest): Response<StoResponse>

    @PATCH("/stos/{stoId}/status")
    suspend fun updateStoStatus(@Path("stoId") stoId: Long, @Body updateStoStatusRequest: UpdateStoStatusRequest): Response<StoResponse>

    @PATCH("/stos/{stoId}/hit/status")
    suspend fun updateStoHitStatus(@Path("stoId") stoId: Long, @Body updateStoStatusRequest: UpdateStoStatusRequest): Response<StoResponse>

    @PATCH("/stos/{stoId}")
    suspend fun updateSto(@Path("stoId") stoId: Long, @Body updateStoRequest: UpdateStoRequest): Response<StoResponse>

    @PATCH("/stos/{stoId}/images")
    suspend fun updateImageList(@Path("stoId") stoId: Long, @Body updateImageListRequest: UpdateImageListRequest) : Response<ImageResponse>

    @PATCH("/stos/{stoId}/round")
    suspend fun updateStoRound(@Path("stoId") stoId: Long) : Response<StoResponse>

    @GET("/stos")
    suspend fun getStoList(): List<StoResponse>

    @DELETE("/stos/{stoId}")
    suspend fun deleteSto(@Path("stoId") stoId: Long): Response<Void>

    // image
    @GET("/images")
    suspend fun getImageList(): List<ImageResponse>

    // point
    @POST("/stos/{stoId}/points")
    suspend fun addPoint(@Path("stoId") stoId: Long, @Body addPointRequest: AddPointRequest) : Response<Void>

//    @PATCH("/stos/{stoId}/points")
//    suspend fun updatePoint(@Path("stoId") stoId: Long, @Body updatePointRequest: UpdatePointRequest) : Response<List<PointResponse>>

    @DELETE("stos/{stoId}/points")
    suspend fun deletePoint(@Path("stoId") stoId: Long) : Response<Void>

    @GET("/stos/{stoId}/points")
    suspend fun getPointList(@Path("stoId") stoId: Long) : List<String>
}
