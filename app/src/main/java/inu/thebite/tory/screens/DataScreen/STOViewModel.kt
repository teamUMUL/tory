package inu.thebite.tory.screens.DataScreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

@SuppressLint("MutableCollectionMutableState")
class STOViewModel: ViewModel() {

    private val ltoLiveDataLists =Array(10){mutableListOf<MutableLiveData<Map<String, Int>>>()}
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

    fun addOrUpdateSTO(ltoIndex: Int,devIndex: Int, key: String, value: Int) {
        if (ltoIndex >= 0 && ltoIndex < ltoLiveDataLists[devIndex].size) {
            val currentMap = ltoLiveDataLists[devIndex][ltoIndex].value?.toMutableMap() ?: mutableMapOf()
            currentMap[key] = value
            ltoLiveDataLists[devIndex][ltoIndex].value = currentMap
        }
    }
    fun updateSTO(ltoIndex: Int, devIndex: Int, oldKey: String, newKey: String) {
        if (ltoIndex >= 0 && ltoIndex < ltoLiveDataLists[devIndex].size) {
            val currentMap = ltoLiveDataLists[devIndex][ltoIndex].value?.toMutableMap()
            if (currentMap != null && currentMap.containsKey(oldKey)) {
                val oldValue : Int? = currentMap[oldKey]

                val beforeSTONameList: List<String> = currentMap.keys.toList()
                var foundOldKey = false
                val behindOldKeyList = mutableListOf<String>()
                val aheadOldKeyList = mutableListOf<String>()

                for(element in beforeSTONameList){
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

                val beforeSTOStateList: List<Int> = currentMap.values.toList()
                var foundOldValue = false
                val behindOldValueList = mutableListOf<Int>()
                val aheadOldValueList = mutableListOf<Int>()

                for(element in beforeSTOStateList){
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

                ltoLiveDataLists[devIndex][ltoIndex].value = resultMap
            }
        }
    }


    fun getSTO(ltoIndex: Int,devIndex: Int): Pair<List<String>, List<Int>> {
        val map = ltoLiveDataLists[devIndex].getOrNull(ltoIndex)?.value ?: emptyMap()
        val keys = map.keys.toList()
        val values = map.values.toList()
        return Pair(keys, values)
    }

    fun getSTOWithOneData(ltoIndex: Int, devIndex: Int): List<String> {
        val map = ltoLiveDataLists[devIndex].getOrNull(ltoIndex)?.value ?: emptyMap()
        return map.entries.map { (key, value) -> "$key: $value" }
    }

    fun removeSTO(ltoIndex: Int,devIndex: Int, key: String) {
        if (ltoIndex >= 0 && ltoIndex < ltoLiveDataLists[devIndex].size) {
            val currentMap = ltoLiveDataLists[devIndex][ltoIndex].value?.toMutableMap() ?: mutableMapOf()
            currentMap.remove(key)
            ltoLiveDataLists[devIndex][ltoIndex].value = currentMap
        }
    }
}