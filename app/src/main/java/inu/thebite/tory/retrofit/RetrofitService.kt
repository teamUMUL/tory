package inu.thebite.tory.retrofit

import inu.thebite.tory.model.center.CenterRequest
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassRequest
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.model.detail.DetailObjectResponse
import inu.thebite.tory.model.detail.DetailResponse
import inu.thebite.tory.model.domain.AddDomainRequest
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.model.image.UpdateImageListRequest
import inu.thebite.tory.model.lto.LtoGraphResponse
import inu.thebite.tory.model.lto.LtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.lto.UpdateLtoStatusRequest
import inu.thebite.tory.model.member.AddDirectorRequest
import inu.thebite.tory.model.member.AddTherapistRequest
import inu.thebite.tory.model.member.EditProfileRequest
import inu.thebite.tory.model.member.FindMemberIdRequest
import inu.thebite.tory.model.member.FindMemberIdResponse
import inu.thebite.tory.model.member.FindMemberPasswordRequest
import inu.thebite.tory.model.member.LoginResponse
import inu.thebite.tory.model.member.MemberLoginRequest
import inu.thebite.tory.model.member.MemberResponse
import inu.thebite.tory.model.member.TemporaryPasswordResponse
import inu.thebite.tory.model.member.UpdatePasswordRequest
import inu.thebite.tory.model.member.ValidationTokenResponse
import inu.thebite.tory.model.notice.AddCommentRequest
import inu.thebite.tory.model.notice.AutoCommentResponse
import inu.thebite.tory.model.notice.DateResponse
import inu.thebite.tory.model.notice.NoticeDatesResponse
import inu.thebite.tory.model.notice.NoticeResponse
import inu.thebite.tory.model.point.AddPointRequest
import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.model.sto.EtcRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoRequest
import inu.thebite.tory.model.sto.UpdateStoRoundRequest
import inu.thebite.tory.model.sto.UpdateStoStatusRequest
import inu.thebite.tory.model.student.AddStudentRequest
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.model.student.UpdateStudentDateRequest
import inu.thebite.tory.model.student.UpdateStudentRequest
import inu.thebite.tory.model.todo.TodoListRequest
import inu.thebite.tory.model.todo.TodoResponse
import inu.thebite.tory.model.todo.UpdateTodoList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    // center
    @POST("/center")
    suspend fun addCenter(@Body addCenterRequest: CenterRequest): Response<CenterResponse>

    @PATCH("/center/{centerId}")
    suspend fun updateCenter(@Path("centerId")  centerId: Long, @Body updateCenterRequest: CenterRequest): Response<CenterResponse>

    @GET("/centers")
    suspend fun getCenterList(): List<CenterResponse>

    @DELETE("/center/{centerId}")
    suspend fun deleteCenter(@Path("centerId") centerId: Long): Response<Boolean>

    // class
    @POST("/{centerId}/classes")
    suspend fun addClass(@Path("centerId") centerId: Long, @Body addChildClassRequest: ChildClassRequest): Response<ChildClassResponse>

    @PATCH("/classes/{classId}")
    suspend fun updateChildClass(@Path("classId") classId: Long, @Body updateChildClass: ChildClassRequest): Response<ChildClassResponse>

    @GET("/classes")
    suspend fun getAllClassList(): List<ChildClassResponse>

    /**
     * 센터별 반 api
     */
    @GET("/{centerId}/classes")
    suspend fun getClassListByCenter(@Path("centerId") centerId: Long): List<ChildClassResponse>

    @DELETE("/classes/{classId}")
    suspend fun deleteClass(@Path("classId") classId: Long): Response<Boolean>

    // student
    @POST("/{classId}/students")
    suspend fun addStudent(@Path("classId") classId: Long, @Body addStudentRequest: AddStudentRequest) : Response<StudentResponse>

    @PATCH("/students/{studentId}")
    suspend fun updateStudent(@Path("studentId") studentId: Long, @Body updateStudentRequest: UpdateStudentRequest): Response<StudentResponse>

    @PATCH("/students/{studentId}/startDate")
    suspend fun updateStudentStartDate(@Path("studentId") studentId: Long, @Body updateStudentDateRequest: UpdateStudentDateRequest) : Response<StudentResponse>

    @PATCH("/students/{studentId}/endDate")
    suspend fun updateStudentEndDate(@Path("studentId") studentId: Long, @Body updateStudentDateRequest: UpdateStudentDateRequest) : Response<StudentResponse>

    @GET("/{classId}/students")
    suspend fun getStudentList(@Path("classId") classId: Long) : List<StudentResponse>

    @DELETE("/students/{studentId}")
    suspend fun deleteStudent(@Path("studentId") studentId: Long) : Response<Boolean>

    // domain
    @POST("/domains/{centerId}")
    suspend fun addDomain(@Body addDomainRequest: AddDomainRequest, @Path("centerId") centerId: Long) : Response<DomainResponse>

    @GET("/domains/{centerId}")
    suspend fun getDomainList(@Path("centerId") centerId: Long) : List<DomainResponse>

    @DELETE("/domains/{domainId}")
    suspend fun deleteDomain(@Path("domainId") domainId: Long) : Response<Boolean>

    @PATCH("/domains/{domainId}")
    suspend fun updateDomain(@Path("domainId") domainId: Long, @Body addDomainRequest: AddDomainRequest) : Response<DomainResponse>

    // lto
    @POST("/{domainId}/ltos/{studentId}")
    suspend fun addLto(@Path("domainId") domainId: Long, @Body ltoRequest: LtoRequest, @Path("studentId") studentId: Long): Response<LtoResponse>

    @PATCH("/ltos/{ltoId}/status")
    suspend fun updateLtoStatus(@Path("ltoId") ltoId: Long, @Body updateLtoStatusRequest: UpdateLtoStatusRequest): Response<LtoResponse>

    @PATCH("/ltos/{ltoId}/hit/status")
    suspend fun updateLtoHitStatus(@Path("ltoId") ltoId: Long, @Body updateLtoStatusRequest: UpdateLtoStatusRequest): Response<LtoResponse>

    @PATCH("/ltos/{ltoId}")
    suspend fun updateLto(@Path("ltoId") ltoId: Long, @Body ltoRequest: LtoRequest) : Response<LtoResponse>

    @GET("/{studentId}/ltos")
    suspend fun getLtoList(@Path("studentId") studentId: Long): List<LtoResponse>

    /**
     * 학생 별로 LTO 리스트 가져오는 Api
     */
    @GET("/{domainId}/{studentId}/ltos")
    suspend fun getLtoListByStudent(@Path("domainId") domainId: Long, @Path("studentId") studentId: Long): List<LtoResponse>

    @GET("/ltos/{ltoId}/graphs")
    suspend fun getLtoGraph(@Path("ltoId") ltoId: Long) : List<LtoGraphResponse>

    @DELETE("/ltos/{ltoId}")
    suspend fun deleteLto(@Path("ltoId") ltoId: Long): Response<Boolean>

    /**
     * STO API
     */
    @POST("/{ltoId}/stos")
    suspend fun addSto(@Path("ltoId") ltoId: Long, @Body addStoRequest: AddStoRequest): Response<StoResponse>

    @PATCH("/stos/{stoId}/status")
    suspend fun updateStoStatus(@Path("stoId") stoId: Long, @Body updateStoStatusRequest: UpdateStoStatusRequest): Response<StoResponse>

    @PATCH("/stos/{stoId}/hit/status")
    suspend fun updateStoHitStatus(@Path("stoId") stoId: Long, @Body updateStoStatusRequest: UpdateStoStatusRequest): Response<StoResponse>

    @PATCH("/stos/{stoId}")
    suspend fun updateSto(@Path("stoId") stoId: Long, @Body updateStoRequest: UpdateStoRequest): Response<StoResponse>

    @PATCH("/stos/{stoId}/images")
    suspend fun updateImageList(@Path("stoId") stoId: Long, @Body updateImageListRequest: UpdateImageListRequest) : Response<StoResponse>

    @PATCH("/stos/{stoId}/round")
    suspend fun updateStoRound(@Path("stoId") stoId: Long, @Body updateStoRoundRequest: UpdateStoRoundRequest) : Response<StoResponse>

    @PATCH("/stos/{stoId}/hit/round")
    suspend fun updateStoHitRound(@Path("stoId") stoId: Long, @Body updateStoRoundRequest: UpdateStoRoundRequest) : Response<StoResponse>

    // 스트레스 지수 업데이트
    @PATCH("/stos/{stoId}/stress")
    suspend fun updateStressStatus(@Path("stoId") stoId: Long, @Body etcRequest: EtcRequest) : Response<StoResponse>

    // 집중도 업데이트
    @PATCH("/stos/{stoId}/concentration")
    suspend fun updateConcentration(@Path("stoId") stoId: Long, @Body etcRequest: EtcRequest) : Response<StoResponse>

    // 특이 사항 업데이트
    @PATCH("/stos/{stoId}/significant")
    suspend fun updateSignificant(@Path("stoId") stoId: Long, @Body etcRequest: EtcRequest) : Response<StoResponse>

    // 돌발 상황 선택
    @PATCH("/stos/{stoId}/selection/lc")
    suspend fun updateLooseCannons(@Path("stoId") stoId: Long, @Body etcRequest: EtcRequest) : Response<StoResponse>

    // 돌발 상황 해제
    @PATCH("/stos/{stoId}/removal/lc")
    suspend fun removeLooseCannon(@Path("stoId") stoId: Long, @Body etcRequest: EtcRequest) : Response<StoResponse>

    // 돌발 상황 리스트 가져오기
