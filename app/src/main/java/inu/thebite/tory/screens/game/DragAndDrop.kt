package inu.thebite.tory.screens.game

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import inu.thebite.tory.database.LTO.LTOEntity

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DragableScreen(
    modifier: Modifier = Modifier,
    selectedLTO : LTOEntity,
    dragAndDropViewModel: DragAndDropViewModel,
    timerRestart : MutableState<Boolean>,
    content: @Composable BoxScope.() -> Unit,
) {
    val state = remember { DragTargetInfo() }
    CompositionLocalProvider(
        LocalDragTargetInfo provides state
    ) {
        Box(modifier = modifier.fillMaxSize())
        {
            content()

            if (state.isDragging) {
                var targetSize by remember {
                    mutableStateOf(IntSize.Zero)
                }
                if(timerRestart.value){

                } else{
                    Box(modifier = Modifier
                        .graphicsLayer {
                            val offset = (state.dragPosition + state.dragOffset)
                            scaleX = 1.3f
                            scaleY = 1.3f
                            alpha = if (targetSize == IntSize.Zero) 0f else .9f
                            translationX = offset.x.minus(targetSize.width / 2)
                            translationY = offset.y.minus(targetSize.height / 2)
                        }
                        .onGloballyPositioned {
                            targetSize = it.size
                        }
                    ) {
                        if(selectedLTO.gameMode == "같은 사진 매칭"){
                            Image(painter = painterResource(id = dragAndDropViewModel.mainItem.value!!.image), contentDescription = null)
                        } else if(selectedLTO.gameMode == "일반화 매칭") {
                            Image(painter = painterResource(id = dragAndDropViewModel.firstMainItem.value!!.image), contentDescription = null)
                        }
//                    state.draggableComposable?.invoke()
                    }
                }
            }
        }
    }
}

@Composable
fun<T> DragTarget(
    modifier: Modifier = Modifier,
    viewModel: DragAndDropViewModel,
    data: T,
    timerStart: MutableState<Boolean>,
    timerRestart: MutableState<Boolean>,
    isCardSelectEnd: Boolean,
    setIsCardSelectEnd: (Boolean) -> Unit,
    resetGameButtonIndex:() -> Unit,
    content: @Composable (() -> Unit)
) {
    val context = LocalContext.current
    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current
    Box(modifier = modifier
        .onGloballyPositioned {
            currentPosition = it.localToWindow(
                Offset.Zero
            )
        }
        .pointerInput(Unit) {
            detectDragGestures(onDragStart = {
                //드래그 시작 시 타이머 시작 및 카드 선택 상태 false로 설정
                setIsCardSelectEnd(false)
                timerRestart.value = false
                viewModel.startDragging(context = context)
                currentState.dataToDrop = viewModel.mainItem.value
                currentState.isDragging = true
                currentState.dragPosition = currentPosition + it
                currentState.draggableComposable = content
            }, onDrag = { change, dragAmount ->
                change.consumeAllChanges()
                currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)
            }, onDragEnd = {
                viewModel.stopDragging()
                currentState.isDragging = false
                currentState.dragOffset = Offset.Zero

            }, onDragCancel = {
                viewModel.stopDragging()
                currentState.dragOffset = Offset.Zero
                currentState.isDragging = false
            })
        })
    {
        content()
    }
}



@Composable
fun <T> DropItem(
    modifier: Modifier,
    content: @Composable() (BoxScope.(isInBound: Boolean, data: T?) -> Unit)
) {

    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    var isCurrentDropTarget by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .onGloballyPositioned {
                it.boundsInWindow().let { rect ->
                    isCurrentDropTarget = rect.contains(dragPosition + dragOffset)
                }
             },
        contentAlignment = Alignment.Center
    ){
        val data =
            if (isCurrentDropTarget && !dragInfo.isDragging) dragInfo.dataToDrop as T? else null
        content(isCurrentDropTarget, data)
    }
}

internal class DragTargetInfo {
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var draggableComposable by mutableStateOf<(@Composable () -> Unit)?>(null)
    var dataToDrop by mutableStateOf<Any?>(null)
}