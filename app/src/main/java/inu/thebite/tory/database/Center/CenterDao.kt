//package inu.thebite.tory.database.Center
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Update
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface CenterDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCenter(center: CenterEntity)
//
//    @Update
//    suspend fun updateCenter(center: CenterEntity)
//
//    //suspend와 Flow는 같이 사용할 수 없음
//    @Query("SELECT * FROM `Center`")
//    fun getAllCenters(): Flow<List<CenterEntity>>
//
//    @Delete
//    suspend fun deleteCenter(center: CenterEntity)
//}