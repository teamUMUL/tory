package inu.thebite.tory.screens.datasceen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import inu.thebite.tory.database.ChildClass.ChildClassEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel: ViewModel() {

    private val _oneGameResult = MutableStateFlow<String?>(null)
    val oneGameResult = _oneGameResult.asStateFlow()

    fun setOneGameResult(oneGameResult: String) {

        _oneGameResult.value = oneGameResult
    }

    fun clearSelectedChildClass() {
        _oneGameResult.value = null
    }

    private val _gameIndex = MutableLiveData<Int>(0)

    val gameIndex: LiveData<Int>
        get() = _gameIndex

    fun setGameIndex(gameIndex: Int) {
        _gameIndex.value = gameIndex
    }


    private val _gameResultList = MutableLiveData<List<String>>(null)

    val gameResultList: LiveData<List<String>>
        get() = _gameResultList

    fun setGameResultList(gameResultList: List<String>) {
        _gameResultList.value = gameResultList
    }
}