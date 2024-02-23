package inu.thebite.tory.screens.education.compose.lto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato

@Composable
fun SelectedLTORow(
    modifier: Modifier = Modifier,
    allLTOs: List<LtoResponse>?,
    selectedLTO : LtoResponse?,
    selectedSTO: StoResponse?,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel
) {



    BoxWithConstraints(
        modifier = modifier
    ) {
        val maxWidth = maxWidth * 0.7f
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            selectedLTO?.let { selectedLTO ->
                Row(
                    modifier = Modifier
                        .weight(7f)
                        .fillMaxHeight()
                        .padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = selectedLTO.name,

                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = fontFamily_Lato,
                            fontWeight = FontWeight(900),
                            color = Color(0xFF1D1C1D),
                        ),
                        modifier = Modifier
                            .widthIn(max = maxWidth*0.7f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                if (selectedLTO.developType.contains("언어발달")){
                                    //삭제
                                } else {
                                    //추가
                                }
                            },
                            modifier = Modifier
                                .height(25.dp),
                            border = BorderStroke(width = 1.dp, color = Color(0xFF0047B3).copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedLTO.developType.contains("언어발달")) Color(0xFFE0E9F5) else Color.Transparent,
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(vertical = 1.dp, horizontal = 10.dp)
                        ) {
                            Text(
                                text = "언어발달",
                                style = TextStyle(
                                    fontFamily = fontFamily_Inter,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(
                            onClick = {
                                if (selectedLTO.developType.contains("인지발달")){
                                    //삭제
                                } else {
                                    //추가
                                }
                            },
                            modifier = Modifier
                                .height(25.dp),
                            border = BorderStroke(width = 1.dp, color = Color(0xFF0047B3).copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedLTO.developType.contains("인지발달")) Color(0xFFE0E9F5) else Color.Transparent,
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(vertical = 1.dp, horizontal = 10.dp)
                        ) {
                            Text(
                                text = "인지발달",
                                style = TextStyle(
                                    fontFamily = fontFamily_Inter,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            )
                        }
                    }
                }

                LTOButtons(
                    modifier = Modifier,
                    selectedLTO = selectedLTO,

                    ltoViewModel = ltoViewModel,
                    stoViewModel = stoViewModel
                )
            }

        }
    }

}
