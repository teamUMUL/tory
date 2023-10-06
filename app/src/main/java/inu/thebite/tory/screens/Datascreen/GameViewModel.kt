package inu.thebite.tory.screens.Datascreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private val _oneGameResult = MutableLiveData<String>("+")

    val oneGameResult: LiveData<String>
        get() = _oneGameResult

    fun setOneGameResult(oneGameResult: String) {
        _oneGameResult.value = oneGameResult
    }

    private val _gameIndex = MutableLiveData<Int>(0)

    val gameIndex: LiveData<String>
        get() = _oneGameResult

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