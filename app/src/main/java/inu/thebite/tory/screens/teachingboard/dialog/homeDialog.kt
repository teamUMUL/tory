package inu.thebite.tory.screens.teachingboard.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import inu.thebite.tory.CenterSelector
import inu.thebite.tory.screens.teachingboard.viewmodel.CenterSelectViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildClassSelectViewModel
import inu.thebite.tory.ChildSelector
import inu.thebite.tory.ClassSelector
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildSelectViewModel

@Composable
fun CenterDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
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
            properties = DialogProperties(usePlatformDefaultWidth = false),
            content = {
                var chainText by remember { mutableStateOf("") }

                Column(modifier = Modifier
                    .width(800.dp)
//                    .height(240.dp)
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
                    CenterSelector(items = allCenters, onDismiss = {onDismiss()}, centerSelectViewModel = centerSelectViewModel, childClassSelectViewModel = childClassSelectViewModel, childSelectViewModel = childSelectViewModel)
                }
            }
        )
    }
}
@Composable
fun ClassDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    childClassSelectViewModel: ChildClassSelectViewModel,
    childSelectViewModel: ChildSelectViewModel
) {
    val allChildClasses by childClassSelectViewModel.allChildClasses.collectAsState()
//    val childClasses by childClassSelectViewModel.childClasses.collectAsState()
    val selectedChildClass by childClassSelectViewModel.selectedChildClass.collectAsState()
    val _selectedChildClass by childClassSelectViewModel.tempSelectedChildClass.collectAsState()



    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            content = {
                var classText by remember { mutableStateOf("") }

                Column(modifier = Modifier
                    .width(800.dp)
//                    .height(240.dp)
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
                    allChildClasses?.let {childClasses ->
                        ClassSelector(
                            items = childClasses,
                            onDismiss = { onDismiss() },
                            childClassSelectViewModel = childClassSelectViewModel,
                            childSelectViewModel = childSelectViewModel
                        )
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
//    val allChildInfos by childSelectViewModel.allChildInfos.collectAsState()
    val childInfos by childSelectViewModel.childInfos.collectAsState()
    val selectedChildInfo by childSelectViewModel.selectedChildInfo.collectAsState()
    val _selectedChildInfo by childSelectViewModel.tempSelectedChildInfo.collectAsState()

    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            content = {


                Column(modifier = Modifier
                    .width(800.dp)
//                    .height(240.dp)
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
                        ChildSelector(
                            items = childInfos,
                            onDismiss = { onDismiss() },
                            childSelectViewModel = childSelectViewModel
                        )
                    }
                }
            }
        )
    }
}
//
//@Composable
//fun SegmentedControl(
//    items: List<String>,
//    defaultSelectedItemIndex: Int = 0,
//    useFixedWidth: Boolean = false,
//    itemWidth: Dp = 120.dp,
//    cornerRadius: Int = 10,
//    @ColorRes color: Int = R.color.light_gray,
//    onItemSelection: (selectedItemIndex: Int) -> Unit
//){
//    var selectedIndex by rememberSaveable {
//        mutableStateOf(defaultSelectedItemIndex)
//    }
//
//    Row(
//        modifier = Modifier
//    ) {
//        items.forEachIndexed{ index, item ->
//            OutlinedButton(
//                modifier = when(index){
//                    0 -> {
//                        if (useFixedWidth) {
//                            Modifier
//                                .width(itemWidth)
//                                .offset(0.dp, 0.dp)
//                                .zIndex(if (selectedIndex == index) 1f else 0f)
//                        } else {
//                            Modifier
//                                .wrapContentSize()
//                                .offset(0.dp, 0.dp)
//                                .zIndex(if (selectedIndex == index) 1f else 0f)
//                        }
//                    }
//                    else -> {
//                        if(useFixedWidth){
//                            Modifier
//                                .width(itemWidth)
//                                .offset((-1 * index).dp, 0.dp)
//                                .zIndex(if (selectedIndex == index) 1f else 0f)
//                        } else {
//                            Modifier
//                                .wrapContentSize()
//                                .offset((-1 * index).dp, 0.dp)
//                                .zIndex(if (selectedIndex == index) 1f else 0f)
//                        }
//                    }
//                },
//                onClick = {
//                    selectedIndex = index
//                    onItemSelection(selectedIndex)
//                },
//                shape = when(index) {
//                    //왼쪽 바깥
//                    0 -> RoundedCornerShape(
//                        topStartPercent = cornerRadius,
//                        topEndPercent = 0,
//                        bottomStartPercent = cornerRadius,
//                        bottomEndPercent = 0
//                    )
//                    //오른쪽 끝
//                    items.size - 1 -> RoundedCornerShape(
//                        topStartPercent = 0,
//                        topEndPercent = cornerRadius,
//                        bottomStartPercent = 0,
//                        bottomEndPercent = cornerRadius
//                    )
//                    //가운데 버튼들
//                    else -> RoundedCornerShape(
//                        topStartPercent = 0,
//                        topEndPercent = 0,
//                        bottomStartPercent = 0,
//                        bottomEndPercent = 0
//                    )
//                },
//                border = BorderStroke(
//                    1.dp, if(selectedIndex == index) {
//                        colorResource(id = color)
//                    } else {
//                        colorResource(id = color).copy(alpha = 0.75f)
//                    }
//                ),
//                colors = if (selectedIndex == index){
//                    ButtonDefaults.outlinedButtonColors(
//                        //선택된 경우 색
//                        containerColor = colorResource(id = color)
//                    )
//                } else {
//                    ButtonDefaults.outlinedButtonColors(
//                        //선택 안된 경우 색
//                        containerColor = Color.Transparent
//                    )
//                },
//            ) {
//                Text(
//                    text = item,
//                    fontWeight = FontWeight.Normal,
//                    color = if (selectedIndex == index){
//                        Color.White
//                    } else {
//                        colorResource(id = color).copy(alpha = 0.9f)
//                    }
//                )
//            }
//
//        }
//    }
//}