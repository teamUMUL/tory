package inu.thebite.tory.screens.education2.screen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoResponse

@Composable
fun NewEducationScreen(

){


    Column(modifier = Modifier.fillMaxSize()) {
        DEVItemRow()
        Divider(thickness = 4.dp, color = MaterialTheme.colorScheme.tertiary)
        LTOAndSTOContainer()
    }

}

@Composable
fun LTOAndSTOContainer(){
    Row(modifier = Modifier.fillMaxSize()) {
        LTOItemColumn()
        STOItemColumn()
    }
}

@Composable
fun STOItemColumn(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){

    }
}

@Composable
fun LTOItemColumn(){
    val ltos = mutableListOf<LtoResponse>()

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
                            color = if(selectedLTO == lto) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
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
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DEVItemRow(
){
        val developZoneItems = listOf<String>(
        "학습준비",
        "매칭",
        "동작모방",
        "언어모방",
        "변별",
        "지시따라하기",
        "요구하기",
        "명명하기",
        "인트라",
        "가나다"
    )
    val allDEVs = mutableListOf<DomainResponse>()
    for (i in 1..10){
        allDEVs.add(
            DomainResponse(
                id = i.toString(),
                name = "$i. ${developZoneItems[i-1]}",
                contents = "",
                type = "",
                status = "",
                useYN = "",
                deleteYN = "",
                registerDate = "",
                templateNum = 0,
            )
        )
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var selectedDEV by remember {
        mutableStateOf<DomainResponse?>(null)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){

        Text(
            text = "발달영역 Item : ",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(start = 10.dp)
        )
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = isExpanded,
            onExpandedChange = {isExpanded = !isExpanded}
        ){
            TextField(

                value = selectedDEV?.name ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedContainerColor= Color.Transparent,
                    unfocusedContainerColor= Color.Transparent,
                    disabledContainerColor= Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {isExpanded = false},
                modifier = Modifier
                    .fillMaxWidth()
                    .exposedDropdownSize(),
            ){
                allDEVs.forEach { dev ->
                    DropdownMenuItem(
                        text = {
                            Text(text = dev.name)
                        },
                        onClick = {
                            selectedDEV = dev
                            isExpanded = false
                        }
                    )
                }
            }
        }

    }
}