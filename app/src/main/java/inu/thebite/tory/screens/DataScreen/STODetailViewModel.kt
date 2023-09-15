package inu.thebite.tory.screens.DataScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class STODetailViewModel: ViewModel() {
    private val ltoLiveDataLists =Array(10){mutableListOf<MutableLiveData<Map<List<String>, Int>>>()}
    private val ltoNum = MutableLiveData(0)

    fun addLTO(devIndex:Int) {
        ltoNum.value = (ltoNum.value ?: 0) + 1
        // 새로운 LTO 추가 시 LiveData 생성
        ltoLiveDataLists[devIndex].add(MutableLiveData(emptyMap()))
    }


    fun removeLTOAtIndex(index: Int, devIndex: Int) {
        if (index >= 0 && index < ltoLiveDataLists[devIndex].size) {
            ltoNum.value = (ltoNum.value ?: 0) - 1
            ltoLiveDataLists[devIndex].removeAt(index)
        }
    }

    fun addOrUpdateSTODetail(ltoIndex: Int,devIndex: Int, stoDetailInfo:List<String>, value: Int) {
        if (ltoIndex >= 0 && ltoIndex < ltoLiveDataLists[devIndex].size) {
            val currentMap = ltoLiveDataLists[devIndex][ltoIndex].value?.toMutableMap() ?: mutableMapOf()
            currentMap[stoDetailInfo] = value
            ltoLiveDataLists[devIndex][ltoIndex].value = currentMap
        }
    }

    fun getSTODetail(ltoIndex: Int,devIndex: Int, stoIndex: Int): Pair<List<String>, List<Int>> {
        val map = ltoLiveDataLists[devIndex].getOrNull(ltoIndex)?.value ?: emptyMap()
        val keys = map.keys.toList()
        val values = map.values.toList()
        return Pair(keys[stoIndex], values)
    }

    fun getSTOWithOneData(ltoIndex: Int, devIndex: Int): List<String> {
        val map = ltoLiveDataLists[devIndex].getOrNull(ltoIndex)?.value ?: emptyMap()
        return map.entries.map { (key, value) -> "${key.first()}: $value" }
    }

    fun removeSTODetail(ltoIndex: Int,devIndex: Int, key: List<String>) {
        if (ltoIndex >= 0 && ltoIndex < ltoLiveDataLists[devIndex].size) {
            val currentMap = ltoLiveDataLists[devIndex][ltoIndex].value?.toMutableMap() ?: mutableMapOf()
            currentMap.remove(key)
            ltoLiveDataLists[devIndex][ltoIndex].value = currentMap
        }
    }
}