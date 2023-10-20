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
    Card(
        modifier = Modifier     //카드 그림자
            .width(311.dp)
            .height(105.dp)
            .background(color = Color(0xFFC7C7C7), shape = RoundedCornerShape(size = 10.dp))
            .clickable { isDialogVisible = true }

        ) {



        Box(modifier = Modifier   //카드 하얀 배경
            .width(309.dp)
            .height(103.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),

            ) {

            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .width(200.dp)
                    .height(102.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(modifier= Modifier
                    .padding(16.dp)
                    .width(32.dp)
                    .align(Alignment.CenterVertically)
                    .height(36.dp),
                    painter = painterResource(id = R.drawable.center_icon),
                    contentDescription = "null",
                    contentScale = ContentScale.Crop)

                Column (modifier =Modifier
                    .padding(start = 40.dp, top = 20.dp)
                    .width(150.dp)
                    .height(100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        modifier = Modifier
                            .width(140.dp)
                            .height(40.dp),
                        text = centerName,
                        style = TextStyle(
                            fontSize = 26.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF0047B3),
                            letterSpacing = 0.28.sp,
                            textAlign = TextAlign.Center)
                        )
                    Text(modifier = Modifier
                        .width(53.dp)
                        .height(30.dp),
                        text = "Center",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFA6ACBE),
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.14.sp,
                        )
                    )
                }




            }
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
    Card(
        modifier = Modifier     //카드 그림자
            .width(311.dp)
            .height(105.dp)
            .background(color = Color(0xFFC7C7C7), shape = RoundedCornerShape(size = 10.dp))
            .clickable { isDialogVisible = true }

    ) {



        Box(modifier = Modifier   //카드 하얀 배경
            .width(309.dp)
            .height(103.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),

            ) {

            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .width(250.dp)
                    .height(102.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(modifier= Modifier
                    .padding(16.dp)
                    .width(32.dp)
                    .align(Alignment.CenterVertically)
                    .height(36.dp),
                    painter = painterResource(id = R.drawable.class_icon),
                    contentDescription = "null",
                    contentScale = ContentScale.Fit)

                Column (modifier =Modifier
                    .padding(top = 20.dp)
                    .width(250.dp)
                    .height(100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        modifier = Modifier
                            .width(250.dp)
                            .height(40.dp),
                        text = className,
                        style = TextStyle(
                            fontSize = 26.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF6100FF),
                            letterSpacing = 0.28.sp,
                            textAlign = TextAlign.Center)
                    )
                    Text(modifier = Modifier
                        .width(53.dp)
                        .height(30.dp),
                        text = "Class",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFA6ACBE),
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.14.sp,
                        )
                    )
                }




            }
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
    Card(
        modifier = Modifier     //카드 그림자
            .width(311.dp)
            .height(105.dp)
            .background(color = Color(0xFFC7C7C7), shape = RoundedCornerShape(size = 10.dp))
            .clickable { isDialogVisible = true }

    ) {



        Box(modifier = Modifier   //카드 하얀 배경
            .width(309.dp)
            .height(103.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),

            ) {

            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .width(250.dp)
                    .height(102.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(modifier= Modifier
                    .padding(16.dp)
                    .width(32.dp)
                    .align(Alignment.CenterVertically)
                    .height(36.dp),
                    painter = painterResource(id = R.drawable.child_icon),
                    contentDescription = "null",
                    contentScale = ContentScale.Fit)

                Column (modifier =Modifier
                    .padding(top = 20.dp)
                    .width(250.dp)
                    .height(100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        modifier = Modifier
                            .width(250.dp)
                            .height(40.dp),
                        text = childName,
                        style = TextStyle(
                            fontSize = 26.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF009733),
                            letterSpacing = 0.28.sp,
                            textAlign = TextAlign.Center)
                    )
                    Text(modifier = Modifier
                        .width(53.dp)
                        .height(30.dp),
                        text = "Child",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFA6ACBE),
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.14.sp,
                        )
                    )
                }




            }
        }
    }
}

