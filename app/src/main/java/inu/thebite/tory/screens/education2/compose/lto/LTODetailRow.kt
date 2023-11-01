package inu.thebite.tory.screens.education2.compose.lto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.R
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel


@Composable
fun LTODetailRow(
    selectedLTO: LtoResponse,
    ltoViewModel : LTOViewModel,
    selectedLTOStatus : MutableState<String>,
    setAddSTODialog : (Boolean) -> Unit
){

    val ltos by ltoViewModel.ltos.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        selectedLTO?.let { selectedLTO ->
            Text(
                text = "LTO : ",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            Text(
                text = selectedLTO.name,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(7f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            LTOStatusButtons(
                modifier = Modifier.weight(3.5f),
                selectedLTO = selectedLTO,
                selectedLTOStatus = selectedLTOStatus,
                setSelectedLTOStatus =  {
                    if(it == selectedLTOStatus.value){
                        selectedLTOStatus.value = ""
                        ltoViewModel.setSelectedLTOStatus(selectedLTO, "")
                    } else {
                        selectedLTOStatus.value = it
                        ltoViewModel.setSelectedLTOStatus(selectedLTO, it)
                    }
                    selectedLTO.status = it
                    ltoViewModel.setSelectedLTO(selectedLTO)

                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedButton(
                modifier = Modifier
                    .size(40.dp),
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    if(ltoViewModel.ltoGraphList.value.isNullOrEmpty()){
                        ltoViewModel.getLTOGraph(selectedLTO)
                    } else {
                        ltoViewModel.clearLTOGraphList()
                    }
                },
                contentPadding = PaddingValues(2.dp)
            ){
                Icon(
                    modifier = Modifier
                        .size(35.dp),
                    painter = painterResource(id = R.drawable.icon_chart),
                    contentDescription = null,
                    tint = Color.Black)
            }
            OutlinedButton(
                modifier = Modifier
                    .weight(1.5f)
                    .height(40.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    //STO 추가 Dialog
                    setAddSTODialog(true)
                },
                contentPadding = PaddingValues(6.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize(),
                    text = "STO 추가",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(10.dp))

        }
    }
}