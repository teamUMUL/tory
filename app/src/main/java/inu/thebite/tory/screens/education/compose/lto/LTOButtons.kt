package inu.thebite.tory.screens.education.compose.lto

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import es.dmoral.toasty.Toasty
import inu.thebite.tory.R
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.screens.education.compose.dialog.lto.UpdateLTOItemDialog
import inu.thebite.tory.screens.education.compose.dialog.sto.AddSTODialog
import inu.thebite.tory.screens.education.compose.graph.GraphRow
import inu.thebite.tory.screens.education.compose.graph.GraphTopBar
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato
import kotlinx.coroutines.coroutineScope


@Composable
fun LTOButtons(
    selectedLTO: LtoResponse,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel
) {
    val context = LocalContext.current

    val (addSTODialog, setAddSTODialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if (addSTODialog) {
        AddSTODialog(
            setAddSTOItem = { setAddSTODialog(it) },
            stoViewModel = stoViewModel,
            selectedLTO = selectedLTO
        )
    }

    val (updateLTODialog, setUpdateLTODialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if (updateLTODialog) {
        UpdateLTOItemDialog(
            context = context,
            setUpdateLTOItem = { setUpdateLTODialog(it) },
            selectedLTO = selectedLTO,
            ltoViewModel = ltoViewModel
        )
    }

    val ltoGraphList by ltoViewModel.ltoGraphList.collectAsState()

    val (isLTOGraphOn, setIsLTOGraphOn) = remember {
        mutableStateOf(false)
    }
    if(isLTOGraphOn){
        ltoGraphList?.let {ltoGraphList ->
            Dialog(
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                ),
                onDismissRequest = { setIsLTOGraphOn(false) }
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GraphTopBar(
                        setIsLTOGraphOn = {setIsLTOGraphOn(it)}
                    )
                    GraphRow(stos = ltoGraphList, stoViewModel = stoViewModel)
                }
            }
        }


    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val buttonList = listOf(
                "완료",
                "진행중",
                "중지"
            )
            buttonList.forEach { button ->
                OutlinedButton(
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 2.dp),
                    onClick = {
                        ltoViewModel.setSelectedLTOStatus(
                            selectedLTO = selectedLTO,
                            changeState = button
                        )
                    },
                    border = BorderStroke(
                        width = 1.dp,
                        color = when (button) {
                            "완료" -> Color(0xFF34C648)
                            "진행중" -> Color(0xFF40B9FC)
                            "중지" -> Color(0xFFFC605C)
                            else -> Color.Black
                        }
                    ),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (button) {
                            "완료" -> if (selectedLTO.status == button) Color(0xFF34C648) else Color.Transparent
                            "진행중" -> if (selectedLTO.status == button) Color(0xFF40B9FC) else Color.Transparent
                            "중지" -> if (selectedLTO.status == button) Color(0xFFFC605C) else Color.Transparent
                            else -> if (selectedLTO.status == button) Color.White else Color.White
                        },
                        contentColor = when (button) {
                            "완료" -> if (selectedLTO.status == button) Color.White else Color(
                                0xFF34C648
                            )

                            "진행중" -> if (selectedLTO.status == button) Color.White else Color(
                                0xFF40B9FC
                            )

                            "중지" -> if (selectedLTO.status == button) Color.White else Color(
                                0xFFFC605C
                            )

                            else -> if (selectedLTO.status == button) Color.White else Color.Black
                        }
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = button,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = fontFamily_Lato,
                            fontWeight = FontWeight(500),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
        IconButton(
            onClick = {
                setUpdateLTODialog(true)
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_edit),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.8f),
                tint = Color.Gray
            )

        }
        IconButton(
            onClick = {
                ltoViewModel.getLTOGraph(context = context,selectedLTO = selectedLTO)
                setIsLTOGraphOn(true)
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_lto_graph),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.6f),
            )

        }
        OutlinedButton(
            modifier = Modifier
                .padding(vertical = 2.dp, horizontal = 16.dp),
            onClick = {
                setAddSTODialog(true)
            },
            border = BorderStroke(width = 0.8.dp, color = Color(0xFFCECECE)),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 15.dp)
        ) {
            Text(
                text = "STO 추가",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = fontFamily_Inter,
                    fontWeight = FontWeight(500),
                    color = Color.Black,
                )
            )
        }
    }
}