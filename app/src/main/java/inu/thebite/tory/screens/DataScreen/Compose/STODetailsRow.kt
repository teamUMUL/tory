package inu.thebite.tory.screens.DataScreen.Compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import inu.thebite.tory.screens.DataScreen.STODetailViewModel
import inu.thebite.tory.screens.DataScreen.STOViewModel


@Composable
fun STODetailsRow(
    selectedSTO: String,
    isSTOGraphSelected: Boolean,
    setIsSTOGraphSelected: (Boolean) -> Unit,
    stoViewModel: STOViewModel,
    stoDetailViewModel: STODetailViewModel,
    selectedDevIndex: Int,
    selectedLTOIndex: Int,
    stoDetailListIndex: Int,
    setProgressState: (Int) -> Unit,
){
    val cornerRadius = 8.dp
    val detailList = listOf<String>(
        "진행중",
        "중지",
        "완료"
    )
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 0.dp),
    ){
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier
                .width(400.dp)
                .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "STO Details",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(end = 10.dp)
                )
                if(selectedSTO != ""){
                    OutlinedButton(
                        modifier = Modifier
                            .size(40.dp),
                        border = BorderStroke(2.dp, Color.Black),
                        shape = RoundedCornerShape(5.dp),
                        onClick = {
                            setIsSTOGraphSelected(!isSTOGraphSelected)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(isSTOGraphSelected) MaterialTheme.colorScheme.secondary else Color.Transparent
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
                        border = BorderStroke(2.dp, Color.Black),
                        shape = RoundedCornerShape(5.dp),
                        onClick = {

                        },
                        contentPadding = PaddingValues(6.dp)
                    ){
                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "게임시작",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    }
                }
            }

            if(selectedSTO != "") {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        detailList.forEachIndexed { index, item ->
                            OutlinedButton(
                                onClick = {
                                    setProgressState(index)
                                    stoViewModel.addOrUpdateSTO(
                                        selectedLTOIndex,
                                        selectedDevIndex,
                                        selectedSTO,
                                        index
                                    )
                                    stoDetailViewModel.addOrUpdateSTODetail(
                                        selectedLTOIndex,
                                        selectedDevIndex,
                                        listOf(selectedSTO,"","","","","",""),
                                        index
                                    )
                                },
                                shape = when (index) {
                                    // left outer button
                                    0 -> RoundedCornerShape(
                                        topStart = cornerRadius,
                                        topEnd = 0.dp,
                                        bottomStart = cornerRadius,
                                        bottomEnd = 0.dp
                                    )
                                    // right outer button
                                    detailList.size - 1 -> RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = cornerRadius,
                                        bottomStart = 0.dp,
                                        bottomEnd = cornerRadius
                                    )
                                    // middle button
                                    else -> RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 0.dp,
                                        bottomStart = 0.dp,
                                        bottomEnd = 0.dp
                                    )
                                },
                                border = BorderStroke(
                                    1.dp,
                                    if (stoDetailListIndex == index) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(
                                        alpha = 0.75f
                                    )
                                ),
                                modifier = when (index) {
                                    0 ->
                                        Modifier
                                            .offset(0.dp, 0.dp)
                                            .zIndex(if (stoDetailListIndex == index) 1f else 0f)

                                    else ->
                                        Modifier
                                            .offset((-1 * index).dp, 0.dp)
                                            .zIndex(if (stoDetailListIndex == index) 1f else 0f)
                                },
                                colors =
                                if (stoDetailListIndex == index) {
                                    ButtonDefaults.outlinedButtonColors(
                                        containerColor =
                                        when (index) {
                                            0 -> {
                                                Color.Blue
                                            }

                                            1 -> {
                                                Color.Green
                                            }

                                            else -> {
                                                Color.Red
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
                                                Color.Green.copy(alpha = 0.2f)
                                            }

                                            else -> {
                                                Color.Red.copy(alpha = 0.2f)
                                            }
                                        },
                                        contentColor = Color.Black
                                    )
                                }

                            ) {
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