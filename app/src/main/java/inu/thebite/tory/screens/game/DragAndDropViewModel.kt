package inu.thebite.tory.screens.game

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import inu.thebite.tory.R
import kotlin.random.Random

class DragAndDropViewModel :ViewModel() {

    var isCurrentlyDragging by mutableStateOf(false)
        private set

    var items by mutableStateOf(emptyList<GameItem>())
        private set

    var mainItems by mutableStateOf(emptyList<GameItem>())
        private set

    var addedPersons = mutableStateListOf<GameItem>()
        private set


    init {
        items = listOf(
            GameItem("spoon_1", R.drawable.spoon_1),
            GameItem("cup_1",R.drawable.cup_1),
            GameItem("block_1",R.drawable.block_1),
        )
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

        // 정답을 맞춘 경우에만 셔플
        if (isCorrect) {
            items = items.shuffled(Random(System.currentTimeMillis()))
        }

        // 아이템 별로 이름 별 이미지로 재설정(정답을 맞춘 경우에는 O로 이미지가 변하기 때문에 이미지를 되돌리기 위해서 필요)
        items = items.map { gameItem ->
            val imageResourceName = gameItem.name
            val imageResourceId = getResourceIdByName(imageResourceName, context)

            //이름 별 이미지로 재설정
            gameItem.copy(image = imageResourceId)
        }

        isCorrect = false
    }

    fun setMainItem(gameItem: GameItem){
        mainItems = listOf(gameItem)
    }

    fun stopDragging(){
        isCurrentlyDragging = false
    }

    fun isCorrect(){
        isCorrect = true
    }

    fun addGameItem(gameItem: GameItem){
        println("Added GameItem")
        addedPersons.add(gameItem)
    }

    fun updateGameItem(updatedItem: GameItem) {
        val updatedList = items.map { oldItem ->
            if (oldItem.name == updatedItem.name) {
                // Replace the old item with the updated item based on some identifier (e.g., id)
                updatedItem
            } else {
                oldItem
            }
        }
        items = updatedList
    }

}

