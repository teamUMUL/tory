package inu.thebite.tory.screens.education.compose.dialog.lto

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import es.dmoral.toasty.Toasty
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.ui.theme.ToryTheme
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato
import inu.thebite.tory.ui.theme.fontFamily_Poppins


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateLTOItemDialog(
    context : Context,
    setUpdateLTOItem: (Boolean) -> Unit,
    selectedLTO : LtoResponse,
    ltoViewModel: LTOViewModel
) {
    var ltoInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(selectedLTO.name))
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f, label = ""
    )

    val (deleteLTODialog, setDeleteLTODialog) = rememberSaveable {
        mutableStateOf(false)
    }
    val ltoMode = remember {
        mutableStateListOf<String>()
    }

    if (deleteLTODialog){
        selectedLTO?.let { selectedLTO ->
            AlertDialog(
                title = {Text(text = "LTO : ${selectedLTO.name} 삭제하시겠습니까?")},
                onDismissRequest = {setDeleteLTODialog(false)},
                confirmButton = { TextButton(onClick = {
                    ltoViewModel.deleteLTO(selectedLTO = selectedLTO)
                    setDeleteLTODialog(false)
                    setUpdateLTOItem(false)
                }) {
                    Text(text = "삭제")
                }
                },
                dismissButton = { TextButton(onClick = { setDeleteLTODialog(false) }) {
                    Text(text = "닫기")
                }
                }
            )
        }
    }
    // AddLTOItemDialog 내용
    Dialog(
        onDismissRequest = {setUpdateLTOItem(false)},
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ){
        Column(
            modifier = Modifier
                .width(900.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(
                    horizontal = 35.dp,
                    vertical = 30.dp
                ),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "LTO 수정",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(400),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    ),
                )
                OutlinedButton(
                    onClick = {
                        setDeleteLTODialog(true)
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

            TextField(
                value = ltoInputValue,
                onValueChange = {
                    ltoInputValue = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RoundedCornerShape(8.dp),
                label = { Text(text = "LTO 이름") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                ),
                textStyle = TextStyle(
                    color = Color.Black, fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif
                ),
                maxLines = 2,
                singleLine = false,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                RadioButton(
                    selected = (ltoMode.contains("언어발달")),
                    onClick = {
                        if (ltoMode.contains("언어발달")){
                            ltoMode.remove("언어발달")
                        } else {
                            ltoMode.add("언어발달")
                        }
                    },
                )
                Text(
                    text = "언어발달",
                    style = TextStyle(
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(400),
                        fontSize = 20.sp,
                        color = Color(0xFF606060)
                    )
                )
                RadioButton(
                    selected = (ltoMode.contains("인지발달")),
                    onClick = {
                        if (ltoMode.contains("인지발달")){
                            ltoMode.remove("인지발달")
                        } else {
                            ltoMode.add("인지발달")
                        }
                    })
                Text(
                    text = "인지발달",
                    style = TextStyle(
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(400),
                        fontSize = 20.sp,
                        color = Color(0xFF606060)
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(80.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        setUpdateLTOItem(false)
                        ltoInputValue = TextFieldValue("")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
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
                        .height(80.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        if(ltoInputValue.text.isNotEmpty()){
                            //LTO 업데이트 코드
                            ltoViewModel.updateLTO(
                                selectedLTO = selectedLTO,
                                updateLTO = LtoRequest(
                                    name = ltoInputValue.text,
                                    contents = selectedLTO.contents,
                                    game = ""
                                )
                            )
                            ltoInputValue = TextFieldValue("")
                            setUpdateLTOItem(false)
                        }
                        else{
                            Toasty.warning(context, "LTO의 이름을 입력해주세요", Toast.LENGTH_SHORT, true).show()
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0047B3))
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

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun updateLTO() {
    ToryTheme {
        Surface(
            modifier = Modifier
                .width(900.dp)
        ) {
            UpdateLTOItemDialog(
                context = LocalContext.current,
                setUpdateLTOItem = {},
                selectedLTO =
                    LtoResponse(
                        id = 1L,
                        templateNum = 1,
                        status = "",
                        name = "Example LTO",
                        contents = "",
                        game = "",
                        achieveDate = "",
                        registerDate = "",
                        delYN = "",
                        domainId = 1L,
                        studentId = 1L
                    ),
                ltoViewModel = LTOViewModel()
            )
        }

    }
}