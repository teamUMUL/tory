package inu.thebite.tory.screens.education2.compose.lto

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education2.compose.dialog.sto.AddSTODialog
import inu.thebite.tory.screens.education2.compose.sto.STOItemColumn
import inu.thebite.tory.screens.education2.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education2.viewmodel.EducationViewModel
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel

@Composable
fun LTOAndSTOContainer(
    context : Context,
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel,
    educationViewModel : EducationViewModel
){
    val selectedLTO by ltoViewModel.selectedLTO.collectAsState()

    val selectedLTOStatus = remember { mutableStateOf("")}
    val (addSTODialog, setAddSTODialog) = remember {
        mutableStateOf(false)
    }

    Row(modifier = Modifier.fillMaxSize()) {
        LTOItemColumn(
            context = context,
            selectedLTOStatus = selectedLTOStatus,
            devViewModel = devViewModel,
            ltoViewModel = ltoViewModel
        )
        Divider(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight(),
            color = MaterialTheme.colorScheme.tertiary
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            selectedLTO?.let {selectedLTO ->
                LTODetailRow(
                    ltoViewModel = ltoViewModel,
                    selectedLTOStatus = selectedLTOStatus,
                    selectedLTO = selectedLTO,
                    setAddSTODialog = {setAddSTODialog(it)}
                )
                Divider(
                    thickness = 4.dp,
                    color = MaterialTheme.colorScheme.tertiary
                )
                STOItemColumn(
                    ltoViewModel = ltoViewModel,
                    educationViewModel = educationViewModel,
                    stoViewModel = stoViewModel,
                    addSTODialog = addSTODialog,
                    setAddSTODialog = {setAddSTODialog(it)}
                )
            }


        }

    }
}



