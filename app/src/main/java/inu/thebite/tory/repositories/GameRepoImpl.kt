package inu.thebite.tory.repositories

import inu.thebite.tory.database.GameDatabase
import inu.thebite.tory.database.GameEntity
import kotlinx.coroutines.flow.Flow

class GameRepoImpl(private val database: GameDatabase):GameRepo {
    private val gameDao = database.gameDao()

    override suspend fun addGameResult(game: GameEntity) = gameDao.addGameResult(game)


    override suspend fun getGameResults(): Flow<List<GameEntity>> = gameDao.getGameResult()

    override suspend fun deleteGameResult(game: GameEntity) = gameDao.deleteGameResult(game)

    override suspend fun updateGameResult(game: GameEntity) = gameDao.updateGameResult(game)

}