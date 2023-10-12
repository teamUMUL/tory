package inu.thebite.tory.screens.game

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import inu.thebite.tory.R
import inu.thebite.tory.database.STO.STOEntity
import inu.thebite.tory.screens.education.GameViewModel

@Composable
fun GameTopBar(
    context : Context,
    dragAndDropViewModel: DragAndDropViewModel,
    gameViewModel : GameViewModel,
    selectedSTO : STOEntity?,
    gameResultList : List<String>,
    gameButton1Index : Int,
    gameButton2Index : Int,
    timerStart : MutableState<Boolean>,
    timerRestart: MutableState<Boolean>,
    setGameDialog : (Boolean) -> Unit,
    setGameButton1Index : (Int) -> Unit,
    setGameButton2Index : (Int) -> Unit,
    setIsCardSelectEnd : (Boolean) -> Unit

){
    val systemUiController = rememberSystemUiController()
    systemUiController.isStatusBarVisible = false // Status bar
    systemUiController.isNavigationBarVisible = false // Navigation bar
    systemUiController.isSystemBarsVisible = false // Status & Navigation bars
    systemUiController.navigationBarDarkContentEnabled =false
    val gameButtons1 = listOf("P", "C")
    val gameButtons2 = listOf("M", "T")
    val cornerRadius = 4.dp


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.05f)
            .background(Color(0xFFF5F5F5)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp)
                .clickable {
                    setGameDialog(false)
                    dragAndDropViewModel.isWrong()
                    setGameButton1Index(-1)
                    setGameButton2Index(-1)
                },
            painter = painterResource(id = R.drawable.icon_back),
            contentDescription = null
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.secondary,
                    RoundedCornerShape(4.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(6f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    selectedSTO.let {
                        items(gameResultList!!) { gameResult ->
                            if (gameResult != "n") {
                                Text(
                                    modifier = Modifier
                                        .padding(start = 5.dp),
                                    fontSize = 20.sp,
                                    text = gameResult,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }


                        }
                    }
                }

            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Timer(timerStart = timerStart, timerRestart = timerRestart, gameButtonIndex = gameButton1Index, gameViewModel = gameViewModel, dragAndDropViewModel = dragAndDropViewModel, setIsCardSelectEnd = {setIsCardSelectEnd(it)})
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                gameButtons1.forEachIndexed { index, item ->
                    OutlinedButton(
                        onClick = {
                            if(!timerStart.value){
                                setGameButton1Index(index)
                                if(index == gameButton1Index){
                                    setGameButton1Index(-1)
                                    gameViewModel.clearOneGameResult()
                                }
                                if(item == "C"){
                                    gameViewModel.setOneGameResult("C")
                                }else{
                                    gameViewModel.setOneGameResult("P")
                                }
                            }
                        },
                        shape = when (index) {
                            // left outer button
                            0 -> RoundedCornerShape(
                                topStart = cornerRadius,
                                topEnd = 0.dp,
                                bottomStart = cornerRadius,
                                bottomEnd = 0.dp
                            )
                            // right outer button
                            gameButtons1.size - 1 -> RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = cornerRadius,
                                bottomStart = 0.dp,
                                bottomEnd = cornerRadius
                            )
                            // middle button
                            else -> RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        },
                        border = BorderStroke(
                            1.dp,
                            if (gameButton1Index == index) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(
                                alpha = 0.75f
                            )
                        ),
                        modifier = when (index) {
                            0 ->
                                Modifier
                                    .offset(0.dp, 0.dp)
                                    .zIndex(if (gameButton1Index == index) 1f else 0f)

                            else ->
                                Modifier
                                    .offset((-1 * index).dp, 0.dp)
                                    .zIndex(if (gameButton1Index == index) 1f else 0f)
                        },
                        colors =
                        if (gameButton1Index == index) {
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = Color(0xFFE4E4E4),
                                contentColor = Color.Black
                            )
                        } else {
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black
                            )
                        },
                        contentPadding = PaddingValues(0.dp)

                    ) {
                        Text(

                            text = gameButtons1[index],
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }


            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                gameButtons2.forEachIndexed { index, item ->
                    OutlinedButton(
                        onClick = {
                            if(item == "M"){
                                dragAndDropViewModel.restart(context = context)
                            }else{
                                if(gameButton1Index == -1) {
                                    timerStart.value = true
                                }
                            }

                        },
                        shape = when (index) {
                            // left outer button
                            0 -> RoundedCornerShape(
                                topStart = cornerRadius,
                                topEnd = 0.dp,
                                bottomStart = cornerRadius,
                                bottomEnd = 0.dp
                            )
                            // right outer button
                            gameButtons2.size - 1 -> RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = cornerRadius,
                                bottomStart = 0.dp,
                                bottomEnd = cornerRadius
                            )
                            // middle button
                            else -> RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        },
                        border = BorderStroke(
                            1.dp,
                            if (gameButton2Index == index) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(
                                alpha = 0.75f
                            )
                        ),
                        modifier = when (index) {
                            0 ->
                                Modifier
                                    .offset(0.dp, 0.dp)
                                    .zIndex(if (gameButton2Index == index) 1f else 0f)

                            else ->
                                Modifier
                                    .offset((-1 * index).dp, 0.dp)
                                    .zIndex(if (gameButton2Index == index) 1f else 0f)
                        },
                        colors =
                        if (gameButton2Index == index) {
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black
                            )
                        } else {
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black
                            )
                        },
                        contentPadding = PaddingValues(0.dp)

                    ) {
                        Text(
                            text = gameButtons2[index],
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}