package inu.thebite.tory.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface STODao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSTO(sto: STOEntity)

    @Update
    suspend fun updateSTO(sto: STOEntity)

//    @Query("SELECT * FROM `STO`")
//    suspend fun getAllSTOs(): Flow<List<STOEntity>>

    @Query("SELECT * FROM `STO` WHERE stoId = :stoId")
    fun getSTOById(stoId: Int): Flow<STOEntity>

    @Delete
    suspend fun deleteSTO(sto: STOEntity)

    @Query("DELETE FROM `STO`")
    suspend fun deleteAllData()
    @Query("SELECT * FROM `STO` WHERE className = :className " +
            "AND (childName = :childName OR :childName IS NULL) " +
            "AND (selectedDEV = :selectedDEV OR :selectedDEV IS NULL) " +
            "AND (selectedLTO = :selectedLTO OR :selectedLTO IS NULL) ")
    fun getSTOsByCriteria(
        className: String?,
        childName: String?,
        selectedDEV: String?,
        selectedLTO: String?,
    ): Flow<List<STOEntity>>

    @Query("SELECT stoId FROM `STO` WHERE " +
            "(className = :className OR :className IS NULL) " +
            "AND (childName = :childName OR :childName IS NULL) " +
            "AND (selectedDEV = :selectedDEV OR :selectedDEV IS NULL) " +
            "AND (selectedLTO = :selectedLTO OR :selectedLTO IS NULL) " +
            "AND (stoName = :stoName OR :stoName IS NULL)")
    fun getSTOIdByCriteria(
        className: String?,
        childName: String?,
        selectedDEV: String?,
        selectedLTO: String?,
        stoName: String?
    ): Flow<Int>


    @Query("DELETE FROM `STO` WHERE " +
            "(className = :childClass OR :childClass IS NULL) " +
            "AND (childName = :childName OR :childName IS NULL) " +
            "AND (selectedDEV = :selectedDEV OR :selectedDEV IS NULL) " +
            "AND (selectedLTO = :selectedLTO OR :selectedLTO IS NULL)")
    suspend fun deleteSTOsByCriteria(
        childClass: String?,
        childName: String?,
        selectedDEV: String?,
        selectedLTO: String?
    )
    // 다른 필요한 쿼리 메서드 추가 가능
}