package inu.thebite.tory.screens.homescreen

import android.util.Log
import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import inu.thebite.tory.ChildViewModel
import inu.thebite.tory.R
import inu.thebite.tory.SegmentedControl
import inu.thebite.tory.screens.homescreen.dialog.ChainDialog
import inu.thebite.tory.screens.homescreen.dialog.ChildDialog
import inu.thebite.tory.screens.homescreen.dialog.ClassDialog
import inu.thebite.tory.screens.homescreen.dialog.SuccessDialog

@Composable
fun ChainCard(
    modifier: Modifier = Modifier,
    viewModel: ChildViewModel = viewModel()
){
    val (centerName, setCenterName) = remember {
        mutableStateOf("")
    }
    var isDialogVisible by remember { mutableStateOf(false) }

    if(isDialogVisible){
        ChainDialog(
            showDialog = isDialogVisible,
            onDismiss = { isDialogVisible = false },
            onConfirm = { configText ->
                // Handle dialog confirmation here
                // configText contains the user input
                isDialogVisible = false},
            centerName = centerName,
            setCenterName = {setCenterName(it)}
        )
    }
    Box(modifier = modifier   //카드 하얀 배경
//        .height(103.dp)
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
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier= Modifier

                .width(28.dp)
                .align(Alignment.CenterVertically)
                .height(32.dp),
                painter = painterResource(id = R.drawable.center_icon),
                contentDescription = "null",
                contentScale = ContentScale.Crop)
            Text(modifier = Modifier
                .width(53.dp)
                .height(32.dp)
                .padding(top = 2.dp),
                text = "Center",
                style = TextStyle(
                    fontSize = 14.sp,
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
                text = centerName,
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 24.sp,
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
    viewModel: ChildViewModel = viewModel()
){
    val (className, setClassName) = remember {
        mutableStateOf("")
    }
    var isDialogVisible by remember { mutableStateOf(false) }

    if(isDialogVisible){
        ClassDialog(
            showDialog = isDialogVisible,
            onDismiss = { isDialogVisible = false },
            onConfirm = { configText ->
                // Handle dialog confirmation here
                // configText contains the user input
                isDialogVisible = false},
            className = className,
            setClassName = {setClassName(it)}
        )
    }


    Box(modifier = modifier   //카드 하얀 배경
//        .height(103.dp)
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
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier= Modifier
                .width(28.dp)
                .align(Alignment.CenterVertically)
                .height(32.dp),
                painter = painterResource(id = R.drawable.class_icon),
                contentDescription = "null",
                contentScale = ContentScale.Fit)

            Text(modifier = Modifier
                .width(53.dp)
                .height(32.dp)
                .padding(top = 2.dp),
                text = "Class",
                style = TextStyle(
                    fontSize = 14.sp,
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
                text = className,
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 24.sp,
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
    viewModel: ChildViewModel = viewModel()
){
    val (childName, setChildName) = remember {
        mutableStateOf("")
    }
    var isDialogVisible by remember { mutableStateOf(false) }

    if(isDialogVisible){
        ChildDialog(
            showDialog = isDialogVisible,
            onDismiss = { isDialogVisible = false },
            onConfirm = { configText ->
                // Handle dialog confirmation here
                // configText contains the user input
                isDialogVisible = false},
            childName = childName,
            setChildName = {setChildName(it)}
        )
    }

    Box(modifier = modifier   //카드 하얀 배경
//        .height(103.dp)
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
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier= Modifier
                .width(28.dp)
                .align(Alignment.CenterVertically)
                .height(32.dp),
                painter = painterResource(id = R.drawable.child_icon),
                contentDescription = "null",
                contentScale = ContentScale.Fit)

            Text(modifier = Modifier
                .width(53.dp)
                .height(32.dp)
                .padding(top = 2.dp),
                text = "Child",
                style = TextStyle(
                    fontSize = 14.sp,
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
                text = childName,
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF0047B3),
                    letterSpacing = 0.28.sp,
                    textAlign = TextAlign.Center)
            )




        }
    }

}

