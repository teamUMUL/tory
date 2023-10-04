package inu.thebite.tory.screens.DataScreen

import android.util.Log
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

    fun setGamaIndex(gameIndex: Int) {
        _gameIndex.value = gameIndex
    }
}