package inu.thebite.tory.database.ChildClass

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildClassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChildClass(childCLass: ChildClassEntity)

    @Update
    suspend fun updateChildClass(childCLass: ChildClassEntity)

    //suspend와 Flow는 같이 사용할 수 없음
    @Query("SELECT * FROM `ChildClass`")
    fun getAllChildClasses(): Flow<List<ChildClassEntity>>

    @Delete
    suspend fun deleteChildClass(childCLass: ChildClassEntity)
}