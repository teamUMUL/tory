package inu.thebite.tory.screens.DataScreen.Compose.Dialog

import android.annotation.SuppressLint
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import inu.thebite.tory.screens.DataScreen.STOViewModel


@SuppressLint("MutableCollectionMutableState")
@Composable
fun UpdateSTOItemDialog(
    stoViewModel: STOViewModel,
    selectedSTOId: Int,
    setSelectedSTO : (String) -> Unit,
    setUpdateSTOItem : (Boolean) -> Unit,
) {
    val addSTOScrollState = rememberScrollState()

    var stoNameInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoViewModel.getSTOById(stoId = selectedSTOId)!!.stoName))
    }
    var stoDetailInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoViewModel.getSTOById(stoId = selectedSTOId)!!.stoDescription))
    }
    var stoTryNumInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoViewModel.getSTOById(stoId = selectedSTOId)!!.stoTryNum.toString()))
    }
    var stoSuccessStandardInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoViewModel.getSTOById(stoId = selectedSTOId)!!.stoSuccessStandard))
    }
    var stoMethodInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoViewModel.getSTOById(stoId = selectedSTOId)!!.stoMethod))

    }
    var stoScheduleInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoViewModel.getSTOById(stoId = selectedSTOId)!!.stoSchedule))

    }
    var stoMemoInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoViewModel.getSTOById(stoId = selectedSTOId)!!.stoMemo))

    }

    Dialog(
        onDismissRequest = {setUpdateSTOItem(false)},
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
                updateSTOTextFieldFrame(stoNameInputValue, setInputValue = {stoNameInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame(stoDetailInputValue, setInputValue = {stoDetailInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame(stoTryNumInputValue, setInputValue = {stoTryNumInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame(stoSuccessStandardInputValue, setInputValue = {stoSuccessStandardInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame(stoMethodInputValue, setInputValue = {stoMethodInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame(stoScheduleInputValue, setInputValue = {stoScheduleInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
                updateSTOTextFieldFrame(stoMemoInputValue, setInputValue = {stoMemoInputValue = it})
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    //-----------

                    val sto = stoViewModel.getSTOById(selectedSTOId)
                    sto?.stoName = stoNameInputValue.text
                    sto?.stoDescription = stoDetailInputValue.text
                    sto?.stoTryNum = stoTryNumInputValue.text.toInt()
                    sto?.stoSuccessStandard = stoSuccessStandardInputValue.text
                    sto?.stoMethod = stoMethodInputValue.text
                    sto?.stoSchedule = stoScheduleInputValue.text
                    sto?.stoMemo = stoMemoInputValue.text
                    stoViewModel.updateSTO(sto!!)
                    //----------------
                    val selectedSTO = stoViewModel.getSTOById(selectedSTOId)!!.stoName
                    setSelectedSTO(selectedSTO)
                    setUpdateSTOItem(false)
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
fun updateSTOTextFieldFrame(inputValue: TextFieldValue, setInputValue:(TextFieldValue) -> Unit){
    TextField(
        value = inputValue,
        onValueChange = {
            setInputValue(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(8.dp),
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