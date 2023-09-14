package inu.thebite.tory.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDao {
    @Insert
    fun addChild(child:ChildEntity)

    @Query("SELECT * FROM `child`")
    fun getChild(): Flow<List<ChildEntity>>

    @Delete
    fun deleteChild(child:ChildEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateChild(child:ChildEntity)
}