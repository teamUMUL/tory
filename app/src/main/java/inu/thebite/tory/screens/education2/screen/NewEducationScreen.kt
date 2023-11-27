package inu.thebite.tory.screens.education2.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.R
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education2.compose.dev.DEVSelector
import inu.thebite.tory.screens.education2.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education2.viewmodel.EducationViewModel
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.game.viewmodel.GameViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato

@Composable
fun NewEducationScreen(
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel,
    imageViewModel: ImageViewModel,
    gameViewModel: GameViewModel,
    dragAndDropViewModel: DragAndDropViewModel,
    educationViewModel: EducationViewModel
) {
    val context = LocalContext.current

    val selectedDEV by devViewModel.selectedDEV.collectAsState()

    val allLTOs by ltoViewModel.allLTOs.collectAsState()
    val ltos by ltoViewModel.ltos.collectAsState()
    val selectedLTO by ltoViewModel.selectedLTO.collectAsState()

    val selectedSTO by stoViewModel.selectedSTO.collectAsState()

    LaunchedEffect(selectedDEV) {
        selectedDEV?.let { ltoViewModel.getLTOsByDEV(selectedDEV = it) }
    }

    LaunchedEffect(selectedLTO) {
        selectedLTO?.let { stoViewModel.getSTOsByLTO(selectedLTO = it) }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
        ) {
            DEVSelector(
                modifier = Modifier
                    .weight(0.5f),
                devViewModel = devViewModel,
                ltoViewModel = ltoViewModel
            )
            Divider(thickness = 1.dp, color = Color.LightGray)
            ltos?.let {
                LTOAndSTOSelector(
                    modifier = Modifier
                        .weight(9.5f),
                    selectedSTO = selectedSTO,
                    ltos = it,
                    ltoViewModel = ltoViewModel,
                    stoViewModel = stoViewModel
                )
            } ?: LTOAndSTONull(modifier = Modifier.weight(9.5f))
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp), color = Color.LightGray
        )
        Column(
            modifier = Modifier
                .weight(8f)
                .fillMaxHeight()
        ) {
            SelectedLTOAndSTOInfo(
                allLTOs = allLTOs,
                selectedSTO = selectedSTO
            )

        }
//        Column(
//            modifier = Modifier
//                .weight(7f)
//                .fillMaxHeight()
//        ) {
//
//        }
//
//        Divider(thickness = 4.dp, color = MaterialTheme.colorScheme.tertiary)
//        LTOAndSTOContainer(
//            context = context,
//            devViewModel = devViewModel,
//            ltoViewModel = ltoViewModel,
//            educationViewModel = educationViewModel,
//            stoViewModel =  stoViewModel,
//            imageViewModel = imageViewModel,
//            dragAndDropViewModel = dragAndDropViewModel,
//            gameViewModel = gameViewModel
//        )

    }

}

@Composable
fun SelectedLTOAndSTOInfo(
    allLTOs: List<LtoResponse>?,
    selectedSTO: StoResponse?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SelectedLTORow(
            modifier = Modifier.weight(0.5f),
            allLTOs = allLTOs,
            selectedSTO = selectedSTO
        )
        Divider(thickness = 1.dp, color = Color.LightGray)
        SelectedSTOInfo(
            modifier = Modifier.weight(9.5f),
            selectedSTO = selectedSTO
        )
    }
}

@Composable
fun SelectedSTOInfo(
    modifier: Modifier = Modifier,
    selectedSTO: StoResponse?
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        SelectedSTORow(
            modifier = Modifier.weight(0.7f),
            selectedSTO = selectedSTO
        )
        SelectedSTODetail(
            modifier = Modifier.weight(9.25f),
            selectedSTO = selectedSTO
        )
    }
}

