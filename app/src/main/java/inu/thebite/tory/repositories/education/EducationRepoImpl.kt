package inu.thebite.tory.repositories.education

import inu.thebite.tory.database.education.EducationDatabase
import inu.thebite.tory.database.education.EducationEntity
import kotlinx.coroutines.flow.Flow

class EducationRepoImpl(private val database: EducationDatabase):EducationRepo {
    private val educationDao = database.educationDao()

    override suspend fun createEducation(education: EducationEntity) {
        educationDao.createEducation(education = education)
    }

    override fun getAllEducations(): Flow<List<EducationEntity>> {
        return educationDao.getAllEducations()
    }

    override suspend fun updateEducation(education: EducationEntity) {
        educationDao.updateEducation(education = education)
    }

    override suspend fun deleteEducation(education: EducationEntity) {
        educationDao.deleteEducation(education = education)
    }
}