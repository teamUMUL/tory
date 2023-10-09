package inu.thebite.tory.screens.datasceen.Compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DevelopZoneRow(
    developZoneItems: List<String>,
    selectDevelopItem: (String)->Unit,
) {
    // DevelopZoneRow 내용
    val selectedDevItem = rememberSaveable{ mutableStateOf<String?>("1. 학습준비") }
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(85.dp)
        .background(Color.Transparent)
    ){
        Box(modifier = Modifier
            .width(100.dp)
            .fillMaxHeight()
            .padding(5.dp),
            contentAlignment = Alignment.Center,
        ){
            Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "발달영역",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Item",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
        }
        LazyRow(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(developZoneItems){ developZoneItem ->
                LazyColumnItem(item = developZoneItem, selectedDevItem,
                    select = {
                        selectDevelopItem(it)

                    })
            }
        }
    }
}
