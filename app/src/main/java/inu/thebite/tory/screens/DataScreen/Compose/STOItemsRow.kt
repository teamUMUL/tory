package inu.thebite.tory.screens.DataScreen.Compose

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.R
import inu.thebite.tory.screens.DataScreen.STODetailViewModel
import inu.thebite.tory.screens.DataScreen.STOViewModel


@SuppressLint("MutableCollectionMutableState")
@Composable
fun STOItemsRow(
    stoViewModel: STOViewModel,
    selectedDevIndex: Int,
    selectedLTO: String,
    selectedSTO: String,
    selectedLTOIndex: Int,
    setAddSTOItem: (Boolean) -> Unit,
    deleteSTOItem: (String) -> Unit,
    selectSTOItem: (it:String, progressState:Int) -> Unit
) {
    // STOItemsRow 내용

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(90.dp)
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
                    text = "STO",
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
        if(selectedLTO!=""){
            Box(modifier = Modifier
                .width(100.dp)
                .fillMaxHeight()
                .padding(5.dp)
                .clickable {
                    setAddSTOItem(true)
                },
                contentAlignment = Alignment.Center
            ){
                Column(modifier = Modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        modifier = Modifier
                            .size(80.dp),
                        painter = painterResource(id = R.drawable.icon_add_square_light),
                        contentDescription = null
                    )
                }

            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(selectedLTO != ""){
                items(stoViewModel.getSTOWithOneData(selectedLTOIndex, selectedDevIndex)){ ltoItem ->
                    val (description, progressState) = ltoItem.split(": ")
                    LazyColumnItemCanDelete(
                        item = description,
                        selectedItem = selectedSTO,
                        progressState = progressState.toInt(),

                        delete = {
                            deleteSTOItem(it)
                        },
                        select = {
                            selectSTOItem(it, progressState.toInt())
                        },
                        listOf(Color.Blue, Color.Green, Color.Red)
                    )
                }
            }

        }
    }
}
