package inu.thebite.tory.retrofit

import inu.thebite.tory.model.center.CenterRequest
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassRequest
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.model.domain.AddDomainRequest
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.model.lto.LtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest
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
    @POST("/center/add")
    suspend fun addCenter(@Body addCenterRequest: CenterRequest): Response<CenterResponse>

    @PATCH("/center/{centerId}/update")
    suspend fun updateCenter(@Path("centerId")  centerId: Long, @Body updateCenterRequest: CenterRequest): Response<CenterResponse>

    @GET("/center/list")
    suspend fun getCenterList(): List<CenterResponse>

    @DELETE("/center/{centerId}/delete")
    suspend fun deleteCenter(@Path("centerId") centerId: Long): Response<Void>

    // class
    @POST("/{centerId}/class/add")
    suspend fun addClass(@Path("centerId") centerId: Long, @Body addChildClassRequest: ChildClassRequest): Response<ChildClassResponse>

    @PATCH("/class/{classId}/update")
    suspend fun updateChildClass(@Path("classId") classId: Long, @Body updateChildClass: ChildClassRequest): Response<ChildClassResponse>

    @GET("/class/list")
    suspend fun getAllClassList(): List<ChildClassResponse>

    @DELETE("/class/{classId}/delete")
    suspend fun deleteClass(@Path("classId") classId: Long): Response<Void>

    // student
    @POST("/{classId}/student/add")
    suspend fun addStudent(@Path("classId") classId: Long, @Body addStudentRequest: AddStudentRequest) : Response<StudentResponse>

    @PATCH("/student/{studentId}/update")
    suspend fun updateStudent(@Path("studentId") studentId: Long, @Body updateStudentRequest: UpdateStudentRequest): Response<StudentResponse>

    @PATCH("/student/{studentId}/startDate/update")
    suspend fun updateStudentStartDate(@Path("studentId") studentId: Long, @Body updateStudentDateRequest: UpdateStudentDateRequest) : Response<StudentResponse>

    @PATCH("/student/{studentId}/endDate/update")
    suspend fun updateStudentEndDate(@Path("studentId") studentId: Long, @Body updateStudentDateRequest: UpdateStudentDateRequest) : Response<StudentResponse>

    @GET("/student/list")
    suspend fun getStudentList() : List<StudentResponse>

    @DELETE("/student/{studentId}/delete")
    suspend fun deleteStudent(@Path("studentId") studentId: Long) : Response<Void>

    // domain
    @POST("/domain/add")
    suspend fun addDomain(@Body addDomainRequest: AddDomainRequest) : Response<DomainResponse>

    @GET("/domain/list")
    suspend fun getDomainList() : List<DomainResponse>

    @DELETE("/{domainId}/delete")
    suspend fun deleteDomain(@Path("domainId") domainId: Long) : Response<Void>

    // lto
    @POST("/{domainId}/lto/add")
    suspend fun addLto(@Path("domainId") domainId: Long, @Body ltoRequest: LtoRequest): Response<LtoResponse>

    @PATCH("/lto/{ltoId}/status/update")
    suspend fun updateLtoStatus(@Path("ltoId") ltoId: Long, @Body updateLtoStatusRequest: UpdateLtoStatusRequest): Response<LtoResponse>

    @PATCH("/lto/{ltoId}/hit/status/update")
    suspend fun updateLtoHitStatus(@Path("ltoId") ltoId: Long, @Body updateLtoStatusRequest: UpdateLtoStatusRequest): Response<LtoResponse>

    @PATCH("/lto/{ltoId}/update")
    suspend fun updateLto(@Path("ltoId") ltoId: Long, @Body ltoRequest: LtoRequest) : Response<LtoResponse>

    @GET("/lto/list")
    suspend fun getLtoList(): List<LtoResponse>

    @DELETE("/lto/{ltoId}/delete")
    suspend fun deleteLto(@Path("ltoId") ltoId: Long): Response<Void>

    // sto
    @POST("/{ltoId}/sto/add")
    suspend fun addSto(@Path("ltoId") ltoId: Long, @Body addStoRequest: AddStoRequest): Response<StoResponse>

    @PATCH("/sto/{stoId}/status/update")
    suspend fun updateStoStatus(@Path("stoId") stoId: Long, @Body updateStoStatusRequest: UpdateStoStatusRequest): Response<StoResponse>

    @PATCH("/sto/{stoId}/hit/status/update")
    suspend fun updateStoHitStatus(@Path("stoId") stoId: Long, @Body updateStoStatusRequest: UpdateStoStatusRequest): Response<StoResponse>

    @PATCH("/sto/{stoId}/update")
    suspend fun updateSto(@Path("stoId") stoId: Long, @Body updateStoRequest: UpdateStoRequest): Response<StoResponse>

    // need a code to update image url list

    @GET("/sto/list")
    suspend fun getStoList(): List<StoResponse>

    @DELETE("/sto/{stoId}/delete")
    suspend fun deleteSto(@Path("stoId") stoId: Long): Response<Void>

    // image
    @GET("/image/list")
    suspend fun getImageList(): List<ImageResponse>

}
