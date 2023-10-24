package inu.thebite.tory.screens.education2.compose.lto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import inu.thebite.tory.model.lto.LtoResponse


@Composable
fun LTOStatusButtons(
    modifier : Modifier,
    selectedLTO : LtoResponse,
    selectedLTOStatus : MutableState<String>,
    setSelectedLTOStatus : (String) -> Unit
){
    val cornerRadius = 8.dp
    val ltoStatusList = listOf<String>(
        "진행중",
        "중지",
        "완료"
    )
    LaunchedEffect(Unit){
        selectedLTOStatus.value = selectedLTO.status
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ltoStatusList.forEachIndexed { index, item ->
            OutlinedButton(
                onClick = {

                    setSelectedLTOStatus(item)
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
                    ltoStatusList.size - 1 -> RoundedCornerShape(
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
                    if (selectedLTO.status == item) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(
                        alpha = 0.75f
                    )
                ),
                modifier = when (index) {
                    0 ->
                        Modifier
                            .offset(0.dp, 0.dp)
                            .zIndex(if (selectedLTO.status == item) 1f else 0f)

                    else ->
                        Modifier
                            .offset((-1 * index).dp, 0.dp)
                            .zIndex(if (selectedLTO.status == item) 1f else 0f)
                },
                colors =
                if (selectedLTOStatus.value == item) {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor =
                        when (index) {
                            0 -> {
                                Color.Blue.copy(alpha = 0.5f)
                            }

                            1 -> {
                                Color.Red.copy(alpha = 0.5f)
                            }

                            else -> {
                                Color.Green.copy(alpha = 0.5f)
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
                                Color.Red.copy(alpha = 0.2f)
                            }

                            else -> {
                                Color.Green.copy(alpha = 0.2f)
                            }
                        },
                        contentColor = Color.Black
                    )
                }

            ) {
                Text(
                    text = ltoStatusList[index]
                )
            }
        }
    }
}