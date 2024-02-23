package inu.thebite.tory.screens.education.compose.dialog.lto

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.ui.theme.ToryTheme
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLTODialog(
    context : Context,
    selectedChild : StudentResponse,
    setAddLTOItem: (Boolean) -> Unit,
    selectedDEV : DomainResponse,
    ltoViewModel: LTOViewModel
) {
    var ltoInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f, label = ""
    )

    val ltoMode = remember {
        mutableStateListOf<String>()
    }
//    var gameMode by remember {
//        mutableStateOf("교육 선택 안함")
//    }
    // AddLTOItemDialog 내용
    Dialog(
        onDismissRequest = {setAddLTOItem(false)},
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
            Text(
                text = "LTO 추가",
                style = TextStyle(
                    fontSize = 33.sp,
                    fontFamily = fontFamily_Inter,
                    fontWeight = FontWeight(400),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                ),
            )
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
//            ExposedDropdownMenuBox(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(80.dp),
//                expanded = isExpanded,
//                onExpandedChange = {isExpanded = !isExpanded}
//            ){
//                TextField(
//                    value = gameMode,
//                    onValueChange = {},
//                    readOnly = true,
//                    trailingIcon = {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowDropDown,
//                            contentDescription = null,
//                            modifier = Modifier
//                                .size(60.dp)
//                                .rotate(rotationState)
//                        )
//                    },
//                    colors = ExposedDropdownMenuDefaults.textFieldColors(
//                        focusedContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
//                        unfocusedContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
//                        disabledContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent,
//                        disabledIndicatorColor = Color.Transparent,
//                    ),
//                    textStyle = TextStyle(
//                        color = Color.Black, fontSize = 30.sp,
//                        fontFamily = FontFamily.SansSerif
//                    ),
//                    modifier = Modifier
//                        .menuAnchor()
//                        .fillMaxWidth()
//                        .height(80.dp)
//                )
//                ExposedDropdownMenu(
//                    expanded = isExpanded,
//                    onDismissRequest = {isExpanded = false},
//                    modifier = Modifier
//                        .exposedDropdownSize(),
//                ){
//                    DropdownMenuItem(
//                        text = {
//                            Text(text = "교육 선택 안함")
//                        },
//                        onClick = {
//                            gameMode = "교육 선택 안함"
//                            isExpanded = false
//                        }
//                    )
//                    DropdownMenuItem(
//                        text = {
//                            Text(text = "같은 사진 매칭")
//                        },
//                        onClick = {
//                            gameMode = "같은 사진 매칭"
//                            isExpanded = false
//                        }
//                    )
//                    DropdownMenuItem(
//                        text = {
//                            Text(text = "일반화 매칭")
//                        },
//                        onClick = {
//                            gameMode = "일반화 매칭"
//                            isExpanded = false
//                        }
//                    )
//                }
//            }
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
                        setAddLTOItem(false)
                        ltoInputValue = TextFieldValue("")
//                        gameMode = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
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
                        .height(80.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        if(ltoInputValue.text.isNotEmpty()){
                            //LTO 추가 코드
                            ltoViewModel.addLTO(
                                selectedDEV = selectedDEV,
                                newLTO = LtoRequest(
                                    name = ltoInputValue.text,
                                    contents = "",
                                    game = "",
                                    developType = ltoMode
                                ),
                                studentId = selectedChild.id
                            )
                        } else{
                            Toasty.warning(context, "LTO의 이름을 입력해주세요", Toast.LENGTH_SHORT, true).show()
                        }
                        setAddLTOItem(false)
                        ltoInputValue = TextFieldValue("")
//                        gameMode = ""
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
fun ArticleCardPreview() {
    ToryTheme {
        Surface(
            modifier = Modifier
                .width(900.dp)
        ) {
            AddLTODialog(
                context = LocalContext.current,
                selectedChild = StudentResponse(
                    id = 1L,
                    name = "김토리",
                    birth = "2023/11/01",
                    etc = "",
                    parentName = "김토리부모",
                    startDate = "2023/11/01",
                    endDate = "2023/11/31",
                    registerDate = "2023/11/01",
                    childClass = ChildClassResponse(
                        id = 1L,
                        name = "오전반",
                        center = CenterResponse(
                            id = 1L,
                            name = "송도점"
                        )
                    )
                ),
                setAddLTOItem = {},
                selectedDEV = DomainResponse(
                    id = 1L,
                    templateNum = 1,
                    name = "Example DEV",
                    registerDate = "",
                    centerId = 1L
                ),
                ltoViewModel = LTOViewModel()
            )
        }

    }
}

