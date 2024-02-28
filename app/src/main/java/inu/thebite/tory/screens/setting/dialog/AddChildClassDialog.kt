package inu.thebite.tory.screens.setting.dialog

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import inu.thebite.tory.model.center.CenterRequest
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassRequest
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.screens.setting.viewmodel.ChildClassViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildInfoViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChildClassDialog(
    context : Context,
    selectedCenter : CenterResponse?,
    childClassViewModel : ChildClassViewModel,
    setAddChildClassDialog : (Boolean) -> Unit,
    isUpdate : Boolean,
    selectedChildClass : ChildClassResponse?,
    onFinish : () -> Unit
){
    val defaultChildClassValue = if(isUpdate) selectedChildClass!!.name else ""

    var childClassNameInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(defaultChildClassValue))
    }

    Dialog(
        onDismissRequest = {setAddChildClassDialog(false)},
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
                text = if (isUpdate) "반 수정" else "반 추가",
                style = TextStyle(
                    fontSize = 33.sp,
                    fontFamily = fontFamily_Inter,
                    fontWeight = FontWeight(400),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                ),
            )
            TextField(
                value = childClassNameInputValue,
                onValueChange = {
                    childClassNameInputValue = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RoundedCornerShape(8.dp),
                label = { Text(text = "반 이름") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                ),
                textStyle = TextStyle(
                    color = Color.Black, fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif
                ),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )
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
                        setAddChildClassDialog(false)
                        childClassNameInputValue = TextFieldValue("")
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
                        selectedCenter?.let { selectedCenter ->
                            if(childClassNameInputValue.text.isNotEmpty()){
                                if(isUpdate){
                                    selectedChildClass?.let {selectedChildClass ->
                                        childClassViewModel.updateChildClass(
                                            selectedChildClass = selectedChildClass,
                                            updateChildClass = ChildClassRequest(
                                                name = childClassNameInputValue.text
                                            )
                                        )
                                        onFinish()
                                    }
//                                        childClassViewModel.clearSelectedChildClass()
                                } else {
                                    childClassViewModel.createChildClass(
                                        selectedCenter = selectedCenter,
                                        newChildClass = ChildClassRequest(
                                            name = childClassNameInputValue.text
                                        )
                                    )
                                    onFinish()
                                }
                                childClassNameInputValue = TextFieldValue("")
                                setAddChildClassDialog(false)
                            } else {
                                Toasty.warning(context, "반의 이름을 입력해주세요", Toast.LENGTH_SHORT, true).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0047B3))
                ){
                    Text(
                        text = if (isUpdate) "수정" else "추가",
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

