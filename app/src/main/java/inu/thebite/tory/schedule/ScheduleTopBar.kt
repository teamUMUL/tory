package inu.thebite.tory.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import inu.thebite.tory.R
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education2.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel

@Composable
fun ScheduleTopBar(
    modifier : Modifier = Modifier,
    currentRoute : String,
    dummySTOList : MutableList<StoResponse>,
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel
) {

    Row(
        modifier = modifier
            .fillMaxHeight(),
//            .weight(4f),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (currentRoute == "Education") {
            Icon(
                painter = painterResource(id = R.drawable.icon_schedule),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 30.dp)
                    .size(25.dp),
                tint = Color.White
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight()
                    .background(
                        color = Color(0xFF9175E7),
                        shape = RoundedCornerShape(10.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                DragDropList(
                    items = dummySTOList,
                    onMove = { fromIndex, toIndex ->
                        dummySTOList.move(fromIndex, toIndex)
                    },
                    devViewModel = devViewModel,
                    ltoViewModel = ltoViewModel,
                    stoViewModel = stoViewModel
                )





            }
        }

    }
}