@Composable
fun SelectedSTORow(
    modifier: Modifier = Modifier,
    selectedSTO: StoResponse?
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        selectedSTO?.let { selectedSTO ->
            Row(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .background(
                            color =
                            when (selectedSTO.status) {
                                "진행중" -> {
                                    Color(0xFF40B9FC)
                                }

                                "준거 도달" -> {
                                    Color(0xFF34C648)
                                }

                                "중지" -> {
                                    Color(0xFFFC605C)
                                }

                                else -> {
                                    MaterialTheme.colorScheme.primary
                                }
                            },
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,

                    ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedSTO.name,
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 22.sp,
                                fontFamily = fontFamily_Lato,
                                fontWeight = FontWeight(900),
                                color = Color(0xFF1D1C1D),

                                )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = selectedSTO.registerDate,
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                fontFamily = fontFamily_Lato,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF616061),

                                )
                        )

                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = replaceNewLineWithSpace(selectedSTO.contents),
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 22.sp,
                                fontFamily = fontFamily_Lato,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF1D1C1D),

                                ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }



            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                OutlinedButton(
                    onClick = {},

                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(width = 2.dp, color = Color(0xFF0047B3)),
                    contentPadding = PaddingValues(vertical = 5.dp, horizontal = 20.dp),

                    ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow, contentDescription = null,
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "교육 시작",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = fontFamily_Lato,
                            fontWeight = FontWeight(900),
                            color = Color(0xFF1D1C1D),

                            )
                    )
                }
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_calendar_2),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Gray
                    )
                }
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_edit),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color.Gray
                    )

                }
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

fun replaceNewLineWithSpace(input: String): String {
    return input.replace("\n", " ")
}

@Composable
fun SelectedSTODetail(
    modifier: Modifier = Modifier,
    selectedSTO: StoResponse?
) {
    Row(
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp, top = 0.dp)
            .fillMaxWidth()
            .background(
                color = selectedSTO?.let { selectedSTO ->
                    when (selectedSTO.status) {
                        "진행중" -> {
                            Color(0xFFC0E9EF)
                        }

                        "완료" -> {
                            Color(0xFFCCEFC0)

                        }

                        "중지" -> {
                            Color(0xFFEFC0C0)

                        }

                        else -> {
                            MaterialTheme.colorScheme.tertiary
                        }
                    }
                } ?: MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(size = 10.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .weight(5f)
                .fillMaxHeight()
                .padding(10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
        ) {

        }
        Column(
            modifier = Modifier
                .weight(5f)
                .fillMaxHeight()
                .padding(10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
        ) {

        }
    }
}

@Composable
fun SelectedLTORow(
    modifier: Modifier = Modifier,
    allLTOs: List<LtoResponse>?,
    selectedSTO: StoResponse?
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        selectedSTO?.let { selectedSTO ->
            Text(
                text = selectedSTO.lto.name,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = fontFamily_Lato,
                    fontWeight = FontWeight(900),
                    color = Color(0xFF1D1C1D),
                ),
                modifier = Modifier
                    .padding(horizontal = 15.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            LTOButtons(
                selectedLTO = selectedSTO.lto
            )
        }

    }
}

@Composable
fun LTOButtons(
    selectedLTO: LtoResponse
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val buttonList = listOf(
                "완료",
                "진행중",
                "중지"
            )
            buttonList.forEach { button ->
                OutlinedButton(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 2.dp),
                    onClick = {},
                    border = BorderStroke(
                        width = 1.dp,
                        color = when (button) {
                            "완료" -> Color(0xFF34C648)
                            "진행중" -> Color(0xFF40B9FC)
                            "중지" -> Color(0xFFFC605C)
                            else -> Color.Black
                        }
                    ),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (button) {
                            "완료" -> if (selectedLTO.status == button) Color(0xFF34C648) else Color.Transparent
                            "진행중" -> if (selectedLTO.status == button) Color(0xFF40B9FC) else Color.Transparent
                            "중지" -> if (selectedLTO.status == button) Color(0xFFFC605C) else Color.Transparent
                            else -> if (selectedLTO.status == button) Color.White else Color.White
                        },
                        contentColor = when (button) {
                            "완료" -> if (selectedLTO.status == button) Color.White else Color(
                                0xFF34C648
                            )

                            "진행중" -> if (selectedLTO.status == button) Color.White else Color(
                                0xFF40B9FC
                            )

                            "중지" -> if (selectedLTO.status == button) Color.White else Color(
                                0xFFFC605C
                            )

                            else -> if (selectedLTO.status == button) Color.White else Color.Black
                        }
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = button,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = fontFamily_Lato,
                            fontWeight = FontWeight(500),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
        IconButton(
            onClick = {},
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_edit),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.8f),
                tint = Color.Gray
            )

        }
        IconButton(
            onClick = {},
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_lto_graph),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.6f),
            )

        }
        OutlinedButton(
            modifier = Modifier
                .padding(vertical = 6.dp, horizontal = 16.dp),
            onClick = {},
            border = BorderStroke(width = 0.8.dp, color = Color(0xFFCECECE)),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 15.dp)
        ) {
            Text(
                text = "STO 추가",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = fontFamily_Inter,
                    fontWeight = FontWeight(500),
                    color = Color.Black,
                )
            )
        }
    }
}

