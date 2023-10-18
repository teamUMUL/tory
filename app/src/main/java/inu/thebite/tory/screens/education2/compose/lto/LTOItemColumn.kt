package inu.thebite.tory.screens.education2.compose.lto

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.R
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.screens.education2.compose.dialog.lto.AddLTODialog
import inu.thebite.tory.screens.education2.compose.dialog.lto.UpdateLTOItemDialog


@Composable
fun LTOItemColumn(
    context: Context
){
    val ltos = mutableListOf<LtoResponse>()
    val (addLTODialog, setAddLTODialog) = remember {
        mutableStateOf(false)
    }
    val (updateLTODialog, setUpdateLTODialog) = remember {
        mutableStateOf(false)
    }
    for (i in 1..10){
        ltos.add(
            LtoResponse(
                id = i.toString(),
                templateNum = i,
                status = "",
                name = "$i. 매칭",
                contents =  "",
                game = "",
                achieveDate = "2023-11-${i}",
                registerDate = "2023-10-${i}",
                delYN = "",
                domainId = i
            )
        )

    }
    var selectedLTO by remember {
        mutableStateOf<LtoResponse?>(null)
    }
    if(addLTODialog){
        AddLTODialog(
            context = context,
            setAddLTOItem = {setAddLTODialog(it)}
        )
    }
    if(updateLTODialog){
        selectedLTO?.let {selectedLTO ->
            UpdateLTOItemDialog(
                context = context,
                setUpdateLTOItem = {setUpdateLTODialog(it)},
                selectedLTO = selectedLTO
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(0.2f)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ){

        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)){
            items(ltos){lto ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .border(
                            width = 2.dp,
                            color = if (selectedLTO == lto) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(
                            color = if (selectedLTO == lto) MaterialTheme.colorScheme.tertiary else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            selectedLTO =
                                if (selectedLTO == lto) {
                                    null
                                } else {
                                    lto
                                }

                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(
                                    text = "${lto.name}",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 22.sp
                                )
                                Text(
                                    text = "등록날짜 : ${lto.registerDate}",
                                    fontWeight = FontWeight.Light,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                    text = "완료날짜 : ${lto.achieveDate}",
                                    fontWeight = FontWeight.Light,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            if(selectedLTO == lto){
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = null
                                            ){
                                                //삭제 AlertDialog
                                            }
                                            .size(30.dp)
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_edit),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = null
                                            ){
                                                setUpdateLTODialog(true)
                                            }
                                            .size(30.dp)
                                    )

                                }

                            }

                        }

                    }

                }

            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(10.dp),
            onClick = {
                setAddLTODialog(true)
            },
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp)
        ){
            Text(
                text = "LTO 추가",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

    }

}
