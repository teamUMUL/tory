package inu.thebite.tory.screens.DataScreen.Compose.Dialog

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.ChildViewModel
import inu.thebite.tory.database.LTO.LTOEntity
import inu.thebite.tory.screens.DataScreen.STOViewModel
import kotlinx.coroutines.launch
import java.sql.Date


@Composable
fun AddSTOItemDialog(
    setAddSTOItem: (Boolean) -> Unit,
    stoViewModel: STOViewModel,
    childViewModel : ChildViewModel,
    selectedLTO : LTOEntity?,
    selectedChildClass: String,
    selectedChildName: String,
    selectedDevIndex: Int,
    updateSTOs: () -> Unit
) {
    val addSTOScrollState = rememberScrollState()

    var stoNameInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var stoDetailInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var stoTryNumInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("15"))
    }
    var stoSuccessStandardInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
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
    Dialog(
        onDismissRequest = {setAddSTOItem(false)},
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White)
                .padding(
                    horizontal = 8.dp,
                    vertical = 16.dp
                )
        ){
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .verticalScroll(addSTOScrollState)) {
                stoTextFieldFrame("STO 이름", stoNameInputValue, setInputValue = {stoNameInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
                stoTextFieldFrame("STO 내용", stoDetailInputValue, setInputValue = {stoDetailInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
                stoTextFieldFrame("시도 수", stoTryNumInputValue, setInputValue = {stoTryNumInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
                stoTextFieldFrame("준거도달 기준", stoSuccessStandardInputValue, setInputValue = {stoSuccessStandardInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
                stoTextFieldFrame("촉구방법", stoMethodInputValue, setInputValue = {stoMethodInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
                stoTextFieldFrame("강화스케줄", stoScheduleInputValue, setInputValue = {stoScheduleInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
                stoTextFieldFrame("메모", stoMemoInputValue, setInputValue = {stoMemoInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    stoViewModel.createSTO(
                        className = selectedChildClass,
                        childName = selectedChildName,
                        selectedDEV = stoViewModel.developZoneItems[selectedDevIndex],
                        selectedLTO = selectedLTO!!.ltoName,
                        stoName = stoNameInputValue.text,
                        stoDescription = stoDetailInputValue.text,
                        stoTryNum = stoTryNumInputValue.text.toInt(),
                        stoSuccessStandard = stoSuccessStandardInputValue.text,
                        stoMethod = stoMethodInputValue.text,
                        stoSchedule = stoScheduleInputValue.text,
                        stoMemo = stoMemoInputValue.text,
                        stoState = -1,
                        gameResult = List(stoTryNumInputValue.text.toInt()) { "n" },
                        gameItems = mutableListOf<String>(),
                        date = mutableListOf<Date>(),
                        plusRatio = mutableListOf<Float>(),
                        minusRatio =mutableListOf<Float>()
                    )


                    setAddSTOItem(false)
                    stoNameInputValue = TextFieldValue("")
                    stoDetailInputValue = TextFieldValue("")
                    stoTryNumInputValue = TextFieldValue("")
                    stoSuccessStandardInputValue = TextFieldValue("")
                    stoMethodInputValue = TextFieldValue("")
                    stoScheduleInputValue = TextFieldValue("")
                    stoMemoInputValue = TextFieldValue("")

                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = null, Modifier.size(40.dp))
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun stoTextFieldFrame(typeOfInput : String, inputValue: TextFieldValue, setInputValue:(TextFieldValue) -> Unit){
    TextField(
        value = inputValue,
        onValueChange = {
            setInputValue(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(8.dp),
        placeholder = { Text(text = typeOfInput) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
        ),
        textStyle = TextStyle(
            color = Color.Black, fontSize = TextUnit.Unspecified,
            fontFamily = FontFamily.SansSerif
        ),
        maxLines = 2,
        singleLine = false
    )
}