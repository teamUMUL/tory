package inu.thebite.tory.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import inu.thebite.tory.R
import inu.thebite.tory.screens.DataScreen.LTOViewModel
//import inu.thebite.tory.screens.DataScreen.STOViewModel

@Composable
fun HomeScreen(
        homeViewModel: HomeViewModel = viewModel(),
){
    val ltoViewModel : LTOViewModel = viewModel()
//    val stoViewModel : STOViewModel = viewModel()

    val (dialogOpen, setDialogOpen) = remember {
        mutableStateOf(false)
    }
    var isDialogVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .padding(16.dp)
    ) {
        Text(text = "Dash Board")
        Row (modifier = Modifier
            .padding(start = 36.dp),
            horizontalArrangement = Arrangement.spacedBy(44.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ){
            ChainCard()
            ClassCard()
            ChildrenCard()

        }


    }

}



