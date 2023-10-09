package inu.thebite.tory.repositories.ChildInfo

import inu.thebite.tory.database.ChildInfo.ChildInfoDatabase
import inu.thebite.tory.database.ChildInfo.ChildInfoEntity
import kotlinx.coroutines.flow.Flow

class ChildInfoRepoImpl(private val database : ChildInfoDatabase) : ChildInfoRepo {
    private val childInfoDao = database.childInfoDao()


    override suspend fun createChildInfo(childInfo: ChildInfoEntity) {
        childInfoDao.insertChildInfo(childInfo)
    }

    override suspend fun getAllChildInfos(): Flow<List<ChildInfoEntity>> {
        return childInfoDao.getAllChildInfos()
    }

    override suspend fun updateChildInfo(updatedChildInfo: ChildInfoEntity) {
        childInfoDao.updateChildInfo(updatedChildInfo)
    }

    override suspend fun deleteChildInfo(childInfo: ChildInfoEntity) {
        childInfoDao.deleteChildInfo(childInfo)
    }
}