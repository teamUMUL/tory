package inu.thebite.tory.screens.setting.dialog

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import es.dmoral.toasty.Toasty
import inu.thebite.tory.model.center.CenterRequest
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.retrofit.RetrofitApi
import inu.thebite.tory.screens.setting.viewmodel.CenterViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildClassViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildInfoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCenterDialog(
    context : Context,
    centerViewModel: CenterViewModel,
    childClassViewModel : ChildClassViewModel,
    childInfoViewModel : ChildInfoViewModel,
    setAddCenterDialog : (Boolean) -> Unit,
    selectedCenter : CenterResponse?,
    isUpdate : Boolean
){
    val defaultCenterValue = if(isUpdate) selectedCenter!!.name else ""

    var centerNameInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(defaultCenterValue))
    }

    val service = RetrofitApi.apiService

    Dialog(
        onDismissRequest = {setAddCenterDialog(false)}
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
                    dialogName = "센터 정보",
                    closeDialog = {setAddCenterDialog(it)}
                )
                Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ){
                    TextField(
                        value = centerNameInputValue,
                        onValueChange = {
                            centerNameInputValue = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                2.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        shape = RoundedCornerShape(8.dp),
                        label = { Text(text = "센터 이름")},
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                        ),
                        textStyle = TextStyle(
                            color = Color.Black, fontSize = 25.sp,
                            fontFamily = FontFamily.SansSerif
                        ),
                        maxLines = 2,
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        )
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(vertical = 10.dp),
                        onClick = {
                            if(centerNameInputValue.text.isNotEmpty()){
                                if(isUpdate){
                                    selectedCenter?.let {selectedCenter ->
                                        centerViewModel.updateCenter(
                                            selectedCenter,
                                            CenterRequest(
                                                name = centerNameInputValue.text
                                            )
                                        )
                                    }
                                    centerViewModel.clearSelectedCenter()
                                    childClassViewModel.clearSelectedChildClass()
                                    childInfoViewModel.clearSelectedChildInfo()
                                    childInfoViewModel.clearChildInfos()
                                } else {
                                    centerViewModel.createCenter(
                                        newCenter = CenterRequest(
                                            name = centerNameInputValue.text
                                        )
                                    )
                                }
                                centerNameInputValue = TextFieldValue("")
                                setAddCenterDialog(false)
                            } else {
                                Toasty.warning(context, "센터의 이름을 입력해주세요", Toast.LENGTH_SHORT, true).show()
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

@Composable
fun DialogTopBar(
    dialogName : String,
    closeDialog : (Boolean) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = dialogName,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        IconButton(
            onClick = {closeDialog(false)}
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp),
                imageVector = Icons.Default.Close,
                contentDescription = null
            )
        }
    }
}