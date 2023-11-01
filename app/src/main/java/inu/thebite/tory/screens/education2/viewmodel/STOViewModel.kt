package inu.thebite.tory.screens.education2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.model.image.UpdateImageListRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.point.AddPointRequest
import inu.thebite.tory.model.point.UpdatePointRequest
import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoRequest
import inu.thebite.tory.model.sto.UpdateStoRoundRequest
import inu.thebite.tory.model.sto.UpdateStoStatusRequest
import inu.thebite.tory.repositories.STO.STORepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class STOViewModel : ViewModel() {
    private val repo: STORepoImpl = STORepoImpl()


    private val _allSTOs: MutableStateFlow<List<StoResponse>?> = MutableStateFlow(null)
    val allSTOs = _allSTOs.asStateFlow()

    private val _stos: MutableStateFlow<List<StoResponse>?> = MutableStateFlow(null)
    val stos = _stos.asStateFlow()

    private val _selectedSTO = MutableStateFlow<StoResponse?>(null)
    val selectedSTO = _selectedSTO.asStateFlow()

    private val _points = MutableStateFlow<List<String>?>(null)
    val points = _points.asStateFlow()

    fun setSelectedSTO(stoEntity: StoResponse) {

        _selectedSTO.value = stoEntity
    }

    fun updateSelectedSTO(stoEntity: StoResponse){
        _selectedSTO.value = stos.value!!.find {
            it.id == stoEntity.id
        }
    }


    fun clearSelectedSTO() {
        _selectedSTO.value = null
    }
    init {
        getAllSTOs()
    }

    fun getAllSTOs(){
        viewModelScope.launch {
            try {
                val allSTOs = repo.getStoList()
                _allSTOs.value = allSTOs
            } catch (e: Exception) {
                Log.e("failed to get all STOs", e.message.toString())
            }
        }
    }
    fun setSelectedSTOStatus(selectedSTO: StoResponse, changeState : String) {
        viewModelScope.launch {
            val updateSTOStatus = UpdateStoStatusRequest(
                status = changeState
            )
            if(changeState == "준거 도달"){
                repo.updateStoHitStatus(selectedSTO, updateSTOStatus)
            } else {
                repo.updateStoStatus(selectedSTO, updateSTOStatus)
            }
            getAllSTOs()
            getSTOsByLTO(selectedSTO.lto)
        }
    }
    fun getSTOsByLTO(
        selectedLTO: LtoResponse,
    ){
        if(selectedLTO.isNotNull()){
            _stos.update {

                val filteredSTOs = allSTOs.value!!.filter {
                    it.lto.id == selectedLTO.id
                }
                filteredSTOs
            }
        }else{
            _stos.update { null }
        }
    }

    fun createSTO(
        selectedLTO: LtoResponse,
        newSTO : AddStoRequest
    ){
        viewModelScope.launch {
            try {
                repo.addSto(
                    ltoInfo = selectedLTO,
                    addStoRequest = newSTO
                )
            } catch (e: Exception) {
                Log.e("failed to create STO", e.message.toString())
            }
            getAllSTOs()
        }
    }

    fun getPointList(
        selectedSTO: StoResponse
    ){
        viewModelScope.launch {
            try {
                val points = repo.getPointList(selectedSTO)
                _points.value = points
            }catch (e: Exception){
                Log.e("failed to get Point List", e.message.toString())
            }
        }
    }

    fun clearPointList(){
        _points.value = null
    }

    fun addPoint(
        selectedSTO: StoResponse,
        addPointRequest: AddPointRequest
    ){
        viewModelScope.launch {
            try {
                repo.addPoint(selectedSTO, addPointRequest)
            }catch (e: Exception){
                Log.e("failed to add Point", e.message.toString())
            }
            getPointList(selectedSTO)
        }
    }

    fun deletePoint(
        selectedSTO: StoResponse,
    ){
        viewModelScope.launch {
            try {
                repo.deletePoint(selectedSTO)
            }catch (e: Exception){
                Log.e("failed to delete Point", e.message.toString())
            }
            getPointList(selectedSTO)
        }
    }

    fun addRound(
        selectedSTO: StoResponse,
        registrant : String
    ){
        viewModelScope.launch {
            try {
                repo.addRound(selectedSTO, updateStoRoundRequest = UpdateStoRoundRequest(
                    registrant = registrant
                ))
            }catch (e: Exception){
                Log.e("failed to add Round", e.message.toString())
            }
            getPointList(selectedSTO)
        }
    }



    fun updateSTO(
        selectedSTO: StoResponse,
        updateSTO : UpdateStoRequest
    ){
        viewModelScope.launch {
            try {
                repo.updateSto(
                    stoInfo = selectedSTO,
                    updateStoRequest = updateSTO
                )
            } catch (e: Exception) {
                Log.e("failed to update STO", e.message.toString())
            }
            getAllSTOs()
            getSTOsByLTO(selectedSTO.lto)
        }
    }

    fun updateSTOImageList(
        selectedSTO: StoResponse,
        updateImageList : List<String>
    ){
        viewModelScope.launch {
            try {
                repo.updateImageList(
                    stoInfo = selectedSTO,
                    updateImageListRequest = UpdateImageListRequest(
                        imageList = updateImageList
                    )
                )
            } catch (e: Exception){
                Log.e("failed to update STO ImageList", e.message.toString())
            }
            getAllSTOs()
            getSTOsByLTO(selectedSTO.lto)
        }
    }

    fun updateSelectedSTO(
        selectedSTOId: Long
    ){
        viewModelScope.launch {
            val foundSTO = allSTOs.value!!.find {
                it.id == selectedSTOId
            }
            foundSTO?.let {foundSTO ->
                setSelectedSTO(
                    foundSTO
                )
            }
        }
    }
    fun deleteSTO(
        selectedSTO : StoResponse
    ){
        viewModelScope.launch {
            try {
                repo.deleteSto(
                    stoInfo = selectedSTO,
                )
            } catch (e: Exception) {
                Log.e("failed to delete STO", e.message.toString())
            }
            getAllSTOs()
        }
    }
}
