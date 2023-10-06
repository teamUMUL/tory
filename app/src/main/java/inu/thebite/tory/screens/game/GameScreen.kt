package inu.thebite.tory.screens.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import inu.thebite.tory.R

@Composable
fun GameScreen(
    dragAndDropViewModel: DragAndDropViewModel = viewModel()
) {
    val mainGameItem = GameItem(
        name = "spoon_1",
        image = R.drawable.spoon_1
    )
    val screenWidth = LocalConfiguration.current.screenWidthDp
    DragableScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                dragAndDropViewModel.items.forEach { gameItem ->
                    DropItem<GameItem>(
                        modifier = Modifier
                            .size(240.dp)
                    ) { isInBound, dragInGameItem ->
                        if(dragInGameItem != null){
                            LaunchedEffect(key1 = dragInGameItem){
                                //드래그해서 들어온 아이템의 이름과 드래그한 곳의 이름이 같은 경우에 맞다는 판정
                                if(dragInGameItem.name == gameItem.name){
                                    dragAndDropViewModel.addGameItem(dragInGameItem)
                                    dragAndDropViewModel.updateGameItem(
                                        GameItem(
                                            dragInGameItem.name,
                                            R.drawable.ellipse
                                        )
                                    )
                                    dragAndDropViewModel.isCorrect()
                                }
                                //여기는 틀린 경우에 들어갈 행동
                                else{

                                }
                            }
                        }
                        Image(
                            painter = painterResource(id = gameItem.image),
                            contentDescription = null
                        )


                    }
                }
            }

            DragTarget(
                dataToDrop = mainGameItem,
                viewModel = dragAndDropViewModel
            ) {

                Image(painter = painterResource(id = mainGameItem.image), contentDescription = null)
            }

            }

        }
    }


