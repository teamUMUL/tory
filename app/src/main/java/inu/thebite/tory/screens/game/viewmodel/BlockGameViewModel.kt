package inu.thebite.tory.screens.game.viewmodel

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


data class BlockEntity(
    val index : Int,
    val color : Color,
    val isSuccess : Boolean
)
class BlockGameViewModel : ViewModel() {

    private val _dragItems = MutableStateFlow<List<BlockEntity>?>(null)
    val dragItems = _dragItems.asStateFlow()

    private val _dropItems = MutableStateFlow<List<BlockEntity>?>(null)
    val dropItems = _dropItems.asStateFlow()

    fun setDragItems(dragItems : List<BlockEntity>){
        _dragItems.value = dragItems
    }

    fun clearDragItems(){
        _dragItems.value = null
    }


    fun setDropItems(dropItems : List<BlockEntity>){
        _dropItems.value = dropItems
    }

    fun clearDropItems(){
        _dropItems.value = null
    }

    fun makeBlockCase(blockSize: Int){
        val blockCase = mutableListOf<BlockEntity>()
        for (i in 0..15){
            blockCase.add(
                BlockEntity(
                    index = i,
                    color = Color.LightGray,
                    isSuccess = false
                )
            )
        }
        _dropItems.value = blockCase
    }

    fun changeBlock(block: BlockEntity){
        val foundBlock = _dropItems.value!!.find {
            it.index == block.index
        }
        foundBlock?.let {foundBlock ->
            val updatedDropItems = _dropItems.value!!.toMutableList()
            updatedDropItems[foundBlock.index] = block

            _dropItems.value = updatedDropItems
        }
    }
}