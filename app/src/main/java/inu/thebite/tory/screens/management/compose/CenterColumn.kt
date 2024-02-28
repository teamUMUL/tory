package inu.thebite.tory.screens.management.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.screens.education.screen.clickableWithNoRipple
import inu.thebite.tory.screens.setting.dialog.AddCenterDialog
import inu.thebite.tory.screens.setting.viewmodel.CenterViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.CenterSelectViewModel
import inu.thebite.tory.ui.theme.fontFamily_Lato

@Composable
fun CenterColumn(
    modifier: Modifier = Modifier,
    centerViewModel: CenterViewModel,
    centerSelectViewModel: CenterSelectViewModel
) {
    val context = LocalContext.current
    val selectedCenter by centerViewModel.selectedCenter.collectAsState()
    val allCenters by centerViewModel.allCenters.collectAsState()

    // Center 추가
    val (addCenterDialog, setAddCenterDialog) = rememberSaveable {
        mutableStateOf(false)
    }
    if (addCenterDialog) {
        AddCenterDialog(
            context = context,
            centerViewModel = centerViewModel,
            setAddCenterDialog = { setAddCenterDialog(it) },
            isUpdate = false,
            selectedCenter = selectedCenter,
            onFinish = {
                centerSelectViewModel.getAllCenters()
            }
        )
    }

    // Center 수정
    val (updateCenterDialog, setUpdateCenterDialog) = rememberSaveable {
        mutableStateOf(false)
    }
    if (updateCenterDialog) {
        AddCenterDialog(
            context = context,
            centerViewModel = centerViewModel,
            setAddCenterDialog = { setUpdateCenterDialog(it) },
            isUpdate = true,
            selectedCenter = selectedCenter,
            onFinish = {
                centerSelectViewModel.getAllCenters()
            }
        )
    }

    // Center 삭제
    val (deleteCenterDialog, setDeleteCenterDialog) = rememberSaveable {
        mutableStateOf(false)
    }
    if (deleteCenterDialog){
        selectedCenter?.let { selectedCenter ->
            AlertDialog(
                title = {Text(text = "센터 : ${selectedCenter.name} 삭제하시겠습니까?")},
                onDismissRequest = {setDeleteCenterDialog(false)},
                confirmButton = { TextButton(onClick = {
                    centerViewModel.deleteCenter(centerEntity = selectedCenter)
                    setDeleteCenterDialog(false)
                }) {
                    Text(text = "삭제")
                }
                },
                dismissButton = { TextButton(onClick = { setDeleteCenterDialog(false) }) {
                    Text(text = "닫기")
                }
                }
            )
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = allCenters?.let { Arrangement.Top } ?: run {Arrangement.Bottom}
    ) {
        allCenters?.let { allCenters ->
            LazyColumn(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(allCenters) { center ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp)
                            .clickable {
                                centerViewModel.setSelectedCenter(center)
                            }
                            .background(
                                if (selectedCenter == center) Color(0xFF0047B3) else Color.Transparent
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(8f)
                        ) {
                            Text(
                                text = "#",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    lineHeight = 22.sp,
                                    fontFamily = fontFamily_Lato,
                                    fontWeight = FontWeight(400),
                                    color = if (selectedCenter == center) Color.White else Color.Black,
                                ),
                                modifier = Modifier
                                    .padding(
                                        start = 20.dp,
                                        end = 0.dp,
                                        top = 4.dp,
                                        bottom = 4.dp
                                    )
                            )
                            Text(
                                text = center.name,
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    lineHeight = 22.sp,
                                    fontFamily = fontFamily_Lato,
                                    fontWeight = FontWeight(400),
                                    color = if (selectedCenter == center) Color.White else Color.Black,
                                ),
                                modifier = Modifier
                                    .padding(vertical = 4.dp, horizontal = 10.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        if (selectedCenter == center) {
                            Row(
                                modifier = Modifier
                                    .weight(2f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .clickableWithNoRipple {
                                            setUpdateCenterDialog(true)
                                        }
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .clickableWithNoRipple {
                                            setDeleteCenterDialog(true)
                                        }
                                )
                            }
                        }


                    }
                }
            }

        }
        Button(
            onClick = {
                setAddCenterDialog(true)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            border = BorderStroke(width = 1.dp, color = Color(0xFFCECECE)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(
                text = "센터 추가",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = fontFamily_Lato,
                    fontWeight = FontWeight(500),
                    color = Color.Black
                )
            )
        }

    }
}