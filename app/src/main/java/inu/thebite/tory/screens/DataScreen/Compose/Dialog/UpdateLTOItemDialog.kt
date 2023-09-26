package inu.thebite.tory.screens.DataScreen.Compose.Dialog

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
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.database.STOEntity
import inu.thebite.tory.screens.DataScreen.LTOViewModel
import inu.thebite.tory.screens.DataScreen.STOViewModel


@Composable
fun UpdateLTOItemDialog(
    setUpdateLTOItem: (Boolean) -> Unit,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel,
    stos : List<STOEntity>,
    selectedDevIndex: Int,
    selectedLTOIndex: Int,
    selectedChildClass: String,
    selectedChildName: String,
    setSelectedLTO: (String) -> Unit
) {
    var ltoInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(ltoViewModel.getLTO(selectedDevIndex).first[selectedLTOIndex]))
    }

    val beforeLTOName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(ltoViewModel.getLTO(selectedDevIndex).first[selectedLTOIndex]))
    }
    // AddLTOItemDialog 내용
    Dialog(
        onDismissRequest = {setUpdateLTOItem(false)},
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
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
                    .height(100.dp),
                shape = RoundedCornerShape(8.dp),
                placeholder = { Text(text = "Enter your LTO") },
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
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    ltoViewModel.updateLTO(selectedDevIndex, selectedLTOIndex,beforeLTOName.text,ltoInputValue.text)
                    setSelectedLTO(ltoInputValue.text)
                    setUpdateLTOItem(false)
                    for(sto in stos){
                        sto.selectedLTO = ltoInputValue.text
                        stoViewModel.updateSTO(sto)
                    }
                    stoViewModel.getSTOsByCriteria(selectedChildClass, selectedChildName, stoViewModel.developZoneItems[selectedDevIndex], ltoInputValue.text)
                    ltoInputValue = TextFieldValue("")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = null, Modifier.size(40.dp))
            }
        }
    }
}
