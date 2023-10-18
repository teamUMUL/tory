package inu.thebite.tory.screens.education2.screen

import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Column(
        modifier = Modifier
            .fillMaxWidth(0.2f)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ){

        LazyColumn(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f)){
            items(ltos){lto ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(8.dp)
                        ),
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

@Composable
fun DEVItemRow(

){
    val selectedDEV = DomainResponse(
        id = "0",
        name = "2.매칭",
        contents = "",
        type = "",
        status = "",
        useYN = "",
        deleteYN = "",
        registerDate = "",
        templateNum = 0,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Text(
            text = "발달영역 Item : ${selectedDEV.name}",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(start = 10.dp)
        )
    }
}