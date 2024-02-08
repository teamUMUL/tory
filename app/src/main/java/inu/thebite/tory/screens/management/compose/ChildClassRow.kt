package inu.thebite.tory.screens.management.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.screens.setting.dialog.AddChildClassDialog
import inu.thebite.tory.screens.setting.dialog.AddChildInfoDialog
import inu.thebite.tory.screens.setting.viewmodel.CenterViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildClassViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter

@Composable
fun ChildClassRow(
    modifier: Modifier = Modifier,
    centerViewModel: CenterViewModel,
    childClassViewModel: ChildClassViewModel,
) {
    val context = LocalContext.current

    val selectedCenter by centerViewModel.selectedCenter.collectAsState()

    val selectedChildClass by childClassViewModel.selectedChildClass.collectAsState()
    val allChildClasses by childClassViewModel.allChildClasses.collectAsState()

    val (deleteChildClassDialog, setDeleteChildClassDialog) = rememberSaveable {
        mutableStateOf(false)
    }

    val (updateChildClassDialog, setUpdateChildClassDialog) = rememberSaveable {
        mutableStateOf(false)
    }

    val (addChildClassDialog, setAddChildClassDialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if (addChildClassDialog) {
        AddChildClassDialog(
            context = context,
            selectedCenter = selectedCenter,
            childClassViewModel = childClassViewModel,
            setAddChildClassDialog = { setAddChildClassDialog(it) },
            selectedChildClass = selectedChildClass,
            isUpdate = false
        )
    }
    if (updateChildClassDialog) {
        AddChildClassDialog(
            context = context,
            selectedCenter = selectedCenter,
            childClassViewModel = childClassViewModel,
            setAddChildClassDialog = { setUpdateChildClassDialog(it) },
            selectedChildClass = selectedChildClass,
            isUpdate = true
        )
    }

    if (deleteChildClassDialog){
        selectedChildClass?.let { selectedChildClass ->
            AlertDialog(
                title = {Text(text = "Class : ${selectedChildClass.name} 삭제하시겠습니까?")},
                onDismissRequest = {setDeleteChildClassDialog(false)},
                confirmButton = { TextButton(onClick = {
                    childClassViewModel.deleteChildClass(selectedChildClass = selectedChildClass)
                    setDeleteChildClassDialog(false)
                }) {
                    Text(text = "삭제")
                }
                },
                dismissButton = { TextButton(onClick = { setDeleteChildClassDialog(false) }) {
                    Text(text = "닫기")
                }
                }
            )
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Class",
            style = TextStyle(
                fontSize = 33.sp,
                fontFamily = fontFamily_Inter,
                fontWeight = FontWeight(500),
                color = Color.Black
            ),
            modifier = Modifier.weight(1f)
        )
        LazyRow(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ){
            allChildClasses?.let { allChildClasses ->
                items(allChildClasses){childClass ->
                    Button(
                        onClick = {
                            childClassViewModel.setSelectedChildClass(childClass)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedChildClass == childClass) Color(0xFFE0E9F5) else Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(width = 0.5.dp, color = Color(0xFF0047B3).copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = childClass.name,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(100.dp)
        ) {
            if (selectedCenter.isNotNull()){
                Button(
                    onClick = {
                        setAddChildClassDialog(true)
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
            if (selectedChildClass.isNotNull()){
                Button(
                    onClick = {
                        setUpdateChildClassDialog(true)
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
                        setDeleteChildClassDialog(true)
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