package inu.thebite.tory.repositories

import inu.thebite.tory.database.GameEntity
import kotlinx.coroutines.flow.Flow

interface GameRepo {
    suspend fun addGameResult(game:GameEntity)
    suspend fun getGameResults():Flow<List<GameEntity>>
    suspend fun deleteGameResult(game:GameEntity)
    suspend fun updateGameResult(game:GameEntity)
}