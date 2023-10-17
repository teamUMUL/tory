//package inu.thebite.tory.database.ChildInfo
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
//@Dao
//interface ChildInfoDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertChildInfo(childInfo: ChildInfoEntity)
//
//    @Update
//    suspend fun updateChildInfo(childCLass: ChildInfoEntity)
//
//    //suspend와 Flow는 같이 사용할 수 없음
//    @Query("SELECT * FROM `ChildInfo`")
//    fun getAllChildInfos(): Flow<List<ChildInfoEntity>>
//
//    @Delete
//    suspend fun deleteChildInfo(childCLass: ChildInfoEntity)
//}
//
