package inu.thebite.tory.screens.education2.compose.sto

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel

@Composable
fun STOItemColumn(
    ltoViewModel: LTOViewModel
){
    val allLTOs by ltoViewModel.allLTOs.collectAsState()
    val ltos by ltoViewModel.ltos.collectAsState()
    val selectedLTO by ltoViewModel.selectedLTO.collectAsState()
    var selectedSTO by remember {
        mutableStateOf<StoResponse?>(null)
    }
    var isExpanded by remember { mutableStateOf (false) }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        val stos = mutableListOf<StoResponse>()
        for(i in 1..10){
            stos.add(
                StoResponse(
                    id = i.toLong() ,
                    templateNum = i,
                    status = "",
                    name = i.toString(),
                    contents = "",
                    count = 0,
                    goal = 0,
                    goalPercent = 0,
                    achievementOrNot = "",
                    urgeType = "",
                    urgeContent = "",
                    enforceContent = "",
                    memo = "",
                    hitGoalDate = "",
                    registerDate = "",
                    delYN = "",
                    lto = selectedLTO ?: LtoResponse(
                        id = i.toLong(),
                        templateNum = 0,
                        status = "",
                        name = "",
                        contents = "",
                        game = "",
                        achieveDate = "",
                        registerDate = "",
                        delYN = "",
                        domain = DomainResponse(
                            id = 0L,
                            templateNum = 0,
                            type = "",
                            status = "",
                            name = "",
                            contents = "",
                            useYN = "",
                            delYN = "",
                            registerDate = ""
                        )
                    )
                )
            )
        }
        LazyColumn(
            reverseLayout = true,
            modifier = Modifier.padding(10.dp)
        ){
            items(stos){sto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .border(
                            2.dp,
                            color = if (selectedSTO == sto) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(color = if (selectedSTO == sto) MaterialTheme.colorScheme.tertiary else Color.Transparent)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (selectedSTO == sto) {
                                selectedSTO = null
                            } else {
                                selectedSTO = sto
                            }
                        }
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            )
                        ),
                ){
                    Column(
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = sto.name ,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if(selectedSTO == sto) Color.Black else MaterialTheme.colorScheme.secondary,
                                modifier = Modifier
                                    .weight(6f)
                            )
                            IconButton(
                                onClick = {isExpanded = !isExpanded},
                                modifier = Modifier
                                    .weight(1f)
                            ){
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        }

                        if(isExpanded && selectedSTO == sto){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(500.dp)
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {

                            }
                        }
                    }
                }

            }
        }

    }
}




