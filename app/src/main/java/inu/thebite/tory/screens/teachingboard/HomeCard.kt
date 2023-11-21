package inu.thebite.tory.screens.teachingboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import inu.thebite.tory.screens.teachingboard.viewmodel.CenterSelectViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildClassSelectViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildSelectViewModel
import inu.thebite.tory.R
import inu.thebite.tory.screens.teachingboard.dialog.CenterDialog

import inu.thebite.tory.screens.teachingboard.dialog.ChildDialog
import inu.thebite.tory.screens.teachingboard.dialog.ClassDialog

@Composable
fun ChainCard(
    modifier: Modifier = Modifier,
    centerSelectViewModel: CenterSelectViewModel,
    childClassSelectViewModel: ChildClassSelectViewModel,
    childSelectViewModel: ChildSelectViewModel
){
    val selectedCenter by centerSelectViewModel.selectedCenter.collectAsState()

    var isDialogVisible by remember { mutableStateOf(false) }

    if(isDialogVisible){
        centerSelectViewModel.getAllCenters()
        CenterDialog(
            showDialog = isDialogVisible,
            onDismiss = { isDialogVisible = false },
            onConfirm = { configText ->
                // Handle dialog confirmation here
                // configText contains the user input
                isDialogVisible = false},
            centerSelectViewModel = centerSelectViewModel,
            childClassSelectViewModel = childClassSelectViewModel,
            childSelectViewModel = childSelectViewModel
        )
    }
    Box(modifier = modifier   //카드 하얀 배경
        .height(50.dp)
        .shadow(
            elevation = 4.dp,
            spotColor = Color(0x40000000),
            ambientColor = Color(0x40000000)
        )
        .clickable { isDialogVisible = true }
        .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))

        ) {

        Row(
            modifier = Modifier
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier= Modifier

                .width(21.dp)
                .align(Alignment.CenterVertically)
                .height(24.dp),
                painter = painterResource(id = R.drawable.center_icon),
                contentDescription = "null",
                contentScale = ContentScale.Crop)
            Text(modifier = Modifier
                .width(53.dp)
                .height(32.dp)
                .padding(top = 2.dp),
                text = "Center",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFA6ACBE),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.14.sp,
                )
            )
            Text(
                modifier = Modifier
                    .width(140.dp)
                    .height(32.dp),
                text = selectedCenter?.name ?: "미선택",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF0047B3),
                    letterSpacing = 0.28.sp,
                    textAlign = TextAlign.Center)
                )


        }
    }

}
@Composable
fun ClassCard(
    modifier: Modifier = Modifier,
    childClassSelectViewModel: ChildClassSelectViewModel,
    childSelectViewModel: ChildSelectViewModel
){
    val selectedChildClass by childClassSelectViewModel.selectedChildClass.collectAsState()

    var isDialogVisible by remember { mutableStateOf(false) }

    if(isDialogVisible){
        childClassSelectViewModel.getAllChildClasses()
        ClassDialog(
            showDialog = isDialogVisible,
            onDismiss = { isDialogVisible = false },
            onConfirm = { configText ->
                // Handle dialog confirmation here
                // configText contains the user input
                isDialogVisible = false},
            childClassSelectViewModel = childClassSelectViewModel,
            childSelectViewModel = childSelectViewModel
        )
    }


    Box(modifier = modifier   //카드 하얀 배경
        .height(50.dp)
        .shadow(
            elevation = 4.dp,
            spotColor = Color(0x40000000),
            ambientColor = Color(0x40000000)
        )
        .clickable { isDialogVisible = true }
        .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))

        ) {

        Row(
            modifier = Modifier
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier= Modifier
                .width(22.dp)
                .align(Alignment.CenterVertically)
                .height(26.dp),
                painter = painterResource(id = R.drawable.class_icon),
                contentDescription = "null",
                contentScale = ContentScale.Fit)

            Text(modifier = Modifier
                .width(53.dp)
                .height(32.dp)
                .padding(top = 2.dp),
                text = "Class",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFA6ACBE),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.14.sp,
                )
            )
            Text(
                modifier = Modifier
                    .width(140.dp)

                    .height(32.dp),
                text = selectedChildClass?.name ?: "미선택",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF0047B3),
                    letterSpacing = 0.28.sp,
                    textAlign = TextAlign.Center)
            )




        }
    }

}
@Composable
fun ChildrenCard(
    modifier: Modifier = Modifier,
    childSelectViewModel: ChildSelectViewModel
){
    val selectedChild by childSelectViewModel.selectedChildInfo.collectAsState()

    var isDialogVisible by remember { mutableStateOf(false) }

    if(isDialogVisible){
        childSelectViewModel.getAllChildInfos()
        ChildDialog(
            showDialog = isDialogVisible,
            onDismiss = { isDialogVisible = false },
            childSelectViewModel = childSelectViewModel
        )
    }

    Box(modifier = modifier   //카드 하얀 배경
        .height(50.dp)
        .shadow(
            elevation = 4.dp,
            spotColor = Color(0x40000000),
            ambientColor = Color(0x40000000)
        )
        .clickable { isDialogVisible = true }
        .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),

        ) {

        Row(
            modifier = Modifier
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier= Modifier
                .width(20.dp)
                .align(Alignment.CenterVertically)
                .height(20.dp),
                painter = painterResource(id = R.drawable.child_icon),
                contentDescription = "null",
                contentScale = ContentScale.Fit)

            Text(modifier = Modifier
                .width(53.dp)
                .height(32.dp)
                .padding(top = 2.dp),
                text = "Child",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFA6ACBE),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.14.sp,
                )
            )
            Text(
                modifier = Modifier
                    .width(140.dp)
                    .height(32.dp),
                text = selectedChild?.name ?: "미선택",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF0047B3),
                    letterSpacing = 0.28.sp,
                    textAlign = TextAlign.Center)
            )




        }
    }

}
