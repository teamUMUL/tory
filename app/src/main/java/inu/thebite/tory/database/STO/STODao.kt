//package inu.thebite.tory.database.STO
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Update
//import kotlinx.coroutines.flow.Flow
//
//
//
//@Dao
//interface STODao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSTO(sto: STOEntity)
//
//    @Update
//    suspend fun updateSTO(sto: STOEntity)
//
//    //suspend와 Flow는 같이 사용할 수 없음
//    @Query("SELECT * FROM `STO`")
//    fun getAllSTOs(): Flow<List<STOEntity>>
//
//    @Query("SELECT * FROM `STO` WHERE stoId = :stoId")
//    fun getSTOById(stoId: Int): Flow<STOEntity>
//
//    @Delete
//    suspend fun deleteSTO(sto: STOEntity)
//
//    @Query("DELETE FROM `STO`")
//    suspend fun deleteAllData()
//
//    @Query("DELETE FROM `STO` WHERE " +
//            "(className = :childClass OR :childClass IS NULL) " +
//            "AND (childName = :childName OR :childName IS NULL) " +
//            "AND (selectedDEV = :selectedDEV OR :selectedDEV IS NULL) " +
//            "AND (selectedLTO = :selectedLTO OR :selectedLTO IS NULL)")
//    suspend fun deleteSTOsByCriteria(
//        childClass: String?,
//        childName: String?,
//        selectedDEV: String?,
//        selectedLTO: String?
//    )
//    // 다른 필요한 쿼리 메서드 추가 가능
//}