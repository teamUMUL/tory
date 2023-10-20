package inu.thebite.tory.screens.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import inu.thebite.tory.R
import inu.thebite.tory.screens.DataScreen.LTOViewModel
import inu.thebite.tory.screens.HomeViewModel

//import inu.thebite.tory.screens.DataScreen.STOViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(color = Color(0xFFEFEFEF))

    ) {
        Text(
            text = "Dash board",
            modifier = Modifier.fillMaxWidth().height(50.dp).padding(start = 16.dp),
            style = TextStyle(
                fontSize = 33.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),

                textAlign = TextAlign.Start,
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(start = 16.dp)

        ) {
            ChainCard()
            Spacer(modifier = Modifier.width(32.dp))
            ClassCard()
            Spacer(modifier = Modifier.width(32.dp))
            ChildrenCard()

        }

            Row (modifier = Modifier
                .padding(16.dp),
                horizontalArrangement = Arrangement.Center){
                childInfor()
                editProgram()
                reportList()

            }

    }

}

