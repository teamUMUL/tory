package inu.thebite.tory.database.LTO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LTODao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLTO(lto: LTOEntity)

    @Update
    suspend fun updateLTO(lto: LTOEntity)

    //suspend와 Flow는 같이 사용할 수 없음
    @Query("SELECT * FROM `LTO`")
    fun getAllLTOs(): Flow<List<LTOEntity>>

    @Delete
    suspend fun deleteLTO(lto: LTOEntity)
}