package inu.thebite.tory.screens.education2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.database.education.EducationEntity
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.repositories.education.EducationRepo
import inu.thebite.tory.repositories.education.EducationRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class EducationViewModel : ViewModel(), KoinComponent {

    private val repo: EducationRepo by inject()

    private val _allEducations: MutableStateFlow<List<EducationEntity>?> = MutableStateFlow(null)
    val allEducations = _allEducations.asStateFlow()

    private val _selectedEducation = MutableStateFlow<EducationEntity?>(null)
    val selectedEducation = _selectedEducation.asStateFlow()

    fun setSelectedEducation(selectedSTO: StoResponse) {
        _selectedEducation.value = allEducations.value!!.find {
            it.stoId == selectedSTO.id
        }
    }

    fun clearSelectedEducation() {
        _selectedEducation.value = null
    }

    init {
        getAllEducations()
    }

    private fun getAllEducations(){
        viewModelScope.launch(Dispatchers.IO){
            repo.getAllEducations().collect{data ->
                _allEducations.update { data }
            }
        }
    }

    fun createEducation(
        selectedSTO : StoResponse,
        educationList: List<String>,
        roundNum : Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newEducation = EducationEntity(
                stoId = selectedSTO.id,
                educationResult = educationList,
                roundNum = roundNum
            )
            repo.createEducation(newEducation)
        }
    }

    fun updateEducation(updatedEducation: EducationEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateEducation(updatedEducation)
        }
    }

    fun deleteEducation(selectedEducation: EducationEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteEducation(selectedEducation)
        }
    }
}