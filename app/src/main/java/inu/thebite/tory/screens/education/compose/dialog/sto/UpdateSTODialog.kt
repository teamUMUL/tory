package inu.thebite.tory.screens.education.compose.dialog.sto

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import es.dmoral.toasty.Toasty
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoRequest
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato
import inu.thebite.tory.ui.theme.fontFamily_Poppins


@OptIn(ExperimentalMaterial3Api::class)
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
        mutableStateOf(TextFieldValue(""))
    }
    var stoSuccessStandardValueInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedSTO.goal.toString()))
    }
    var stoMethodInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedSTO.urgeContent))

    }
    var stoReinforceMessageInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedSTO.enforceContent))
    }

    val stoTryNum = remember { mutableStateOf(selectedSTO.count) }

    val (deleteSTODialog, setDeleteSTODialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if (deleteSTODialog){
        selectedSTO?.let { selectedSTO ->
            AlertDialog(
                title = {Text(text = "STO : ${selectedSTO.name} 삭제하시겠습니까?")},
                onDismissRequest = {setDeleteSTODialog(false)},
                confirmButton = { TextButton(onClick = {
                    stoViewModel.deleteSTO(selectedSTO = selectedSTO)
                    setDeleteSTODialog(false)
                    setUpdateSTOItem(false)
                }) {
                    Text(text = "삭제")
                }
                },
                dismissButton = { TextButton(onClick = { setDeleteSTODialog(false) }) {
                    Text(text = "닫기")
                }
                }
            )
        }
    }

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                    OutlinedButton(
                        onClick = {
                            setDeleteSTODialog(true)
                        },
                        border = BorderStroke(width = 1.dp, color = Color.Red),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "삭제",
                            style = TextStyle(
                                fontFamily = fontFamily_Lato,
                                fontSize = 20.sp,
                                color = Color.Red
                            )
                        )
                    }
                }

                updateSTOTextFieldFrame("STO 이름", setInputValue = {stoNameInputValue = it}, inputValue = stoNameInputValue, isSingleLine = false)
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame("STO 내용", setInputValue = {stoDetailInputValue = it}, inputValue = stoDetailInputValue, isSingleLine = false)
                Spacer(modifier = Modifier.height(10.dp))
                STOCountSelector(stoCount = stoTryNum.value, setSTOCount = {stoTryNum.value = it})
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "준거도달 방식을 선택해주세요.",
                        style = TextStyle(
                            color = Color(0xFF606060),
                            fontFamily = fontFamily_Inter,
                            fontWeight = FontWeight(400),
                            fontSize = 20.sp
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        RadioButton(selected = (stoSuccessStandardInputValue.text == "퍼센트"), onClick = { stoSuccessStandardInputValue = TextFieldValue("퍼센트") })
                        Text(
                            text = "퍼센트",
                            style = TextStyle(
                                color = Color(0xFF606060),
                                fontFamily = fontFamily_Inter,
                                fontWeight = FontWeight(400),
                                fontSize = 20.sp
                            )
                        )
                        RadioButton(selected = (stoSuccessStandardInputValue.text == "연속"), onClick = { stoSuccessStandardInputValue = TextFieldValue("연속") })
                        Text(
                            text = "연속",
                            style = TextStyle(
                                color = Color(0xFF606060),
                                fontFamily = fontFamily_Inter,
                                fontWeight = FontWeight(400),
                                fontSize = 20.sp
                            )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .width(150.dp)
                            .fillMaxHeight()
                            .background(
                                color = Color(0xFFDFE3E7),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (stoSuccessStandardInputValue.text == "퍼센트") {
                            TextField(
                                value = stoSuccessStandardValueInputValue,
                                onValueChange = {
                                    stoSuccessStandardValueInputValue = it
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(80.dp),
                                shape = RoundedCornerShape(8.dp),
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.None,
                                    autoCorrect = true, keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                                ),
                                textStyle = TextStyle(
                                    color = Color.Black, fontSize = 30.sp,
                                    fontFamily = FontFamily.SansSerif
                                ),
                                maxLines = 1,
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color(0xFFDFE3E7),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                ),
                            )
                            Text(
                                text = "%",
                                style = TextStyle(
                                    color = Color(0xFF606060),
                                    fontFamily = fontFamily_Inter,
                                    fontWeight = FontWeight(400),
                                    fontSize = 20.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        } else if (stoSuccessStandardInputValue.text == "연속") {
                            TextField(
                                value = stoSuccessStandardValueInputValue,
                                onValueChange = {
                                    stoSuccessStandardValueInputValue = it
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(80.dp),
                                shape = RoundedCornerShape(8.dp),
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.None,
                                    autoCorrect = true, keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                                ),
                                textStyle = TextStyle(
                                    color = Color.Black, fontSize = 30.sp,
                                    fontFamily = FontFamily.SansSerif
                                ),
                                maxLines = 1,
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color(0xFFDFE3E7),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                ),
                            )
                            Text(
                                text = "회",
                                style = TextStyle(
                                    color = Color(0xFF606060),
                                    fontFamily = fontFamily_Inter,
                                    fontWeight = FontWeight(400),
                                    fontSize = 20.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame("준거 도달 기준", setInputValue = {stoSuccessStandardValueInputValue = it}, inputValue = stoSuccessStandardValueInputValue, isSingleLine = false, isInt = true)
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame("촉구 방법", setInputValue = {stoMethodInputValue = it}, inputValue = stoMethodInputValue, isSingleLine = false)
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame("강화 메세지", setInputValue = {stoReinforceMessageInputValue = it}, inputValue = stoReinforceMessageInputValue, isSingleLine = false)
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
                        val successStandard = stoSuccessStandardValueInputValue.text.toIntOrNull()
                        successStandard?.let { successStandard ->
                            if(stoNameInputValue.text.isNotEmpty()){
                                //업데이트 코드
                                stoViewModel.updateSTO(
                                    selectedSTO = selectedSTO,
                                    updateSTO = UpdateStoRequest(
                                        name = stoNameInputValue.text,
                                        contents = stoDetailInputValue.text,
                                        count = stoTryNum.value,
                                        goal = successStandard,
                                        urgeType = "",
                                        urgeContent = stoMethodInputValue.text,
                                        enforceContent = stoReinforceMessageInputValue.text,
                                        memo = ""
                                    )
                                )
                                //----
                                setUpdateSTOItem(false)
                                stoNameInputValue = TextFieldValue("")
                                stoDetailInputValue = TextFieldValue("")
                                stoTryNumInputValue = TextFieldValue("")
                                stoSuccessStandardValueInputValue = TextFieldValue("")
                                stoSuccessStandardInputValue = TextFieldValue("")
                                stoMethodInputValue = TextFieldValue("")
                                stoReinforceMessageInputValue = TextFieldValue("")
                            } else {
                                Toasty.warning(context, "STO의 이름을 입력해주세요", Toast.LENGTH_SHORT, true).show()
                            }
                        } ?: run {
                            Toasty.warning(context, "준거 도달 기준은 숫자로 입력해주세요..", Toast.LENGTH_SHORT, true).show()
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
fun updateSTOTextFieldFrame(typeOfInput : String, inputValue: TextFieldValue, setInputValue:(TextFieldValue) -> Unit, isSingleLine: Boolean, isReadOnly : Boolean = false, isInt : Boolean = false){
    val containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f)
    val containerColor1 = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f)
    TextField(
        value = inputValue,
        onValueChange = {
            setInputValue(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        label = { Text(text = typeOfInput) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true, keyboardType = if(isInt) KeyboardType.Number else KeyboardType.Text, imeAction = ImeAction.Done
        ),
        textStyle = TextStyle(
            color = Color.Black, fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif
        ),
        maxLines = if (isSingleLine) 2 else 100,
        singleLine = isSingleLine,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFDFE3E7),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
    )
}