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