@Composable
fun LTOAndSTONull(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "데이터 없음",
            style = TextStyle(
                fontSize = 35.sp,
                lineHeight = 22.sp,
                fontFamily = fontFamily_Lato,
                fontWeight = FontWeight(800),
                color = Color(0xFF000000),
            )
        )
    }
}

@Composable
fun LTOAndSTOSelector(
    modifier: Modifier = Modifier,
    selectedSTO: StoResponse?,
    ltos: List<LtoResponse>,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(0.9f)
        ) {
            items(ltos) { lto ->
                val expandedState =
                    rememberSaveable { mutableStateOf(selectedSTO?.let { it.lto == lto } ?: false) }
                val rotationState by animateFloatAsState(
                    targetValue = if (expandedState.value) 180f else 0f, label = ""
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    shape = RectangleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = when (lto.status) {
                            "진행중" -> {
                                Color(0xFFC0E9EF)
                            }

                            "완료" -> {
                                Color(0xFFCCEFC0)

                            }

                            "중지" -> {
                                Color(0xFFEFC0C0)

                            }

                            else -> {
                                MaterialTheme.colorScheme.tertiary
                            }
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 10.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                expandedState.value = !expandedState.value
                                ltoViewModel.setSelectedLTO(lto)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_arrowdown),
                            contentDescription = "Drop-Down Arrow",
                            modifier = Modifier
                                .rotate(rotationState)
                                .size(12.dp),
                            tint = Color.Black
                        )
                        Text(
                            text = lto.name,
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 22.sp,
                                fontFamily = fontFamily_Lato,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF000000),
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    AnimatedVisibility(visible = expandedState.value) {
                        STOSelector(
                            lto = lto,
                            stoViewModel = stoViewModel
                        )
                    }
                }
            }

        }

        OutlinedButton(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            border = BorderStroke(width = 0.8.dp, color = Color(0xFFCECECE))
        ) {
            Text(
                text = "LTO 추가",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = fontFamily_Inter,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),

                    )
            )
        }
    }
}

@Composable
fun STOSelector(
    lto: LtoResponse,
    stoViewModel: STOViewModel
) {
    val stos = stoViewModel.getSTOsByLTOWithReturn(lto)
    val selectedSTO by stoViewModel.selectedSTO.collectAsState()
    Column(
    ) {
        stos?.let { stos ->
            stos.forEachIndexed { index, sto ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            stoViewModel.setSelectedSTO(sto)
                        }
                        .background(
                            if (selectedSTO == sto) Color(0xFF1264A3) else Color.Transparent
                        )
                        .padding(start = 20.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                color = when (sto.status) {
                                    "진행중" -> {
                                        Color(0xFF40B9FC)
                                    }

                                    "준거 도달" -> {
                                        Color(0xFF34C648)
                                    }

                                    "중지" -> {
                                        Color(0xFFFC605C)
                                    }

                                    else -> {
                                        MaterialTheme.colorScheme.primary
                                    }
                                },
                                shape = CircleShape
                            )
                    )
                    Text(
                        text = sto.name,
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 22.sp,
                            fontFamily = fontFamily_Lato,
                            fontWeight = FontWeight(400),
                            color = if (selectedSTO == sto) Color.White else Color.Black
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }

}


