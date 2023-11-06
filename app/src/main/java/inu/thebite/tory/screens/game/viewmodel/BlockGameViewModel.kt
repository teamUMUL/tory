package inu.thebite.tory.screens.game.viewmodel

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
}