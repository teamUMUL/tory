package inu.thebite.tory.screens.education.compose.dialog.sto

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import es.dmoral.toasty.Toasty
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Poppins

@Composable
fun AddSTODialog(
    context : Context,
    setAddSTOItem: (Boolean) -> Unit,
    stoViewModel: STOViewModel,
    selectedLTO : LtoResponse,
) {
    val addSTOScrollState = rememberScrollState()

    var stoNameInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var stoContentInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
//    var stoTryNumInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
//        mutableStateOf(TextFieldValue("15"))
//    }
    var stoSuccessStandardInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("90"))
    }
    var stoMethodInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var stoScheduleInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var stoMemoInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    val stoTryNum = remember { mutableStateOf(15) }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {setAddSTOItem(false)},
    ){
        Column(
            modifier = Modifier
                .width(900.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(
                    horizontal = 50.dp,
                    vertical = 16.dp
                )
        ){
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .verticalScroll(addSTOScrollState)) {
                Text(
                    text = "STO 추가",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),

                        textAlign = TextAlign.Center,
                    )
                )
                stoTextFieldFrame("STO 이름", stoNameInputValue, setInputValue = {stoNameInputValue = it}, isSingleLine = true)
                Spacer(modifier = Modifier.height(10.dp))
                stoTextFieldFrame("STO 내용", stoContentInputValue, setInputValue = {stoContentInputValue = it}, isSingleLine = true)
                Spacer(modifier = Modifier.height(10.dp))
//                stoTextFieldFrame("시도 수", stoTryNumInputValue, setInputValue = {stoTryNumInputValue = it})
//                Text(
//                    text = "시도 수",
//                    fontSize = 20.sp,
//                    modifier = Modifier
//                        .padding(start = 10.dp)
//                )
                STOCountSelector(stoCount = stoTryNum.value, setSTOCount = {stoTryNum.value = it})
//                roundedSelectButtons(
//                    buttonList = listOf(10, 15, 20),
//                    cornerRadius = 5.dp,
//                    colorList = listOf(),
//                    defaultButtonIndex = 1,
//                    stoTryNum = stoTryNum
//                )
                Spacer(modifier = Modifier.height(10.dp))
                stoTextFieldFrame("준거도달 기준", stoSuccessStandardInputValue, setInputValue = {stoSuccessStandardInputValue = it}, isSingleLine = true)
                Spacer(modifier = Modifier.height(10.dp))
                stoTextFieldFrame("촉구방법", stoMethodInputValue, setInputValue = {stoMethodInputValue = it}, isSingleLine = true)
                Spacer(modifier = Modifier.height(10.dp))
                stoTextFieldFrame("강화스케줄", stoScheduleInputValue, setInputValue = {stoScheduleInputValue = it}, isSingleLine = true)
                Spacer(modifier = Modifier.height(10.dp))
                stoTextFieldFrame("메모", stoMemoInputValue, setInputValue = {stoMemoInputValue = it}, isSingleLine = false)
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 40.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBFBFBF)
                    ),
                    onClick = { setAddSTOItem(false) }
                ){
                    Text(
                        text = "취소",
                        style = TextStyle(
                            fontSize = 26.sp,
                            fontFamily = fontFamily_Poppins,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 40.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0047B3)
                    ),
                    onClick = {
                        val successStandard = stoSuccessStandardInputValue.text.toIntOrNull()
                        successStandard?.let { successStandard ->
                            if(stoNameInputValue.text.isNotEmpty()){
                                stoViewModel.createSTO(
                                    selectedLTO = selectedLTO,
                                    newSTO = AddStoRequest(
                                        name = stoNameInputValue.text,
                                        contents = stoContentInputValue.text,
                                        count = stoTryNum.value,
                                        goal = successStandard,
                                        urgeType = "",
                                        urgeContent = stoMethodInputValue.text,
                                        enforceContent = stoScheduleInputValue.text,
                                        memo = stoMemoInputValue.text,
                                        registrant = "테스트"
                                    )
                                )
                                stoNameInputValue = TextFieldValue("")
                                stoContentInputValue = TextFieldValue("")
                                stoSuccessStandardInputValue = TextFieldValue("")
                                stoMethodInputValue = TextFieldValue("")
                                stoScheduleInputValue = TextFieldValue("")
                                stoMemoInputValue = TextFieldValue("")
                                setAddSTOItem(false)
                            } else {
                                Toasty.warning(context, "STO의 이름을 입력해주세요", Toast.LENGTH_SHORT, true).show()
                            }
                        } ?:
                        Toasty.warning(context, "준거 도달 기준은 숫자로 입력해주세요..", Toast.LENGTH_SHORT, true).show()


                    },
                ){
                    Text(
                        text = "추가",
                        style = TextStyle(
                            fontSize = 26.sp,
                            fontFamily = fontFamily_Poppins,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                            )
                    )
                }

            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun stoTextFieldFrame(typeOfInput : String, inputValue: TextFieldValue, setInputValue:(TextFieldValue) -> Unit, isSingleLine: Boolean){
    TextField(
        value = inputValue,
        onValueChange = {
            setInputValue(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        label = { Text(text = typeOfInput)},
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
        ),
        textStyle = TextStyle(
            color = Color.Black, fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif
        ),
        maxLines = 2,
        singleLine = isSingleLine,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun roundedSelectButtons(
    buttonList: List<Int>,
    cornerRadius: Dp,
    colorList: List<Color>,
    defaultButtonIndex: Int,
    stoTryNum : MutableState<Int>
){
    val (buttonIndex, setButtonIndex) = rememberSaveable {
        mutableStateOf(defaultButtonIndex)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        buttonList.forEachIndexed { index, item ->
            OutlinedButton(
                onClick = {
                    setButtonIndex(buttonList.indexOf(item))
                    stoTryNum.value = item
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
                    buttonList.size - 1 -> RoundedCornerShape(
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
                    if (buttonIndex == index) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(
                        alpha = 0.75f
                    )
                ),
                modifier = when (index) {
                    0 ->
                        Modifier
                            .weight(1f)
                            .offset(0.dp, 0.dp)
                            .zIndex(if (buttonIndex == index) 1f else 0f)

                    else ->
                        Modifier
                            .weight(1f)
                            .offset((-1 * index).dp, 0.dp)
                            .zIndex(if (buttonIndex == index) 1f else 0f)
                },
                colors =
                if (buttonIndex == index) {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color.Black
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                        contentColor = Color.Black
                    )
                }

            ) {
                Text(
                    text = buttonList[index].toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun STOCountSelector(
    stoCount : Int,
    setSTOCount : (Int) -> Unit
){
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f, label = ""
    )
    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        expanded = isExpanded,
        onExpandedChange = {isExpanded = !isExpanded}
    ){
        TextField(
            value = stoCount.toString(),
            onValueChange = {},
            label = { Text(text = "시도 수")},
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .rotate(rotationState)
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                unfocusedContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                disabledContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            textStyle = TextStyle(
                color = Color.Black, fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(80.dp)
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {isExpanded = false},
            modifier = Modifier
                .exposedDropdownSize()
                .background(color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f))
                .fillMaxWidth(),
        ){
            repeat(20){ index ->
                val count = index+1
                DropdownMenuItem(
                    text = {
                        Text(text = count.toString())
                    },
                    onClick = {
                        setSTOCount(count)
                        isExpanded = false
                    }
                )
                if (count != 20){
                    Divider(thickness = 1.dp)
                }
            }
        }
    }
}