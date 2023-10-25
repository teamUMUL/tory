package inu.thebite.tory.screens.education2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        Log.d("selectedEducation",allEducations.value.toString())
        val foundEducation = allEducations.value!!.find {
            it.stoName == selectedSTO.name
        }
        _selectedEducation.value = foundEducation
    }


    fun clearSelectedEducation() {
        _selectedEducation.value = null
    }

    private val _educationList = MutableLiveData<List<String>>(null)

    val educationList: LiveData<List<String>>
        get() = _educationList

    fun setEducationList(gameResultList: List<String>) {
        _educationList.value = gameResultList
    }

    init {
        getAllEducations()
//        setDummyEducationData()
    }

//    fun setDummyEducationData(){
//        val dummyEducationDataList = mutableListOf<EducationEntity>()
//        for (i in 1..10){
//            val dummyEducationData = EducationEntity(
//                stoId = i.toString(),
//                roundNum = 1,
//                educationResult = listOf(
//                    "n","n","n","n","n","n","n","n","n","n","n","n","n","n","n"
//                )
//            )
//            dummyEducationDataList.add(dummyEducationData)
//        }
//        _allEducations.value = dummyEducationDataList
//        Log.d("allEducations", allEducations.value.toString())
//    }

    private fun getAllEducations(){
        viewModelScope.launch(Dispatchers.IO){
            repo.getAllEducations().collect{data ->
                _allEducations.update { data }
            }
        }
    }

    fun createEducation(
        selectedSTOName : String,
        educationList: List<String>,
        roundNum : Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newEducation = EducationEntity(
                stoName = selectedSTOName,
                educationResult = educationList,
                roundNum = roundNum
            )
            repo.createEducation(newEducation)
        }
    }

    fun updateSelectedEducationList(selectedEducation: EducationEntity, newEducationList: List<String>){
        viewModelScope.launch(Dispatchers.IO) {
            _selectedEducation.value = selectedEducation.copy(
                educationResult = newEducationList
            )
        }
    }

    fun addEducationRound(selectedEducation: EducationEntity){
        viewModelScope.launch(Dispatchers.IO) {
            val beforeRound = selectedEducation.roundNum
            val beforeEducationList = selectedEducation.educationResult

            repo.updateEducation(
                selectedEducation.copy(
                    roundNum = beforeRound+1,
                    educationResult = List(beforeEducationList.size){"n"}
                )
            )
            val foundEducation = allEducations.value!!.find {
                it.stoName == selectedEducation.stoName
            }
            foundEducation?.roundNum = beforeRound+1
            foundEducation?.educationResult = List(beforeEducationList.size){"n"}

            _selectedEducation.value = foundEducation
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