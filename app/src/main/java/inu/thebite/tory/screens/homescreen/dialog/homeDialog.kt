package inu.thebite.tory.screens.homescreen.dialog

import android.util.Log
import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import inu.thebite.tory.CenterControl
import inu.thebite.tory.CenterSelectViewModel
import inu.thebite.tory.ChildClassControl
import inu.thebite.tory.ChildClassSelectViewModel
import inu.thebite.tory.ChildInfoControl
import inu.thebite.tory.ChildSelectViewModel
import inu.thebite.tory.R

@Composable
fun CenterDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    centerSelectViewModel: CenterSelectViewModel,
    childClassSelectViewModel: ChildClassSelectViewModel,
    childSelectViewModel: ChildSelectViewModel
) {

    val allCenters by centerSelectViewModel.allCenters.collectAsState()
    val selectedCenter by centerSelectViewModel.selectedCenter.collectAsState()
    val _selectedCenter by centerSelectViewModel.tempSelectedCenter.collectAsState()

    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            content = {
                var chainText by remember { mutableStateOf("") }

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 16.dp
                    )
                ) {

                    Text(
                        text = "지점선택",
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    CenterControl(
                        items = allCenters!!,
                        selectedCenter = _selectedCenter,
                        centerSelectViewModel = centerSelectViewModel,
                        childClassSelectViewModel = childClassSelectViewModel,
                        childSelectViewModel = childSelectViewModel,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { onDismiss() },
                            modifier = Modifier
                                .height(50.dp)
                                .weight(1f),
                            shape = RoundedCornerShape(5.dp),
                        ) {
                            Text(text = "취소")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.light_gray)
                            ),
                            onClick = {
                                _selectedCenter?.let {_selectedCenter ->
                                    centerSelectViewModel.setSelectedCenter(_selectedCenter)
                                }
                                onConfirm(chainText)
//                                viewModel.selectedChildClass = selectedChildClass
                                onDismiss()
                            },

                            ) {
                            Text(text = "선택")
                        }
                    }
                }
            }
        )
    }
}
@Composable
fun ClassDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    childClassSelectViewModel: ChildClassSelectViewModel,
    childSelectViewModel: ChildSelectViewModel
) {
    val allChildClasses by childClassSelectViewModel.allChildClasses.collectAsState()
    val childClasses by childClassSelectViewModel.childClasses.collectAsState()
    val selectedChildClass by childClassSelectViewModel.selectedChildClass.collectAsState()
    val _selectedChildClass by childClassSelectViewModel.tempSelectedChildClass.collectAsState()



    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            content = {
                var classText by remember { mutableStateOf("") }

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 16.dp
                    )
                ) {

                    Text(
                        text = "반선택",
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    childClasses?.let {childClasses ->
                        ChildClassControl(
                            items = childClasses,
                            selectedChildClass = _selectedChildClass,
                            childSelectViewModel = childSelectViewModel,
                            childClassSelectViewModel = childClassSelectViewModel
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { onDismiss() },
                            modifier = Modifier
                                .height(50.dp)
                                .weight(1f),
                            shape = RoundedCornerShape(5.dp),
                        ) {
                            Text(text = "취소")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.light_gray)
                            ),
                            onClick = {
                                onConfirm(classText)
                                _selectedChildClass?.let {_selectedChildClass ->
                                    childClassSelectViewModel.setSelectedChildClass(
                                        _selectedChildClass
                                    )
                                }
//                                viewModel.selectedChildClass = selectedChildClass
                                onDismiss()
                            },

                            ) {
                            Text(text = "선택")
                        }
                    }
                }
            }
        )
    }
}
@Composable
fun ChildDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    childSelectViewModel: ChildSelectViewModel
) {
    val allChildInfos by childSelectViewModel.allChildInfos.collectAsState()
    val childInfos by childSelectViewModel.childInfos.collectAsState()
    val selectedChildInfo by childSelectViewModel.selectedChildInfo.collectAsState()
    val _selectedChildInfo by childSelectViewModel.tempSelectedChildInfo.collectAsState()

    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            content = {


                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 16.dp
                    )
                ) {

                    Text(
                        text = "아이선택",
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    childInfos?.let {childInfos ->
                        ChildInfoControl(
                            items = childInfos,
                            selectedChildInfo = _selectedChildInfo,
                            childSelectViewModel = childSelectViewModel
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { onDismiss() },
                            modifier = Modifier
                                .height(50.dp)
                                .weight(1f),
                            shape = RoundedCornerShape(5.dp),
                        ) {
                            Text(text = "취소")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.light_gray)
                            ),
                            onClick = {
                                _selectedChildInfo?.let {_selectedChildInfo ->
                                    childSelectViewModel.setSelectedChildInfo(
                                        _selectedChildInfo
                                    )
                                }
                                onDismiss()
                            },

                            ) {
                            Text(text = "선택")
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun SegmentedControl(
    items: List<String>,
    defaultSelectedItemIndex: Int = 0,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    cornerRadius: Int = 10,
    @ColorRes color: Int = R.color.light_gray,
    onItemSelection: (selectedItemIndex: Int) -> Unit
){
    var selectedIndex by rememberSaveable {
        mutableStateOf(defaultSelectedItemIndex)
    }

    Row(
        modifier = Modifier
    ) {
        items.forEachIndexed{ index, item ->
            OutlinedButton(
                modifier = when(index){
                    0 -> {
                        if (useFixedWidth) {
                            Modifier
                                .width(itemWidth)
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedIndex == index) 1f else 0f)
                        } else {
                            Modifier
                                .wrapContentSize()
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedIndex == index) 1f else 0f)
                        }
                    }
                    else -> {
                        if(useFixedWidth){
                            Modifier
                                .width(itemWidth)
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedIndex == index) 1f else 0f)
                        } else {
                            Modifier
                                .wrapContentSize()
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedIndex == index) 1f else 0f)
                        }
                    }
                },
                onClick = {
                    selectedIndex = index
                    onItemSelection(selectedIndex)
                },
                shape = when(index) {
                    //왼쪽 바깥
                    0 -> RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = 0,
                        bottomStartPercent = cornerRadius,
                        bottomEndPercent = 0
                    )
                    //오른쪽 끝
                    items.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = 0,
                        bottomEndPercent = cornerRadius
                    )
                    //가운데 버튼들
                    else -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 0,
                        bottomStartPercent = 0,
                        bottomEndPercent = 0
                    )
                },
                border = BorderStroke(
                    1.dp, if(selectedIndex == index) {
                        colorResource(id = color)
                    } else {
                        colorResource(id = color).copy(alpha = 0.75f)
                    }
                ),
                colors = if (selectedIndex == index){
                    ButtonDefaults.outlinedButtonColors(
                        //선택된 경우 색
                        containerColor = colorResource(id = color)
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        //선택 안된 경우 색
                        containerColor = Color.Transparent
                    )
                },
            ) {
                Text(
                    text = item,
                    fontWeight = FontWeight.Normal,
                    color = if (selectedIndex == index){
                        Color.White
                    } else {
                        colorResource(id = color).copy(alpha = 0.9f)
                    }
                )
            }

        }
    }
}