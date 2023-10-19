package inu.thebite.tory.repositories.education

import inu.thebite.tory.database.education.EducationEntity
import kotlinx.coroutines.flow.Flow

interface EducationRepo {

    suspend fun createEducation(education: EducationEntity)
    fun getAllEducations(): Flow<List<EducationEntity>>
    suspend fun updateEducation(education: EducationEntity)
    suspend fun deleteEducation(education: EducationEntity)
}