//    @GET("/stos/{stoId}/lc")
//    suspend fun getLooseCannonListBySto(@Path("stoId") stoId: Long) : List<String>


    @GET("/{studentId}/stos")
    suspend fun getStoList(@Path("studentId") studentId: Long): List<StoResponse>

    /**
     * LTO별로 STO 리스트 가져오기 api
     */
    @GET("/{ltoId}/stos")
    suspend fun getStoListByLto(@Path("ltoId") ltoId: Long) : List<StoResponse>

    @DELETE("/stos/{stoId}")
    suspend fun deleteSto(@Path("stoId") stoId: Long): Response<Boolean>

    // image
    @GET("/images")
    suspend fun getImageList(): List<ImageResponse>

    // point
    @POST("/stos/{stoId}/points")
    suspend fun addPoint(@Path("stoId") stoId: Long, @Body addPointRequest: AddPointRequest) : Response<Void>

//    @PATCH("/stos/{stoId}/points")
//    suspend fun updatePoint(@Path("stoId") stoId: Long, @Body updatePointRequest: UpdatePointRequest) : Response<List<PointResponse>>

    @DELETE("/stos/{stoId}/points")
    suspend fun deletePoint(@Path("stoId") stoId: Long) : Response<Void>

    @GET("/stos/{stoId}/points")
    suspend fun getPointList(@Path("stoId") stoId: Long) : List<String>

    /**
     * TodoList Api
     */
    @POST("/todos/{studentId}")
    suspend fun addTodoList(@Path("studentId") studentId: Long, @Body todoListRequest: TodoListRequest) : Response<TodoResponse>

    // TodoList update == TodoList delete
    @PATCH("/todos/{studentId}")
    suspend fun updateTodoList(@Path("studentId") studentId: Long, @Body updateTodoList: UpdateTodoList) : Response<TodoResponse>

    @GET("/todos/{studentId}")
    suspend fun getTodoList(@Path("studentId") studentId: Long) : Response<TodoResponse>

    /**
     * Notice Api = 오늘의 총평 부분 알림장
     */
    @PATCH("/notices/{studentId}")
    suspend fun updateComment(@Path("studentId") studentId: Long, @Query("year") year: String, @Query("month") month: Int, @Query("date") date: String, @Body addCommentRequest: AddCommentRequest) : Response<NoticeResponse>

    @GET("/notices/{studentId}/dateList")
    suspend fun getNoticeDateList(@Path("studentId") studentId: Long, @Query("year") year: String, @Query("month") month: Int) : List<DateResponse>

    @GET("/notices/{studentId}")
    suspend fun getNotice(@Path("studentId") studentId: Long, @Query("year") year: String, @Query("month") month: Int, @Query("date") date: String) : Response<NoticeResponse>

    @GET("/notices/{studentId}/dates")
    suspend fun getNoticeDates(@Path("studentId") studentId: Long) : Response<List<NoticeDatesResponse>>

    // 월간 보고서 조회
    @GET("/notices/{studentId}/monthly")
    suspend fun getMonthlyNotice(@Path("studentId") studentId: Long, @Query("year") year: String, @Query("month") month: Int) : Response<List<NoticeResponse>>

    // notice 자동 멘트 생성
    @GET("/notices/{studentId}/auto/comment")
    suspend fun getNoticeAutoComment(@Path("studentId") studentId: Long, @Query("year") year: String, @Query("month") month: Int, @Query("date") date: String) : Response<AutoCommentResponse>

    /** http://{서버 ip}:8081/notices/{studentId}/reports?year=2023&month=12&date=12*/
    @POST("/notices/{studentId}/reports")
    suspend fun showWebView(@Path("studentId") studentId: Long, @Query("year") year: String, @Query("month") month: Int, @Query("date") date: String) : Response<Void>

    /**
     * Detail Api = 각 LTO 상세 부분 알림장
     */
    @POST("/details/{studentId}")
    suspend fun addDetail(@Path("studentId") studentId: Long, @Query("year") year: String, @Query("month") month: Int, @Query("date") date: String, @Query("stoId") stoId: Long) : Response<DetailResponse>

    @PATCH("/details/{studentId}")
    suspend fun updateComment(@Path("studentId") studentId: Long, @Query("year") year: String, @Query("month") month: Int, @Query("date") date: String, @Query("stoId") stoId: Long, @Body addCommentRequest: AddCommentRequest) : Response<DetailResponse>

    @GET("/details/{studentId}")
    suspend fun getDetailList(@Path("studentId") studentId: Long, @Query("year") year: String, @Query("month") month: Int, @Query("date") date: String) : Response<List<DetailObjectResponse>>

    // Detail 멘트 자동 생성
    @GET("/details/{studentId}/{ltoId}/auto/comment")
    suspend fun getDetailAutoComment(@Path("studentId") studentId: Long, @Path("ltoId") ltoId: Long, @Query("year") year: String, @Query("month") month: Int, @Query("date") date: String) : Response<AutoCommentResponse>

    /**
     * Member Api
     */

    // 원장님 회원가입
    @POST("/members/join")
    suspend fun joinPrincipalUser(@Body addDirectorRequest: AddDirectorRequest) : Response<Boolean>

    // 치료사 회원가입
    @POST("/members/therapist/join")
    suspend fun joinTherapistUser(@Body addTherapistRequest: AddTherapistRequest) : Response<Boolean>

    @POST("/members/login")
    suspend fun login(@Body memberLoginRequest: MemberLoginRequest) : Response<LoginResponse>

    @POST("/valid/token")
    suspend fun validationToken() : Response<ValidationTokenResponse>

    // 비밀번호 변경
    @POST("/members/password")
    suspend fun updatePassword(@Body updatePasswordRequest: UpdatePasswordRequest) : Response<Boolean>

    // 아이디 찾기
    @POST("/members/find/id")
    suspend fun findMemberId(@Body findMemberIdRequest: FindMemberIdRequest) : Response<FindMemberIdResponse>

    // 비밀번호 찾기
    @POST("/members/find/password")
    suspend fun findMemberPassword(@Body findMemberPasswordRequest: FindMemberPasswordRequest) : Response<TemporaryPasswordResponse>

    // 프로필 편집
    @PATCH("/edit/profile")
    suspend fun editProfile(@Body editProfileRequest: EditProfileRequest) : Response<MemberResponse>

    // 프로필 조회
    @GET("/profile")
    suspend fun getProfile() : Response<MemberResponse>

}
