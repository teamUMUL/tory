package inu.thebite.tory.screens.setting.dialog

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import es.dmoral.toasty.Toasty
import inu.thebite.tory.R
import inu.thebite.tory.database.ChildClass.ChildClassEntity
import inu.thebite.tory.database.ChildInfo.ChildInfoEntity
import inu.thebite.tory.screens.setting.viewmodel.ChildInfoViewModel
import java.time.LocalDate

@Composable
fun AddChildInfoDialog(
    context : Context,
    childInfos : List<ChildInfoEntity>?,
    selectedChildClass : ChildClassEntity?,
    selectedChildInfo : ChildInfoEntity?,
    childInfoViewModel: ChildInfoViewModel,
    setAddChildInfoDialog : (Boolean) -> Unit,
    isUpdate : Boolean,

){

    var childInfoNameInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(if(isUpdate) selectedChildInfo!!.childName else ""))
    }
    var childInfoBirthInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(if(isUpdate) selectedChildInfo!!.child_birth else ""))
    }
    var childInfoEtcInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(if(isUpdate) selectedChildInfo!!.child_etc else ""))
    }
    var childInfoParentNameInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(if(isUpdate) selectedChildInfo!!.parent_name else ""))
    }
    var childInfoStartDateInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(if(isUpdate) selectedChildInfo!!.child_startDate else ""))
    }
    var childInfoEndDateInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(if(isUpdate) selectedChildInfo!!.child_endDate else ""))
    }



    Dialog(
        onDismissRequest = {setAddChildInfoDialog(false)}
    ){
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(4.dp, MaterialTheme.colorScheme.primary)
        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                DialogTopBar(
                    dialogName = "아이 정보",
                    closeDialog = {setAddChildInfoDialog(it)}
                )
                Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ){
                    childInfoDialogRow{
                        SettingTextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 10.dp),
                            inputValue = childInfoNameInputValue,
                            setInputValue = {childInfoNameInputValue = it},
                            labelText = "아이 이름",
                            isReadOnly = false,
                            isSingleLine = true
                        )

                        SettingTextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 10.dp),
                            inputValue = childInfoParentNameInputValue,
                            setInputValue = {childInfoParentNameInputValue = it},
                            labelText = "부모 이름",
                            isReadOnly = false,
                            isSingleLine = true
                        )
                        SettingTextField(
                            modifier = Modifier
                                .weight(1f),
                            inputValue = childInfoBirthInputValue,
                            setInputValue = {childInfoBirthInputValue = it},
                            labelText = "아이 생년월일",
                            isReadOnly = true,
                            isSingleLine = true
                        )
                    }

                    childInfoDialogRow{
                        SettingTextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 10.dp),
                            inputValue = childInfoStartDateInputValue,
                            setInputValue = {childInfoStartDateInputValue = it},
                            labelText = "시작 날짜",
                            isReadOnly = true,
                            isSingleLine = true
                        )
                        SettingTextField(
                            modifier = Modifier
                                .weight(1f),
                            inputValue = childInfoEndDateInputValue,
                            setInputValue = {childInfoEndDateInputValue = it},
                            labelText = "종료 날짜",
                            isReadOnly = true,
                            isSingleLine = true
                        )

                    }
                    childInfoDialogRow{
                        SettingTextField(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp),
                            inputValue = childInfoEtcInputValue,
                            setInputValue = {childInfoEtcInputValue = it},
                            labelText = "아이 정보",
                            isReadOnly = false,
                            isSingleLine = false
                        )
                    }
                    selectedChildClass?.let { selectedChildClass ->
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .padding(bottom = 10.dp),
                            onClick = {
                                if(
                                    childInfoNameInputValue.text.isNotEmpty() &&
                                    childInfoNameInputValue.text.isNotEmpty() &&
                                    childInfoNameInputValue.text.isNotEmpty() &&
                                    childInfoNameInputValue.text.isNotEmpty() &&
                                    childInfoNameInputValue.text.isNotEmpty() &&
                                    childInfoNameInputValue.text.isNotEmpty() &&
                                    childInfoNameInputValue.text.isNotEmpty()
                                ){
                                    if (childInfos != null) {
                                        if(!childInfos.any { it.childName ==  selectedChildClass.centerName}){
                                            if(isUpdate){
                                                selectedChildInfo?.let {selectedChildInfo ->
                                                    childInfoViewModel.updateChildInfo(
                                                        selectedChildInfo.copy(
                                                            childName = childInfoNameInputValue.text,
                                                            parent_name = childInfoParentNameInputValue.text,
                                                            child_birth = childInfoBirthInputValue.text,
                                                            child_startDate = childInfoStartDateInputValue.text,
                                                            child_endDate = childInfoEndDateInputValue.text,
                                                            child_etc = childInfoEtcInputValue.text
                                                        )
                                                    )
                                                }
                                                childInfoViewModel.clearSelectedChildInfo()
                                            } else {
                                                childInfoViewModel.createChildInfo(
                                                    centerName = selectedChildClass.centerName,
                                                    childClassName = selectedChildClass.className,
                                                    childName = childInfoNameInputValue.text,
                                                    parentName = childInfoParentNameInputValue.text,
                                                    childBirth = childInfoBirthInputValue.text,
                                                    childEtc = childInfoEtcInputValue.text,
                                                    startDate = childInfoStartDateInputValue.text,
                                                    endDate = childInfoEndDateInputValue.text,
                                                    registerDate = LocalDate.now().toString(),
                                                )

                                            }
                                            childInfoNameInputValue = TextFieldValue("")
                                            setAddChildInfoDialog(false)
                                        } else {
                                            if (selectedChildInfo != null) {
                                                if(childInfoNameInputValue.text == selectedChildInfo.childName){
                                                    if(isUpdate){
                                                        selectedChildInfo.let { selectedChildInfo ->
                                                            childInfoViewModel.updateChildInfo(
                                                                selectedChildInfo.copy(
                                                                    childName = childInfoNameInputValue.text,
                                                                    parent_name = childInfoParentNameInputValue.text,
                                                                    child_birth = childInfoBirthInputValue.text,
                                                                    child_startDate = childInfoStartDateInputValue.text,
                                                                    child_endDate = childInfoEndDateInputValue.text,
                                                                    child_etc = childInfoEtcInputValue.text
                                                                )
                                                            )
                                                        }
                                                        childInfoViewModel.clearSelectedChildInfo()
                                                    } else {
                                                        childInfoViewModel.createChildInfo(
                                                            centerName = selectedChildClass.centerName,
                                                            childClassName = selectedChildClass.className,
                                                            childName = childInfoNameInputValue.text,
                                                            parentName = childInfoParentNameInputValue.text,
                                                            childBirth = childInfoBirthInputValue.text,
                                                            childEtc = childInfoEtcInputValue.text,
                                                            startDate = childInfoStartDateInputValue.text,
                                                            endDate = childInfoEndDateInputValue.text,
                                                            registerDate = LocalDate.now().toString(),
                                                        )

                                                    }
                                                    childInfoNameInputValue = TextFieldValue("")
                                                    setAddChildInfoDialog(false)
                                                }
                                            } else {
                                                Toasty.warning(context, "이미 동일한 이름의 아이가 존재합니다", Toast.LENGTH_SHORT, true).show()
                                            }
                                        }
                                    } else{
                                        Toasty.warning(context, "선택된 반이 없습니다", Toast.LENGTH_SHORT, true).show()
                                    }
                                } else {
                                    Toasty.warning(context, "아이 정보를 제외한 모든 내용을 입력해주세요", Toast.LENGTH_SHORT, true).show()
                                }


                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ){
                            Icon(imageVector = Icons.Default.Add, contentDescription = null, Modifier.size(40.dp))
                        }
                    }
                }

            }
        }
    }
}
@Composable
fun childInfoDialogRow(content: @Composable RowScope.() -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTextField(
    modifier : Modifier,
    inputValue: TextFieldValue,
    setInputValue: (TextFieldValue) -> Unit,
    labelText : String,
    isReadOnly : Boolean,
    isSingleLine : Boolean
){
    val calendarState = rememberSheetState()
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
        ),
        selection = CalendarSelection.Date{ date ->
            Log.e("선택한 날짜", date.toString())
            setInputValue(TextFieldValue(date.toString()))
        }
    )
    TextField(
        value = inputValue,
        onValueChange = {
            setInputValue(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .border(
                2.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                if (isReadOnly) {
                    calendarState.show()
                }
            },
        shape = RoundedCornerShape(8.dp),
        label = { Text(text = labelText) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = if(labelText == "아이 정보") ImeAction.Default else ImeAction.Done
        ),
        textStyle = TextStyle(
            color = Color.Black, fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif
        ),
        readOnly = isReadOnly,
        singleLine = isSingleLine,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        trailingIcon = {
            if(isReadOnly) {
                IconButton(
                    onClick = {
                        calendarState.show()
                    }
                ) {
                    Icon(painter = painterResource(id = R.drawable.icon_calendar), contentDescription = null)
                }
            }
        }
    )
}