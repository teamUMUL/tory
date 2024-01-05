package inu.thebite.tory.screens.education.compose.dialog.sto

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import inu.thebite.tory.model.sto.AddStoRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoRequest
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Poppins


@SuppressLint("MutableCollectionMutableState")
@Composable
fun UpdateSTODialog(
    context : Context,
    stoViewModel: STOViewModel,
    selectedSTO: StoResponse,
    setUpdateSTOItem : (Boolean) -> Unit,
) {
    val addSTOScrollState = rememberScrollState()

    var stoNameInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedSTO.name))
    }
    var stoDetailInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedSTO.contents))
    }
    var stoTryNumInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedSTO.count.toString()))
    }
    var stoSuccessStandardInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedSTO.goal.toString()))
    }
    var stoMethodInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedSTO.urgeContent))

    }
    var stoScheduleInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedSTO.enforceContent))

    }
    var stoMemoInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedSTO.memo))

    }

    val stoTryNum = remember { mutableStateOf(selectedSTO.count) }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {setUpdateSTOItem(false)},
    ){
        Column(
            modifier = Modifier
                .width(900.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp))
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
                    text = "STO 수정",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),

                        textAlign = TextAlign.Center,
                    )
                )
                updateSTOTextFieldFrame("STO 이름", setInputValue = {stoNameInputValue = it}, inputValue = stoNameInputValue, isSingleLine = false)
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame("STO 내용", setInputValue = {stoDetailInputValue = it}, inputValue = stoDetailInputValue, isSingleLine = false)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "시도 수",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
                roundedSelectButtons(
                    buttonList = listOf(10, 15, 20),
                    cornerRadius = 5.dp,
                    colorList = listOf(),
                    defaultButtonIndex = listOf(10,15,20).indexOf(selectedSTO.count),
                    stoTryNum = stoTryNum
                )
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame("준거도달 기준", setInputValue = {stoSuccessStandardInputValue = it}, inputValue = stoSuccessStandardInputValue, isSingleLine = false)
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame("촉구방법", setInputValue = {stoMethodInputValue = it}, inputValue = stoMethodInputValue, isSingleLine = false)
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame("강화스케줄", setInputValue = {stoScheduleInputValue = it}, inputValue = stoScheduleInputValue, isSingleLine = false)
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame("메모", setInputValue = {stoMemoInputValue = it}, inputValue = stoMemoInputValue, isSingleLine = false)
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
                    onClick = { setUpdateSTOItem(false) }
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
                        if(stoNameInputValue.text.isNotEmpty()){
                            //업데이트 코드
                            stoViewModel.updateSTO(
                                selectedSTO = selectedSTO,
                                updateSTO = UpdateStoRequest(
                                    name = stoNameInputValue.text,
                                    contents = stoDetailInputValue.text,
                                    count = stoTryNum.value,
                                    goal = stoSuccessStandardInputValue.text.toInt(),
                                    urgeType = "",
                                    urgeContent = stoMethodInputValue.text,
                                    enforceContent = stoScheduleInputValue.text,
                                    memo = stoMemoInputValue.text
                                )
                            )
                            //----
                            setUpdateSTOItem(false)
                            stoNameInputValue = TextFieldValue("")
                            stoDetailInputValue = TextFieldValue("")
                            stoTryNumInputValue = TextFieldValue("")
                            stoSuccessStandardInputValue = TextFieldValue("")
                            stoMethodInputValue = TextFieldValue("")
                            stoScheduleInputValue = TextFieldValue("")
                            stoMemoInputValue = TextFieldValue("")
                        }
                    },
                ){
                    Text(
                        text = "수정",
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
fun updateSTOTextFieldFrame(typeOfInput : String, inputValue: TextFieldValue, setInputValue:(TextFieldValue) -> Unit, isSingleLine: Boolean, isReadOnly : Boolean = false){
    val containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f)
    val containerColor1 = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f)
    TextField(
        value = inputValue,
        onValueChange = {
            setInputValue(it)
        },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        label = { Text(text = typeOfInput) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
        ),
        textStyle = TextStyle(
            color = Color.Black, fontSize = 22.sp,
            fontFamily = FontFamily.SansSerif
        ),
        maxLines = 2,
        singleLine = isSingleLine,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
    )
}