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
import inu.thebite.tory.screens.DataScreen.STODetailViewModel
import inu.thebite.tory.screens.DataScreen.STOViewModel


@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateSTOItemDialog(
    stoViewModel: STOViewModel,
    stoDetailViewModel: STODetailViewModel,
    selectedDevIndex: Int,
    selectedLTOIndex: Int,
    selectedSTOIndex: Int,
    selectedSTO : String,
    setSelectedSTO : (String) -> Unit,
    setUpdateSTOItem : (Boolean) -> Unit,
    setSelectedSTODetailList: (List<String>) -> Unit
) {
    val addSTOScrollState = rememberScrollState()

    var stoNameInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[0]))
    }
    var stoDetailInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[1]))
    }
    var stoTryNumInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[2]))
    }
    var stoSuccessStandardInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[3]))
    }
    var stoMethodInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[4]))
    }
    var stoScheduleInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[5]))
    }
    var stoMemoInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[6]))
    }

    val beforeSTOName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[0]))
    }
    val beforeSTODetail by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[1]))
    }
    val beforeSTOTryNum by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[2]))
    }
    val beforeSTOSuccessStandard by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[3]))
    }
    val beforeSTOMethod by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[4]))
    }
    val beforeSTOSchedule by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[5]))
    }
    val beforeSTOMemo by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[6]))
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
                    stoViewModel.updateSTO(selectedLTOIndex,selectedDevIndex,beforeSTOName.text,stoNameInputValue.text)
                    stoDetailViewModel.updateSTODetail(
                        selectedLTOIndex,
                        selectedDevIndex,
                        listOf<String>(
                            beforeSTOName.text,
                            beforeSTODetail.text,
                            beforeSTOTryNum.text,
                            beforeSTOSuccessStandard.text,
                            beforeSTOMethod.text,
                            beforeSTOSchedule.text,
                            beforeSTOMemo.text
                        ),
                        listOf(
                            stoNameInputValue.text,
                            stoDetailInputValue.text,
                            stoTryNumInputValue.text,
                            stoSuccessStandardInputValue.text,
                            stoMethodInputValue.text,
                            stoScheduleInputValue.text,
                            stoMemoInputValue.text
                        ))
                    setSelectedSTODetailList(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first.toMutableList())
                    val selectedSTO = stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDevIndex, selectedSTOIndex).first[0]
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