package inu.thebite.tory.repositories

import inu.thebite.tory.database.ChildEntity
import kotlinx.coroutines.flow.Flow

interface ChildRepo {
    suspend fun addChild(child: ChildEntity)
    suspend fun getChild(): Flow<List<ChildEntity>>
    suspend fun deleteChild(child: ChildEntity)
    suspend fun updateChild(child: ChildEntity)
}