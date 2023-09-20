package inu.thebite.tory.screens.DataScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class STODetailViewModel: ViewModel() {
    private val ltoLiveDataLists =Array(10){mutableListOf<MutableLiveData<Map<List<String>, List<String>>>>()}
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

    fun addOrUpdateSTODetail(ltoIndex: Int,devIndex: Int, stoDetailInfo:List<String>, stoGameData: List<String>) {
        if (ltoIndex >= 0 && ltoIndex < ltoLiveDataLists[devIndex].size) {
            val currentMap = ltoLiveDataLists[devIndex][ltoIndex].value?.toMutableMap() ?: mutableMapOf()
            currentMap[stoDetailInfo] = stoGameData
            ltoLiveDataLists[devIndex][ltoIndex].value = currentMap
        }
    }

    fun updateSTODetail(ltoIndex: Int,devIndex: Int, beforeSTODetailInfo:List<String>,afterSTODetailInfo:List<String>) {
        if (ltoIndex >= 0 && ltoIndex < ltoLiveDataLists[devIndex].size) {
            val currentMap = ltoLiveDataLists[devIndex][ltoIndex].value?.toMutableMap()
            if (currentMap != null && currentMap.containsKey(beforeSTODetailInfo)) {
                val oldValue : List<String>? = currentMap[beforeSTODetailInfo]


                val beforeSTODetailListKeys: List<List<String>> = currentMap.keys.toList()
                var foundOldKey = false
                val behindOldKeyList = mutableListOf<List<String>>()
                val aheadOldKeyList = mutableListOf<List<String>>()

                for(element in beforeSTODetailListKeys){
                    if(foundOldKey){
                        behindOldKeyList.add(element)
                    }
                    else{
                        if(element != beforeSTODetailInfo){
                            aheadOldKeyList.add(element)
                        }
                    }
                    if(element == beforeSTODetailInfo){
                        foundOldKey = true
                    }
                }
                val resultKeyList = mutableListOf<List<String>>()
                resultKeyList.addAll(aheadOldKeyList)
                resultKeyList.add(afterSTODetailInfo)
                resultKeyList.addAll(behindOldKeyList)

                val beforeSTODetailListValues: List<List<String>> = currentMap.values.toList()
                var foundOldValue = false
                val behindOldValueList = mutableListOf<List<String>>()
                val aheadOldValueList = mutableListOf<List<String>>()

                for(element in beforeSTODetailListValues){
                    if(foundOldValue){
                        behindOldValueList.add(element)
                    }
                    else{
                        if(element != oldValue){
                            aheadOldValueList.add(element)
                        }
                    }
                    if(element == oldValue){
                        foundOldValue = true
                    }
                }
                val resultValueList = mutableListOf<List<String>>()
                resultValueList.addAll(aheadOldValueList)
                resultValueList.add(oldValue!!)
                resultValueList.addAll(behindOldValueList)

                val resultMap = resultKeyList.zip(resultValueList).toMap()

                ltoLiveDataLists[devIndex][ltoIndex].value = resultMap
            }
        }
    }

    fun updateSTODetailValue(ltoIndex: Int, devIndex: Int, stoDetailInfo: List<String>, newValue: List<String>){
        if (ltoIndex >= 0 && ltoIndex < ltoLiveDataLists[devIndex].size) {
            val currentMap = ltoLiveDataLists[devIndex][ltoIndex].value?.toMutableMap() ?: mutableMapOf()
            currentMap[stoDetailInfo] = newValue
            ltoLiveDataLists[devIndex][ltoIndex].value = currentMap
        }
    }

    fun getSTODetail(ltoIndex: Int,devIndex: Int, stoIndex: Int): Pair<List<String>, List<String>> {
        val map = ltoLiveDataLists[devIndex].getOrNull(ltoIndex)?.value ?: emptyMap()
        val keys = map.keys.toList()
        val values = map.values.toList()
        return Pair(keys[stoIndex], values[stoIndex])
    }

    fun getSTOWithOneData(ltoIndex: Int, devIndex: Int): List<String> {
        val map = ltoLiveDataLists[devIndex].getOrNull(ltoIndex)?.value ?: emptyMap()
        return map.entries.map { (key, value) -> "${key.first()}: $value" }
    }


    fun removeSTODetail(ltoIndex: Int,devIndex: Int, key: List<String>) {
        if (ltoIndex >= 0 && ltoIndex < ltoLiveDataLists[devIndex].size) {
            val currentMap = ltoLiveDataLists[devIndex][ltoIndex].value?.toMutableMap() ?: mutableMapOf()
            Log.e("삭제되는 키", key.toString())
            currentMap.remove(key)
            ltoLiveDataLists[devIndex][ltoIndex].value = currentMap
        }
    }
}