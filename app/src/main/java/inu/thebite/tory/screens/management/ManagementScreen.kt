package inu.thebite.tory.screens.management

import android.text.TextPaint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.screens.management.compose.CenterColumn
import inu.thebite.tory.screens.management.compose.ChildClassRow
import inu.thebite.tory.screens.setting.dialog.AddChildInfoDialog
import inu.thebite.tory.screens.setting.viewmodel.CenterViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildClassViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildInfoViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import java.lang.Integer.min

@Composable
fun ManagementScreen(
    centerViewModel: CenterViewModel,
    childClassViewModel: ChildClassViewModel,
    childInfoViewModel: ChildInfoViewModel
) {
    val context = LocalContext.current

    val centers by centerViewModel.centers.collectAsState()
    val selectedCenter by centerViewModel.selectedCenter.collectAsState()
    val allCenters by centerViewModel.allCenters.collectAsState()

    val selectedChildClass by childClassViewModel.selectedChildClass.collectAsState()
    val allChildClasses by childClassViewModel.allChildClasses.collectAsState()

    val selectedChildInfo by childInfoViewModel.selectedChildInfo.collectAsState()
    val allChildInfos by childInfoViewModel.allChildInfos.collectAsState()

    val (addChildInfoDialog, setAddChildInfoDialog) = rememberSaveable {
        mutableStateOf(false)
    }
    val (updateChildInfoDialog, setUpdateChildInfoDialog) = rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        centerViewModel.getAllCenters()
    }

    LaunchedEffect(selectedCenter, allChildClasses) {
        selectedCenter?.let {
            childClassViewModel.getAllChildClasses(
                it.id
            )
            childClassViewModel.clearSelectedChildClass()
//            childClassViewModel.clearAllChildClasses()

            childInfoViewModel.clearSelectedChildInfo()
//            childInfoViewModel.clearAllChildInfos()
        }
    }

    LaunchedEffect(selectedChildClass, allChildInfos) {
        selectedChildClass?.let { selectedChildClass ->
            childInfoViewModel.getAllChildInfos(
                selectedChildClass.id
            )
            childInfoViewModel.clearSelectedChildInfo()
        } ?: run {
            childInfoViewModel.clearAllChildInfos()
        }
    }



    if (addChildInfoDialog) {
        AddChildInfoDialog(
            context = context,
            childInfos = allChildInfos,
            selectedChildClass = selectedChildClass,
            childInfoViewModel = childInfoViewModel,
            setAddChildInfoDialog = { setAddChildInfoDialog(it) },
            selectedChildInfo = selectedChildInfo,
            isUpdate = false
        )
    }
    if (updateChildInfoDialog) {
        AddChildInfoDialog(
            context = context,
            childInfos = allChildInfos,
            selectedChildClass = selectedChildClass,
            childInfoViewModel = childInfoViewModel,
            setAddChildInfoDialog = { setUpdateChildInfoDialog(it) },
            selectedChildInfo = selectedChildInfo,
            isUpdate = true
        )
    }





    val (deleteChilInfoDialog, setDeleteChildInfoDialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if (deleteChilInfoDialog){
        selectedChildInfo?.let { selectedChildInfo ->
            AlertDialog(
                title = {Text(text = "Student : ${selectedChildInfo.name} 삭제하시겠습니까?")},
                onDismissRequest = {setDeleteChildInfoDialog(false)},
                confirmButton = { TextButton(onClick = {
                    childInfoViewModel.deleteChildInfo(selectedChildInfo = selectedChildInfo)
                    setDeleteChildInfoDialog(false)
                }) {
                    Text(text = "삭제")
                }
                },
                dismissButton = { TextButton(onClick = { setDeleteChildInfoDialog(false) }) {
                    Text(text = "닫기")
                }
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Divider(thickness = 1.dp, color = Color.LightGray)
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CenterColumn(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFF3F3F3)),
                centerViewModel = centerViewModel
            )
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp), color = Color.LightGray
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                ChildClassRow(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color(0xFF0047B3),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(40.dp),
                    centerViewModel = centerViewModel,
                    childClassViewModel = childClassViewModel
                )

                Column(
                    modifier = Modifier
                        .weight(7f)
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color(0xFF0047B3),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(40.dp)
                ) {
                    Text(
                        text = "Children",
                        style = TextStyle(
                            fontSize = 33.sp,
                            fontFamily = fontFamily_Inter,
                            fontWeight = FontWeight(500),
                            color = Color.Black
                        ),
                        modifier = Modifier.weight(1.5f)
                    )
                    Column(
                        modifier = Modifier
                            .weight(7f)
                            .fillMaxWidth()
                    ) {
                        allChildInfos?.let { allChildInfos ->
                            val itemsPerRow = 9
                            val childInfoRows = allChildInfos.size / itemsPerRow
                            val remainItems = allChildInfos.size % itemsPerRow
                            BoxWithConstraints {
                                val childNameWidth = maxWidth / 10

                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(20.dp)
                                ){
                                    repeat(childInfoRows){ rowIndex ->
                                        item {
                                            LazyRow(
                                                userScrollEnabled = false,
                                                horizontalArrangement = Arrangement.spacedBy(30.dp)
                                            ){
                                                items(itemsPerRow){ itemIndex ->
                                                    val index = rowIndex * itemsPerRow + itemIndex
                                                    val childInfo = allChildInfos[index]
                                                    Button(
                                                        onClick = {
                                                            childInfoViewModel.setSelectedChildInfo(childInfoEntity = childInfo)
                                                        },
                                                        colors = ButtonDefaults.buttonColors(
                                                            containerColor = if (selectedChildInfo == childInfo) Color(0xFFE0E9F5) else Color.White,
                                                            contentColor = Color.Black
                                                        ),
                                                        border = BorderStroke(width = 0.5.dp, color = Color(0xFF0047B3).copy(alpha = 0.5f)),
                                                        shape = RoundedCornerShape(20.dp),
                                                        modifier = Modifier
                                                            .widthIn(max = childNameWidth)
                                                    ) {
                                                        Text(
                                                            text = childInfo.name,
                                                            style = TextStyle(
                                                                fontSize = 20.sp,
                                                                fontWeight = FontWeight.Medium,
                                                                fontFamily = fontFamily_Inter,
                                                                color = Color.Black
                                                            ),
                                                            maxLines = 1,
                                                            overflow = TextOverflow.Ellipsis
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (remainItems > 0) {
                                        item {
                                            LazyRow(
                                                userScrollEnabled = false,
                                                horizontalArrangement = Arrangement.spacedBy(30.dp)
                                            ){
                                                items(remainItems){itemIndex ->
                                                    val index = childInfoRows * itemsPerRow + itemIndex
                                                    val childInfo = allChildInfos[index]
                                                    Button(
                                                        onClick = {
                                                            childInfoViewModel.setSelectedChildInfo(childInfoEntity = childInfo)
                                                        },
                                                        colors = ButtonDefaults.buttonColors(
                                                            containerColor = if (selectedChildInfo == childInfo) Color(0xFFE0E9F5) else Color.White,
                                                            contentColor = Color.Black
                                                        ),
                                                        border = BorderStroke(width = 0.5.dp, color = Color(0xFF0047B3).copy(alpha = 0.5f)),
                                                        shape = RoundedCornerShape(20.dp)
                                                    ) {
                                                        Text(
                                                            text = childInfo.name,
                                                            style = TextStyle(
                                                                fontSize = 20.sp,
                                                                fontWeight = FontWeight.Medium,
                                                                fontFamily = fontFamily_Inter,
                                                                color = Color.Black
                                                            )
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.5f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(100.dp)
                    ) {
                        if (selectedChildClass.isNotNull()){
                            Button(
                                onClick = {
                                    setAddChildInfoDialog(true)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF0047B3),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "추가",
                                    style = TextStyle(
                                        fontFamily = fontFamily_Inter,
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                        if (selectedChildInfo.isNotNull()){
                            Button(
                                onClick = {
                                    setUpdateChildInfoDialog(true)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF0047B3),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "수정",
                                    style = TextStyle(
                                        fontFamily = fontFamily_Inter,
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White
                                    )
                                )
                            }
                            Button(
                                onClick = {
                                    setDeleteChildInfoDialog(true)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFBFBFBF),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "삭제",
                                    style = TextStyle(
                                        fontFamily = fontFamily_Inter,
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                    }

                }
            }

        }
    }
}



