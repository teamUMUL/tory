package inu.thebite.tory.screens.homescreen

import android.util.Log
import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import inu.thebite.tory.R
import inu.thebite.tory.SegmentedControl

@Composable
fun ChainCard(){
    var isDialogVisible by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(120.dp)
            .padding(8.dp)
            .background(
                color = Color(0xFF59B791),
                shape = RoundedCornerShape(size = 20.dp)
            ),


        ) {


        Column(modifier = Modifier
            .padding(top = 5.dp)
            .width(250.dp)
            .height(92.dp)
            .clickable { isDialogVisible = true }
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),

            ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .height(70.dp)
                    .padding(top = 10.dp, start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Chain",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.size(80.dp))
                Text(
                    text = "2",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center)
                )
                ConfigDialog(
                    showDialog = isDialogVisible,
                    onDismiss = { isDialogVisible = false },
                    onConfirm = { configText ->
                        // Handle dialog confirmation here
                        // configText contains the user input
                        isDialogVisible = false})
            }
        }
    }
}
@Composable
fun ClassCard(){
    var isDialogVisible by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(120.dp)
            .padding(8.dp)
            .background(
                color = Color(0xFF59B791),
                shape = RoundedCornerShape(size = 20.dp)
            ),


        ) {


        Column(modifier = Modifier
            .padding(top = 5.dp)
            .width(250.dp)
            .clickable { isDialogVisible = true }
            .height(92.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),

            ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .height(70.dp)
                    .padding(top = 10.dp, start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Class",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.size(80.dp))
                Text(
                    text = "2",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center)
                )
                ConfigDialog(
                    showDialog = isDialogVisible,
                    onDismiss = { isDialogVisible = false },
                    onConfirm = { configText ->
                        // Handle dialog confirmation here
                        // configText contains the user input
                        isDialogVisible = false})
            }
        }
    }
}
@Composable
fun ChildrenCard(){
    var isDialogVisible by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(120.dp)

            .padding(8.dp)
            .background(
                color = Color(0xFF59B791),
                shape = RoundedCornerShape(size = 20.dp)
            ),


        ) {


        Column(modifier = Modifier
            .padding(top = 5.dp)
            .width(250.dp)
            .height(92.dp)
            .clickable { isDialogVisible = true }
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),

            ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .height(70.dp)
                    .padding(top = 10.dp, start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Children",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.size(40.dp))
                Text(
                    text = "2",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center)
                )

                ConfigDialog(
                    showDialog = isDialogVisible,
                    onDismiss = { isDialogVisible = false },
                    onConfirm = { configText ->
                        // Handle dialog confirmation here
                        // configText contains the user input
                        isDialogVisible = false})
            }
        }
    }
}
@Composable
fun ConfigDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val classList = listOf<String>(
        "오전반(월수금)",
        "오후반(월수금)",
        "오전반(화목)",
        "오후반(화목)"
    )
    val (selectedChildClass, setSelectedChildClass) = rememberSaveable {
        mutableStateOf(classList[0])
    }
    val (selectedChildClassIndex, setSelectedChildClassIndex) = rememberSaveable {
        mutableStateOf(0)
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            content = {
                var configText by remember { mutableStateOf("") }

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
                    SegmentedControl(
                        items = classList,
                        defaultSelectedItemIndex = selectedChildClassIndex,
                        useFixedWidth = true,
                        itemWidth = 140.dp
                    ){
                        Log.e("CustomToggle", "Selected item : ${classList[it]}")
                        setSelectedChildClassIndex(it)
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
                                .weight(1f)
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
                                setSelectedChildClass(classList[selectedChildClassIndex])
                                onConfirm(configText)
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
//@Composable
//fun ConfigDialog(
//    showDialog: Boolean,
//    onDismiss: () -> Unit,
//    onConfirm: (String) -> Unit
//) {
//    if (showDialog) {
//        Dialog(
//            onDismissRequest = { onDismiss() },
//            content = {
//                var configText by remember { mutableStateOf("") }
//
//                Column(
//                    modifier = Modifier
//                        .padding(16.dp)
//                ) {
//                    Text(
//                        text = "Configure the box",
//                        fontSize = 20.sp,
//                        color = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier.padding(bottom = 16.dp)
//                    )
//
//                    BasicTextField(
//                        value = configText,
//                        onValueChange = { configText = it },
//                        textStyle = TextStyle(fontSize = 16.sp),
//                        singleLine = true,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 16.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Button(
//                            onClick = { onDismiss() },
//                            modifier = Modifier
//                                .height(50.dp)
//                                .weight(1f)
//                        ) {
//                            Text(text = "Cancel")
//                        }
//
//                        Spacer(modifier = Modifier.width(16.dp))
//
//                        Button(
//                            onClick = {
//                                onConfirm(configText)
//                                onDismiss()
//                            },
//                            modifier = Modifier
//                                .height(50.dp)
//                                .weight(1f)
//                        ) {
//                            Text(text = "Confirm")
//                        }
//                    }
//                }
//            }
//        )
//    }
//}
