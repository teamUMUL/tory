package inu.thebite.tory.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun ChainCard(){
    var isDialogVisible by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(120.dp)
            .padding(8.dp)
            .background(
                color = Color(0xFF59B791),
                shape = RoundedCornerShape(size = 20.dp)
            ),


        ) {


        Column(modifier = Modifier
            .padding(top = 5.dp)
            .width(250.dp)
            .height(92.dp)
            .clickable { isDialogVisible = true }
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),

            ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .height(70.dp)
                    .padding(top = 10.dp, start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Chain",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.size(80.dp))
                Text(
                    text = "2",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center)
                )
                ConfigDialog(
                    showDialog = isDialogVisible,
                    onDismiss = { isDialogVisible = false },
                    onConfirm = { configText ->
                        // Handle dialog confirmation here
                        // configText contains the user input
                        isDialogVisible = false})
            }
        }
    }
}
@Composable
fun ClassCard(){
    var isDialogVisible by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(120.dp)
            .padding(8.dp)
            .background(
                color = Color(0xFF59B791),
                shape = RoundedCornerShape(size = 20.dp)
            ),


        ) {


        Column(modifier = Modifier
            .padding(top = 5.dp)
            .width(250.dp)
            .clickable { isDialogVisible=true }
            .height(92.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),

            ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .height(70.dp)
                    .padding(top = 10.dp, start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Class",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.size(80.dp))
                Text(
                    text = "2",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center)
                )
                ConfigDialog(
                    showDialog = isDialogVisible,
                    onDismiss = { isDialogVisible = false },
                    onConfirm = { configText ->
                        // Handle dialog confirmation here
                        // configText contains the user input
                        isDialogVisible = false})
            }
        }
    }
}
@Composable
fun ChildrenCard(){
    var isDialogVisible by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(120.dp)

            .padding(8.dp)
            .background(
                color = Color(0xFF59B791),
                shape = RoundedCornerShape(size = 20.dp)
            ),


        ) {


        Column(modifier = Modifier
            .padding(top = 5.dp)
            .width(250.dp)
            .height(92.dp)
            .clickable { isDialogVisible = true }
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),

            ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .height(70.dp)
                    .padding(top = 10.dp, start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Children",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.size(40.dp))
                Text(
                    text = "2",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center)
                )

                ConfigDialog(
                    showDialog = isDialogVisible,
                    onDismiss = { isDialogVisible = false },
                    onConfirm = { configText ->
                        // Handle dialog confirmation here
                        // configText contains the user input
                        isDialogVisible = false})
            }
        }
    }
}
@Composable
fun ConfigDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            content = {
                var configText by remember { mutableStateOf("") }

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Configure the box",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    BasicTextField(
                        value = configText,
                        onValueChange = { configText = it },
                        textStyle = TextStyle(fontSize = 16.sp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { onDismiss() },
                            modifier = Modifier
                                .height(50.dp)
                                .weight(1f)
                        ) {
                            Text(text = "Cancel")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = {
                                onConfirm(configText)
                                onDismiss()
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .weight(1f)
                        ) {
                            Text(text = "Confirm")
                        }
                    }
                }
            }
        )
    }
}
