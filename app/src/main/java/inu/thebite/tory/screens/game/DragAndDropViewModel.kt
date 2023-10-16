package inu.thebite.tory.screens.game

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class DragAndDropViewModel :ViewModel() {

    var isCurrentlyDragging by mutableStateOf(false)
        private set


    var isRandomGame by mutableStateOf(false)
        private set


    //타겟카테고리
    private val _targetCategory = MutableStateFlow<List<String>?>(null)
    val targetCategory = _targetCategory.asStateFlow()

    fun setTargetCategory(targetCategory: List<String>){
        _targetCategory.value = targetCategory
    }
    fun clearTargetCategory() {
        _targetCategory.value = null
    }
    //메인카테고리
    private val _mainCategory = MutableStateFlow<String?>(null)
    val mainCategory = _mainCategory.asStateFlow()

    fun setMainCategory(targetCategory: String){
        _mainCategory.value = targetCategory
    }
    fun clearMainCategory() {
        _mainCategory.value = null
    }

    //첫번째 메인 카테고리 이미지
    private val _firstMainImage = MutableStateFlow<Int?>(null)
    val firstMainImage = _firstMainImage.asStateFlow()

    fun setFirstMainImage(mainImage: Int){
        _firstMainImage.value = mainImage
    }
    fun clearFirstMainImage() {
        _firstMainImage.value = null
    }

    //두번째 메인 카테고리 이미지
    private val _secondMainImage = MutableStateFlow<Int?>(null)
    val secondMainImage = _secondMainImage.asStateFlow()

    fun setSecondMainImage(mainImage: Int){
        _secondMainImage.value = mainImage
    }
    fun clearSecondMainImage() {
        _secondMainImage.value = null
    }

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

    fun restartSameMode(context: Context){
        _targetItems.value = _targetItems.value!!.shuffled(Random(System.currentTimeMillis()))

        // 아이템 별로 이름 별 이미지로 재설정(정답을 맞춘 경우에는 O로 이미지가 변하기 때문에 이미지를 되돌리기 위해서 필요)
        _targetItems.value = _targetItems.value!!.map { gameItem ->
            val imageResourceName = gameItem.name
            val imageResourceId = getResourceIdByName(imageResourceName, context)

            //이름 별 이미지로 재설정
            gameItem.copy(image = imageResourceId)
        }
        isCorrect = false
    }

    fun restartGeneralMode(context: Context){
        _targetCategory.value = _targetCategory.value!!.shuffled(Random(System.currentTimeMillis()))

        isCorrect = false
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


    fun getRandomImageInCategory(context: Context,category: String): Int {
        val random = Random.nextInt(1, 4)
        return getResourceIdByName(
            imageName = category+"_"+random.toString(),
            context = context
        )
    }

    fun setTwoMainDifferentImageInCategory(context: Context, category: String){
        val (firstIndex, secondIndex) = generateRandomNumbers()
        Log.e("인덱스", firstIndex.toString()+"_"+secondIndex.toString())
        setFirstMainImage(getResourceIdByName(imageName = category+"_"+firstIndex.toString(), context = context))
        setSecondMainImage(getResourceIdByName(imageName = category+"_"+secondIndex.toString(), context = context))
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