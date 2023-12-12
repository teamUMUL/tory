package inu.thebite.tory.screens.education.compose.dialog.lto

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import es.dmoral.toasty.Toasty
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoRequest
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel

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
    var gameMode by remember {
        mutableStateOf("교육 선택 안함")
    }
    // AddLTOItemDialog 내용
    Dialog(
        onDismissRequest = {setAddLTOItem(false)},
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White)
                .padding(
                    horizontal = 8.dp,
                    vertical = 16.dp
                )
        ){

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
            Spacer(modifier = Modifier.height(10.dp))
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth(),
                expanded = isExpanded,
                onExpandedChange = {isExpanded = !isExpanded}
            ){
                TextField(

                    value = gameMode,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                        unfocusedContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                        disabledContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = {isExpanded = false},
                    modifier = Modifier
                        .exposedDropdownSize(),
                ){
                    DropdownMenuItem(
                        text = {
                            Text(text = "교육 선택 안함")
                        },
                        onClick = {
                            gameMode = "교육 선택 안함"
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "같은 사진 매칭")
                        },
                        onClick = {
                            gameMode = "같은 사진 매칭"
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "일반화 매칭")
                        },
                        onClick = {
                            gameMode = "일반화 매칭"
                            isExpanded = false
                        }
                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
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
                                game = gameMode
                            ),
                            studentId = selectedChild.id
                        )
                    } else{
                        Toasty.warning(context, "LTO의 이름을 입력해주세요", Toast.LENGTH_SHORT, true).show()
                    }
                    setAddLTOItem(false)
                    ltoInputValue = TextFieldValue("")
                    gameMode = ""
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = null, Modifier.size(40.dp))
            }
        }
    }
}