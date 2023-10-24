package inu.thebite.tory.database.education

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EducationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createEducation(education: EducationEntity)

    @Update
    suspend fun updateEducation(education: EducationEntity)

    //suspend와 Flow는 같이 사용할 수 없음
    @Query("SELECT * FROM `Education`")
    fun getAllEducations(): Flow<List<EducationEntity>>

    @Delete
    suspend fun deleteEducation(education: EducationEntity)
}