package inu.thebite.tory.screens.education2.compose.sto

import android.content.Context
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.R
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education.Compose.STODetailTableAndGameResult
import inu.thebite.tory.screens.education2.viewmodel.EducationViewModel
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel

@Composable
fun STOItemColumn(
    ltoViewModel: LTOViewModel,
    educationViewModel : EducationViewModel
){
    val allLTOs by ltoViewModel.allLTOs.collectAsState()
    val ltos by ltoViewModel.ltos.collectAsState()
    val selectedLTO by ltoViewModel.selectedLTO.collectAsState()
    var selectedSTO by remember {
        mutableStateOf<StoResponse?>(null)
    }
    var isExpanded by remember { mutableStateOf (false) }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 0f else 180f
    )

    val selectedSTODetailGameDataIndex = remember { mutableIntStateOf(0) }
    val selectedSTOStatus = remember { mutableStateOf("") }

    val selectedEducation by educationViewModel.selectedEducation.collectAsState()

    val stos = mutableListOf<StoResponse>()
    for(i in 1..10){
        stos.add(
            StoResponse(
                id = i.toLong() ,
                templateNum = i,
                status = "",
                name = "$i. 3 Array) 시도 수 : 20, 메인 아이템 : 연필, 예시 아이템 : 연필, 숟가락, 컵 2023/10/20",
                contents = "",
                count = 15,
                goal = 90,
                goalPercent = 90,
                achievementOrNot = "",
                urgeType = "",
                urgeContent = "",
                enforceContent = "",
                memo = "",
                hitGoalDate = "",
                registerDate = "",
                delYN = "",
                lto = selectedLTO ?: LtoResponse(
                    id = i.toLong(),
                    templateNum = 0,
                    status = "",
                    name = "",
                    contents = "",
                    game = "",
                    achieveDate = "",
                    registerDate = "",
                    delYN = "",
                    domain = DomainResponse(
                        id = 0L,
                        templateNum = 0,
                        type = "",
                        status = "",
                        name = "",
                        contents = "",
                        useYN = "",
                        delYN = "",
                        registerDate = ""
                    )
                )
            )
        )
    }

    stos.reverse()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){

        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ){
            items(stos){sto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .border(
                            2.dp,
                            color = if (selectedSTO == sto) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(8.dp)
                        )
//                        .clickable(
//                            interactionSource = remember { MutableInteractionSource() },
//                            indication = null
//                        ) {
//
//                        }
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            )
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ){
                    Column(
                        modifier = Modifier
                            .background(color =
                                if (selectedSTO == sto){
                                    when(selectedSTOStatus.value){
                                        "진행중" -> {
                                            Color.Blue.copy(alpha = 0.1f)
                                        }
                                        "준거 도달" -> {
                                            Color.Green.copy(alpha = 0.1f)

                                        }
                                        "중지" -> {
                                            Color.Red.copy(alpha = 0.1f)

                                        }
                                        else -> {
                                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                                        }
                                    }
                                } else {
                                    when(sto.status){
                                        "진행중" -> {
                                            Color.Blue.copy(alpha = 0.1f)
                                        }
                                        "준거 도달" -> {
                                            Color.Green.copy(alpha = 0.1f)

                                        }
                                        "중지" -> {
                                            Color.Red.copy(alpha = 0.1f)

                                        }
                                        else -> {
                                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                                        }
                                    }
                                }
                            )

                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    if (selectedSTO == sto) {
                                        selectedSTO = null
                                        educationViewModel.clearSelectedEducation()
                                        selectedSTOStatus.value = ""
                                    } else {
                                        selectedSTO = sto
                                        educationViewModel.setSelectedEducation(selectedSTO = sto)
                                        selectedSTOStatus.value = selectedSTO!!.status
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = sto.name ,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                                modifier = Modifier
                                    .weight(6f)
                                    .padding(start = 20.dp),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                            Row(
                                modifier = Modifier
                                    .weight(
                                        if(selectedSTO == sto){
                                            6f
                                        } else {
                                            1f
                                        }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                if(selectedSTO == sto){
                                    selectedSTO?.let { selectedSTO->
                                        STOStatusButtons(
                                            modifier = Modifier.weight(4f),
                                            selectedSTO = selectedSTO,
                                            selectedSTOStatus = selectedSTOStatus,
                                            setSelectedSTOStatus = {
                                                selectedSTOStatus.value = it
                                                selectedSTO.status = it
                                                Log.d("클릭 감지", selectedSTO.toString())
                                            }
                                        )
                                        stoSettingButtons(
                                            modifier = Modifier.weight(2f)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                    }
                                }
                            }

                        }

                        if(selectedSTO == sto){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(700.dp)
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .background(
                                        color = Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                selectedSTO?.let { selectedSTO->
                                    selectedEducation?.let {selectedEducation ->
                                        STODetailTableAndGameResult(
                                            selectedSTO = selectedSTO,
                                            selectedEducation = selectedEducation,
                                            selectedSTODetailGameDataIndex = selectedSTODetailGameDataIndex,
                                            educationViewModel = educationViewModel,
                                            setSelectedSTOStatus = {
                                                selectedSTOStatus.value = it
                                                selectedSTO.status = it
                                            }
                                        )
                                    }
                                    gameReadyRow(
                                        selectedSTO = selectedSTO,
                                        setAddGameItem = {},
                                    )

                                }

                            }
                        }
                    }
                }

            }
        }

    }
}




