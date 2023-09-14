package inu.thebite.tory.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Insert
    fun addGameResult(game:GameEntity)

    @Query("SELECT * FROM `games`")
    fun getGameResult():Flow<List<GameEntity>>

    @Delete
    fun deleteGameResult(game:GameEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGameResult(game:GameEntity)
}