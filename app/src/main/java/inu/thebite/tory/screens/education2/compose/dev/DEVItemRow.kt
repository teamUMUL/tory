package inu.thebite.tory.screens.education2.compose.dev

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.domain.DomainResponse


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