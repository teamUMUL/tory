package inu.thebite.tory.repositories

import inu.thebite.tory.database.ChildDatabase
import inu.thebite.tory.database.ChildEntity
import inu.thebite.tory.database.GameDatabase
import inu.thebite.tory.database.GameEntity
import kotlinx.coroutines.flow.Flow

class ChildRepoImpl(private val database: ChildDatabase):ChildRepo {
    private val childDao = database.childDao()

    override suspend fun addChild(child: ChildEntity) = childDao.addChild(child)

    override suspend fun getChild(): Flow<List<ChildEntity>> = childDao.getChild()

    override suspend fun deleteChild(child: ChildEntity) = childDao.deleteChild(child)

    override suspend fun updateChild(child: ChildEntity) = childDao.updateChild(child)

}