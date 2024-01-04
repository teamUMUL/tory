package inu.thebite.tory.screens.game.viewmodel//package inu.thebite.tory.screens.game

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import inu.thebite.tory.R
import inu.thebite.tory.model.image.ImageResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class DragAndDropViewModel :ViewModel() {

    var isCurrentlyDragging by mutableStateOf(false)
        private set


    var isRandomGame by mutableStateOf(false)
        private set


//    //타겟카테고리
//    private val _targetCategory = MutableStateFlow<List<String>?>(null)
//    val targetCategory = _targetCategory.asStateFlow()
//
//    fun setTargetCategory(targetCategory: List<String>){
//        _targetCategory.value = targetCategory
//    }
//    fun clearTargetCategory() {
//        _targetCategory.value = null
//    }
    //메인카테고리
//    private val _mainCategory = MutableStateFlow<String?>(null)
//    val mainCategory = _mainCategory.asStateFlow()
//
//    fun setMainCategory(targetCategory: String){
//        _mainCategory.value = targetCategory
//    }
//    fun clearMainCategory() {
//        _mainCategory.value = null
//    }

    //첫번째 메인 카테고리 이미지
    private val _firstMainItem = MutableStateFlow<ImageResponse?>(null)
    val firstMainItem = _firstMainItem.asStateFlow()

    fun setFirstMainItem(mainItem: ImageResponse){
        _firstMainItem.value = mainItem
    }
    fun clearFirstMainItem() {
        _firstMainItem.value = null
    }

    //두번째 메인 카테고리 이미지
    private val _secondMainItem = MutableStateFlow<ImageResponse?>(null)
    val secondMainItem = _secondMainItem.asStateFlow()

    fun setSecondMainItem(mainItem: ImageResponse){
        _secondMainItem.value = mainItem
    }
    fun clearSecondMainItem() {
        _secondMainItem.value = null
    }

    //타겟아이템
    private val _targetItems = MutableStateFlow<List<ImageResponse>?>(null)
    val targetItems = _targetItems.asStateFlow()
    fun setTargetItems(targetItems: List<ImageResponse>) {
        _targetItems.value = targetItems
    }

    fun clearTargetItems() {
        _targetItems.value = null
    }

    //메인아이템
    private val _mainItem = MutableStateFlow<ImageResponse?>(null)
    val mainItem = _mainItem.asStateFlow()

    fun setMainItem(mainItem: ImageResponse) {

        _mainItem.value = mainItem
    }

    fun clearMainItem() {
        _mainItem.value = null
    }

    fun clearAll(){
        _mainItem.update { null }
        _targetItems.update { null }
        _firstMainItem.update { null }
        _secondMainItem.update { null }
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

    fun isRandomGame(){
        isRandomGame = true
    }

    fun isNotRandomGame(){
        isRandomGame = false
    }

    fun startDragging(context: Context) {
        isCurrentlyDragging = true
//        restart(context)

    }

    fun restartSameMode(allImageList : List<ImageResponse>){
        _targetItems.value = _targetItems.value!!.shuffled(Random(System.currentTimeMillis()))

        _targetItems.value = targetItems.value!!.map { gameItem ->
            gameItem.copy(
                url = allImageList.find { it.name == gameItem.name }?.url ?: "https://storage.googleapis.com/tory-image-repository/Etc/Correct.png"
            )
        }
        isCorrect = false
    }

    fun restartGeneralMode(imagesByCategory : List<ImageResponse>){
        _targetItems.value = _targetItems.value!!.shuffled(Random(System.currentTimeMillis()))

        resetMainItemsGeneralMode(imagesByCategory)
        isCorrect = false
    }

    fun resetMainItemsGeneralMode(imagesByCategory: List<ImageResponse>){
        val shuffledImagesByCategory = imagesByCategory.shuffled(Random(System.currentTimeMillis()))
        setFirstMainItem(shuffledImagesByCategory[0])
        setSecondMainItem(shuffledImagesByCategory[1])
        Log.d("reset", "resetMainItemGeneralMode")
    }

    fun stopDragging(){
        isCurrentlyDragging = false
    }

    fun isCorrect(){
        isCorrect = true
    }

    fun isWrong(){
        isCorrect = false
    }

    fun updateGameItem(updatedItem: ImageResponse) {
        Log.e("정답 유무", updatedItem.name)
        val updatedList = _targetItems.value!!.map { oldItem ->
            if (oldItem.name == updatedItem.name) {
                updatedItem
            } else {
                oldItem
            }
        }
        _targetItems.value = updatedList
    }

    fun updateGameItemGeneralMode(updatedItem: ImageResponse) {
        _secondMainItem.value = updatedItem
    }


    fun getRandomImageInCategory(context: Context,category: String): Int {
        val random = Random.nextInt(1, 4)
        return getResourceIdByName(
            imageName = category+"_"+random.toString(),
            context = context
        )
    }

    fun setTwoMainDifferentImageInCategory(context: Context, category: String){

    }
}

fun generateRandomNumbers(): Pair<Int, Int> {
    val random = Random
    val numbers = mutableListOf(1, 2, 3)

    val firstIndex = random.nextInt(numbers.size)
    val firstNumber = numbers.removeAt(firstIndex)

    val secondIndex = random.nextInt(numbers.size)
    val secondNumber = numbers[secondIndex]

    return Pair(firstNumber, secondNumber)
}

