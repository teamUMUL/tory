package inu.thebite.tory.screens.DataScreen.Compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.zIndex
import inu.thebite.tory.R
import inu.thebite.tory.screens.DataScreen.LTOViewModel

@Composable
fun LTODetailsRow(
    selectedLTO: String,
    isGraphSelected: Boolean,
    setGraphSelected: (Boolean) -> Unit,
    setDetailListIndex: (Int) -> Unit,
    ltoViewModel: LTOViewModel,
    selectedDevIndex: Int,
    setProgressState: (Int) -> Unit,
    ltoDetailListIndex: Int,
    setLTOUpdateDialog: (Boolean) -> Unit
) {
    val cornerRadius = 8.dp
    // DetailsRow 내용
    val detailList = listOf<String>(
        "진행중",
        "중지",
        "완료"
    )
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)
        .padding(5.dp)
        .background(Color.Transparent)
        .border(4.dp, MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp)),
    ){
        Box(modifier = Modifier
            .width(100.dp)
            .fillMaxHeight()
            .padding(5.dp),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Details",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
        Box(modifier = Modifier
            .fillMaxSize(),
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier
                    .width(500.dp)
                    .fillMaxHeight()
                ){
                    Row(modifier = Modifier
                        .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 10.dp),
                            text = selectedLTO,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                        if(selectedLTO != ""){
                            OutlinedButton(
                                modifier = Modifier
                                    .size(40.dp),
                                border = BorderStroke(2.dp, Color.Black),
                                shape = RoundedCornerShape(5.dp),
                                onClick = {
                                    setGraphSelected(!isGraphSelected)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if(isGraphSelected) MaterialTheme.colorScheme.secondary else Color.Transparent
                                ),
                                contentPadding = PaddingValues(2.dp)
                            ){
                                Icon(
                                    modifier = Modifier
                                        .size(35.dp),
                                    painter = painterResource(id = R.drawable.icon_chart_inner),
                                    contentDescription = null,
                                    tint = Color.Black)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            OutlinedButton(
                                modifier = Modifier
                                    .size(40.dp),
                                border = BorderStroke(2.dp, Color.Black),
                                shape = RoundedCornerShape(5.dp),
                                onClick = {
                                    setLTOUpdateDialog(true)
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
                        }
                    }
                }
                if(selectedLTO != ""){
                    Box(modifier = Modifier
                        .width(300.dp)
                        .fillMaxHeight()
                    ){
                        Row(modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ){
                            detailList.forEachIndexed { index, item ->
                                OutlinedButton(
                                    onClick = {
                                        setDetailListIndex(index)
                                        setProgressState(index)
                                        ltoViewModel.addOrUpdateLTO(selectedDevIndex, selectedLTO, index)
                                    },
                                    shape =  when (index) {
                                        // left outer button
                                        0 -> RoundedCornerShape(topStart = cornerRadius, topEnd = 0.dp, bottomStart = cornerRadius, bottomEnd = 0.dp)
                                        // right outer button
                                        detailList.size - 1 -> RoundedCornerShape(topStart = 0.dp, topEnd = cornerRadius, bottomStart = 0.dp, bottomEnd = cornerRadius)
                                        // middle button
                                        else -> RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
                                    },
                                    border = BorderStroke(1.dp, if(ltoDetailListIndex == index) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(alpha = 0.75f)),
                                    modifier = when (index) {
                                        0 ->
                                            Modifier
                                                .offset(0.dp, 0.dp)
                                                .zIndex(if (ltoDetailListIndex == index) 1f else 0f)
                                        else ->
                                            Modifier
                                                .offset((-1 * index).dp, 0.dp)
                                                .zIndex(if (ltoDetailListIndex == index) 1f else 0f)
                                    },
                                    colors =
                                    if(ltoDetailListIndex == index){
                                        ButtonDefaults.outlinedButtonColors(
                                            containerColor =
                                            when (index) {
                                                0 -> {
                                                    Color.Blue
                                                }
                                                1 -> {
                                                    Color.Red
                                                }
                                                else -> {
                                                    Color.Green
                                                }
                                            },
                                            contentColor = Color.White
                                        )
                                    } else {
                                        ButtonDefaults.outlinedButtonColors(
                                            containerColor =
                                            when (index) {
                                                0 -> {
                                                    Color.Blue.copy(alpha = 0.2f)
                                                }
                                                1 -> {
                                                    Color.Red.copy(alpha = 0.2f)
                                                }
                                                else -> {
                                                    Color.Green.copy(alpha = 0.2f)
                                                }
                                            },
                                            contentColor = Color.Black
                                        )
                                    }

                                ){
                                    Text(
                                        text = detailList[index]
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}