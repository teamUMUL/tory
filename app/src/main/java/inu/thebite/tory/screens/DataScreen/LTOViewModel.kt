package inu.thebite.tory.screens.DataScreen

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

@SuppressLint("MutableCollectionMutableState")
class LTOViewModel: ViewModel() {
    private val _ltoList1 = MutableLiveData<Map<String, Int>>()

    private val _ltoList2 = MutableLiveData<Map<String, Int>>()

    private val _ltoList3 = MutableLiveData<Map<String, Int>>()

    private val _ltoList4 = MutableLiveData<Map<String, Int>>()

    private val _ltoList5 = MutableLiveData<Map<String, Int>>()

    private val _ltoList6 = MutableLiveData<Map<String, Int>>()

    private val _ltoList7 = MutableLiveData<Map<String, Int>>()

    private val _ltoList8 = MutableLiveData<Map<String, Int>>()

    private val _ltoList9 = MutableLiveData<Map<String, Int>>()

    private val _ltoList10 = MutableLiveData<Map<String, Int>>()

    // Create or Update
    fun addOrUpdateLTO(devIndex: Int, key: String, value: Int) {
        val currentMap = getMapForDevIndex(devIndex).toMutableMap()
        currentMap[key] = value
        updateLiveData(devIndex, currentMap)
    }

    fun updateLTO(devIndex: Int,ltoIndex:Int, oldKey:String, newKey:String){
        val currentLiveData = getLiveDataForDevIndex(devIndex)
        val currentMap = getMapForDevIndex(devIndex).toMutableMap()
        val oldValue : Int? = currentMap[oldKey]

        val beforeLTONameList: List<String> = currentMap.keys.toList()
        var foundOldKey = false
        val behindOldKeyList = mutableListOf<String>()
        val aheadOldKeyList = mutableListOf<String>()

        for(element in beforeLTONameList){
            if(foundOldKey){
                behindOldKeyList.add(element)
            }else{
                if(element != oldKey){
                    aheadOldKeyList.add(element)
                }
            }
            if(element == oldKey){
                foundOldKey = true
            }
        }
        val resultKeyList = mutableListOf<String>()
        resultKeyList.addAll(aheadOldKeyList)
        resultKeyList.add(newKey)
        resultKeyList.addAll(behindOldKeyList)

        val beforeLTOStateList: List<Int> = currentMap.values.toList()
        var foundOldValue = false
        val behindOldValueList = mutableListOf<Int>()
        val aheadOldValueList = mutableListOf<Int>()

        for(element in beforeLTOStateList){
            if(foundOldValue){
                behindOldValueList.add(element)
            }else{
                if(element != oldValue){
                    aheadOldValueList.add(element)
                }
            }
            if(element == oldValue){
                foundOldValue = true
            }
        }
        val resultValueList = mutableListOf<Int>()
        resultValueList.addAll(aheadOldValueList)
        resultValueList.add(oldValue!!)
        resultValueList.addAll(behindOldValueList)

        val resultMap = resultKeyList.zip(resultValueList).toMap()

        currentLiveData.value = resultMap
    }

    // Read
    fun getLTO(devIndex: Int): Pair<List<String>, List<Int>> {
        val map = getMapForDevIndex(devIndex)
        val keys = map.keys.toList()
        val values = map.values.toList()
        return Pair(keys, values)
    }

    // Read
    fun getLTOWithOneData(devIndex: Int): List<String> {
        val map = getMapForDevIndex(devIndex)
        val keyValueStrings = map.entries.map { entry ->
            "${entry.key}: ${entry.value}"
        }
        return keyValueStrings
    }

    // Delete
    fun removeLTO(devIndex: Int, key: String) {
        val currentMap = getMapForDevIndex(devIndex).toMutableMap()
        currentMap.remove(key)
        updateLiveData(devIndex, currentMap)
    }

    private fun getLiveDataForDevIndex(devIndex: Int): MutableLiveData<Map<String, Int>> {
        return when (devIndex) {
            0 -> _ltoList1
            1 -> _ltoList2
            2 -> _ltoList3
            3 -> _ltoList4
            4 -> _ltoList5
            5 -> _ltoList6
            6 -> _ltoList7
            7 -> _ltoList8
            8 -> _ltoList9
            9 -> _ltoList10
            else -> _ltoList1
        }
    }

    private fun getMapForDevIndex(devIndex: Int): Map<String, Int> {
        return getLiveDataForDevIndex(devIndex).value ?: emptyMap()
    }

    private fun updateLiveData(devIndex: Int, updatedMap: Map<String, Int>) {
        getLiveDataForDevIndex(devIndex).value = updatedMap
    }
}