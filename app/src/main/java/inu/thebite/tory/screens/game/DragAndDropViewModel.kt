package inu.thebite.tory.screens.game

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import inu.thebite.tory.R
import inu.thebite.tory.database.ChildClass.ChildClassEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class DragAndDropViewModel :ViewModel() {

    var isCurrentlyDragging by mutableStateOf(false)
        private set



    //타겟아이템
    private val _targetItems = MutableStateFlow<List<GameItem>?>(null)
    val targetItems = _targetItems.asStateFlow()

    fun setTargetItems(targetItems: List<GameItem>) {
        _targetItems.value = targetItems
    }

    fun clearTargetItems() {
        _targetItems.value = null
    }

    //메인아이템
    private val _mainItem = MutableStateFlow<GameItem?>(null)
    val mainItem = _mainItem.asStateFlow()

    fun setMainItem(mainItem: GameItem) {

        _mainItem.value = mainItem
    }

    fun clearMainItem() {
        _mainItem.value = null
    }




    init {

    }

    //정답 유무 판단
    var isCorrect by mutableStateOf(false)
        private set

    private fun getResourceIdByName(imageName: String, context: Context): Int {
        // 이 함수는 이미지 리소스 이름을 리소스 ID로 변환합니다.
        val packageName = context.packageName
        return context.resources.getIdentifier(imageName, "drawable", packageName)
    }



    fun startDragging(context: Context) {
        isCurrentlyDragging = true
        restart(context)

    }

    fun restart(context: Context){
        if (isCorrect) {
            _targetItems.value = _targetItems.value!!.shuffled(Random(System.currentTimeMillis()))
        }

        // 아이템 별로 이름 별 이미지로 재설정(정답을 맞춘 경우에는 O로 이미지가 변하기 때문에 이미지를 되돌리기 위해서 필요)
        _targetItems.value = _targetItems.value!!.map { gameItem ->
            val imageResourceName = gameItem.name
            val imageResourceId = getResourceIdByName(imageResourceName, context)

            //이름 별 이미지로 재설정
            gameItem.copy(image = imageResourceId)
        }
        isCorrect = false
    }

    fun stopDragging(){
        isCurrentlyDragging = false
    }

    fun isCorrect(){
        isCorrect = true
    }



    fun updateGameItem(updatedItem: GameItem) {
        Log.e("정답 유무", updatedItem.name)
        val updatedList = _targetItems.value!!.map { oldItem ->
            if (oldItem.name == updatedItem.name) {
                // Replace the old item with the updated item based on some identifier (e.g., id)
                updatedItem
            } else {
                oldItem
            }
        }
        _targetItems.value = updatedList
    }

}

