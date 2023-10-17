//package inu.thebite.tory.screens.education
//
//import android.annotation.SuppressLint
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import co.yml.charts.common.extensions.isNotNull
//import inu.thebite.tory.model.center.CenterResponse
//import inu.thebite.tory.model.domain.DomainResponse
//import inu.thebite.tory.model.lto.AddLtoRequest
//import inu.thebite.tory.model.lto.LtoResponse
//import inu.thebite.tory.repositories.LTO.LTORepoImpl
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import org.koin.core.component.KoinComponent
//import org.koin.core.component.inject
//import org.koin.java.KoinJavaComponent.inject
//import java.lang.Exception
//
//@SuppressLint("MutableCollectionMutableState")
//class LTOViewModel: ViewModel() {
//
//
//    private val repo: LTORepoImpl = LTORepoImpl()
//
//    private val _allLTOs = MutableLiveData<List<LtoResponse>>()
//    val allLTOs: LiveData<List<LtoResponse>> = _allLTOs
//
//    private val _ltos: MutableStateFlow<List<LtoResponse>?> = MutableStateFlow(null)
//    val ltos = _ltos.asStateFlow()
//
//    private val _selectedLTO = MutableStateFlow<LtoResponse?>(null)
//    val selectedLTO = _selectedLTO.asStateFlow()
//
//    // Function to set the selected STO
//    fun setSelectedLTO(ltoEntity: LtoResponse) {
//
//        _selectedLTO.value = ltoEntity
//    }
//
//    fun clearSelectedLTO() {
//        _selectedLTO.value = null
//    }
//    init {
//        getAllLTOs()
//    }
//
//    private fun getAllLTOs(){
//        viewModelScope.launch{
//            try {
//                val allLTOs = repo.getAllLTOs()
//                _allLTOs.value = allLTOs
//            } catch (e: Exception) {
//                Log.e("forEach문", e.message.toString())
//            }
//        }
//    }
//
//
//    fun createLTO(
//        className: String,
//        childName: String,
//        selectedDEV: String,
//        ltoName: String,
//        ltoState: Int,
//        gameMode: String,
//    ) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val newLTOEntity = AddLtoRequest(
//                name = ltoName,
//                contents = "",
//                game = gameMode
//            )
//            repo.createLTO(newLTOEntity)
//        }
//    }
//
//    fun getLTOsByCriteria(
//        selectedDEV: DomainResponse,
//    ){
//        if(selectedDEV.isNotNull()){
//            _ltos.update {
//                val filteredLTOs = allLTOs.value!!.filter {
//                    it.id == selectedDEV.id
//                }
//                filteredLTOs
//            }
//        }else{
//            _ltos.update { null }
//        }
//    }
////    fun updateLTO(updatedLTOEntity: LTOEntity) {
////        viewModelScope.launch(Dispatchers.IO) {
////            repo.updateLTO(updatedLTOEntity)
////        }
////
////    }
////
////    fun deleteLTO(lto: LTOEntity) {
////        viewModelScope.launch(Dispatchers.IO) {
////            repo.deleteLTO(lto)
////        }
////    }
//}