@Composable
fun stoSettingButtons(
    modifier : Modifier
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            modifier = Modifier
                .size(40.dp),
            border = BorderStroke(1.dp, Color.Black),
            shape = RoundedCornerShape(5.dp),
            onClick = {
//                                            setUpdateSTOItem(true)
            },
            contentPadding = PaddingValues(2.dp)
        ){
            Icon(
                modifier = Modifier
                    .size(35.dp),
                painter = painterResource(id = R.drawable.icon_edit),
                contentDescription = null,
                tint = Color.Black)
        }
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedButton(
            modifier = Modifier
                .width(100.dp)
                .height(40.dp),
            border = BorderStroke(1.dp, Color.Black),
            shape = RoundedCornerShape(5.dp),
            onClick = {
//                                            gameStart()
            },
            contentPadding = PaddingValues(6.dp)
        ){
            Text(
                modifier = Modifier
                    .fillMaxSize(),
                text = "교육시작",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

@Composable
fun STOStatusButtons(
    modifier : Modifier,
    selectedSTO : StoResponse,
    setSelectedSTOStatus : (String) -> Unit,
    selectedSTOStatus : MutableState<String>
){
    val cornerRadius = 8.dp
    val stoStatusList = listOf<String>(
        "진행중",
        "준거 도달",
        "중지"
    )
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        stoStatusList.forEachIndexed { index, item ->
            OutlinedButton(
                onClick = {
                    if(item == selectedSTOStatus.value){
                        setSelectedSTOStatus("")
                    } else {
                        setSelectedSTOStatus(item)
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
                    stoStatusList.size - 1 -> RoundedCornerShape(
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
                    if (selectedSTO.status == item) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(
                        alpha = 0.75f
                    )
                ),
                modifier = when (index) {
                    0 ->
                        Modifier
                            .offset(0.dp, 0.dp)
                            .zIndex(if (selectedSTO.status == item) 1f else 0f)

                    else ->
                        Modifier
                            .offset((-1 * index).dp, 0.dp)
                            .zIndex(if (selectedSTO.status == item) 1f else 0f)
                },
                colors =
                if (selectedSTO.status == item) {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor =
                        when (index) {
                            0 -> {
                                Color.Blue.copy(alpha = 0.5f)
                            }

                            1 -> {
                                Color.Green.copy(alpha = 0.5f)
                            }

                            else -> {
                                Color.Red.copy(alpha = 0.5f)
                            }
                        },
                        contentColor = Color.White
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor =
                        when (index) {
                            0 -> {
                                Color.Blue.copy(alpha = 0.2f)
                            }

                            1 -> {
                                Color.Green.copy(alpha = 0.2f)
                            }

                            else -> {
                                Color.Red.copy(alpha = 0.2f)
                            }
                        },
                        contentColor = Color.Black
                    )
                }

            ) {
                Text(
                    text = stoStatusList[index]
                )
            }
        }
    }
}


@Composable
fun gameReadyRow(
//    dragAndDropViewModel : DragAndDropViewModel,
    selectedSTO : StoResponse,
    setAddGameItem : (Boolean) -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
    ) {
        if(selectedSTO.isNotNull()){
            Box(modifier = Modifier
                .width(100.dp)
                .fillMaxHeight()
                .padding(5.dp)
                .clickable {
                    setAddGameItem(true)

                },
                contentAlignment = Alignment.Center
            ){
                Column(modifier = Modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        modifier = Modifier
                            .size(80.dp),
                        painter = painterResource(id = R.drawable.icon_add_square_light),
                        contentDescription = null
                    )
                }

            }

//            LazyRow(
//            ) {
//                items(selectedSTO.gameItems ?: emptyList()) { selectedGameItem ->
//                    val imageResource = getResourceIdByName(selectedGameItem, context)
//                    val isSelected = selectedGameItem == mainGameItem
//
//                    Image(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
//                            .clickable {
//                                // Update the selected item when clicked
//                                if (isSelected) {
//                                    setMainGameItem("")
//                                    dragAndDropViewModel.clearMainItem()
//                                } else {
//                                    setMainGameItem(selectedGameItem)
//                                    dragAndDropViewModel.setMainItem(
//                                        GameItem(
//                                            name = selectedGameItem,
//                                            image = imageResource
//                                        )
//                                    )
//
//                                }
//                            },
//                        painter = painterResource(id = imageResource),
//                        contentDescription = null,
//                        alpha = if (isSelected) 1.0f else 0.5f
//                    )
//                }
//            }
        }
    }
}