package inu.thebite.tory.screens.education2.compose.sto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.R
import inu.thebite.tory.database.education.EducationEntity
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education2.viewmodel.EducationViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel


@Composable
fun STOSettingButtons(
    modifier : Modifier,
    setUpdateSTODialog : (Boolean) -> Unit,
    stoViewModel: STOViewModel,
    educationViewModel: EducationViewModel,
    selectedEducation : EducationEntity,
    selectedSTO : StoResponse,
    gameStart : () -> Unit
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            modifier = Modifier
                .size(40.dp),
            border = BorderStroke(1.dp, Color.Black),
            shape = RoundedCornerShape(5.dp),
            onClick = {
                stoViewModel.deleteSTO(selectedSTO)
                educationViewModel.deleteEducation(selectedEducation)
            },
            contentPadding = PaddingValues(2.dp)
        ){
            Icon(
                modifier = Modifier
                    .size(35.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.Black)
        }
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedButton(
            modifier = Modifier
                .size(40.dp),
            border = BorderStroke(1.dp, Color.Black),
            shape = RoundedCornerShape(5.dp),
            onClick = {
                setUpdateSTODialog(true)
            },
            contentPadding = PaddingValues(2.dp)
        ){
            Icon(
                modifier = Modifier
                    .size(35.dp),
                painter = painterResource(id = R.drawable.icon_edit),
                contentDescription = null,
                tint = Color.Black)
        }
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedButton(
            modifier = Modifier
                .width(100.dp)
                .height(40.dp),
            border = BorderStroke(1.dp, Color.Black),
            shape = RoundedCornerShape(5.dp),
            onClick = {
                gameStart()
            },
            contentPadding = PaddingValues(6.dp)
        ){
            Text(
                modifier = Modifier
                    .fillMaxSize(),
                text = "교육시작",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

