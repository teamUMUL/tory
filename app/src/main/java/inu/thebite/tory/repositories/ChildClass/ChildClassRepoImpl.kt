package inu.thebite.tory.repositories.ChildClass

import inu.thebite.tory.database.ChildClass.ChildClassDatabase
import inu.thebite.tory.database.ChildClass.ChildClassEntity
import kotlinx.coroutines.flow.Flow

class ChildClassRepoImpl(private val database: ChildClassDatabase): ChildClassRepo {
    private val childClassDao = database.childClassDao()


    override suspend fun createChildClass(childClass: ChildClassEntity) {
        childClassDao.insertChildClass(childClass)
    }

    override suspend fun getAllChildClasses(): Flow<List<ChildClassEntity>> {
        return childClassDao.getAllChildClasses()
    }


    override suspend fun updateChildClass(updatedChildClass: ChildClassEntity) {
        childClassDao.updateChildClass(updatedChildClass)

    }

    override suspend fun deleteChildClass(childClass: ChildClassEntity) {
        childClassDao.deleteChildClass(childClass)

    }


}