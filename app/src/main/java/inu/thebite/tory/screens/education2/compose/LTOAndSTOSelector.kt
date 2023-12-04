package inu.thebite.tory.screens.education2.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.R
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education2.compose.dialog.lto.AddLTODialog
import inu.thebite.tory.screens.education2.compose.sto2.STOSelector
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato


@Composable
fun LTOAndSTOSelector(
    modifier: Modifier = Modifier,
    selectedDEV: DomainResponse?,
    selectedSTO: StoResponse?,
    ltos: List<LtoResponse>,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel
) {
    val context = LocalContext.current

    val (addLTODialog, setAddLTODialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if(addLTODialog){
        selectedDEV?.let { selectedDEV ->
            AddLTODialog(
                context = context,
                setAddLTOItem = {setAddLTODialog(it)},
                selectedDEV = selectedDEV,
                ltoViewModel = ltoViewModel
            )
        }
    }

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
                    rememberSaveable { mutableStateOf(selectedSTO?.let { it.ltoId == lto } ?: false) }
                val rotationState by animateFloatAsState(
                    targetValue = if (expandedState.value) 180f else 0f, label = ""
                )
                LaunchedEffect(selectedSTO){
                    selectedSTO?.let { selectedSTO ->
                        if (selectedSTO.ltoId == lto){
                            expandedState.value = true
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
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
            onClick = {
                setAddLTODialog(true)
            },
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