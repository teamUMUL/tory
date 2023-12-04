package inu.thebite.tory.screens.education2.compose.sto2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.R
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.ui.theme.fontFamily_Lato


@Composable
fun STOSelector(
    stoViewModel: STOViewModel
) {
    val allSTOs by stoViewModel.allSTOs.collectAsState()
    val selectedSTO by stoViewModel.selectedSTO.collectAsState()
    Column(
    ) {
        allSTOs?.let { allSTOs ->
            allSTOs.forEachIndexed { index, sto ->
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
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
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

}