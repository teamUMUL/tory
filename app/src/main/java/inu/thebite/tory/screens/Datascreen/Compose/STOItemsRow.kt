package inu.thebite.tory.screens.Datascreen.Compose

import android.annotation.SuppressLint
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.R
import inu.thebite.tory.database.LTO.LTOEntity
import inu.thebite.tory.database.STO.STOEntity
import inu.thebite.tory.screens.Datascreen.STOViewModel


@SuppressLint("MutableCollectionMutableState")
@Composable
fun STOItemsRow(
    stoViewModel: STOViewModel,
    selectedLTO: LTOEntity?,
    selectedSTO: STOEntity?,
    stos: List<STOEntity>?,
    allSTOs: List<STOEntity>,
    setAddSTOItem: (Boolean) -> Unit,
    selectSTOItem: (it:String, progressState:Int) -> Unit,
    setSelectedSTOTryNum: (Int) -> Unit
) {
    // STOItemsRow 내용
    var selectedSTOName : String = ""
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
        if(selectedLTO.isNotNull()){
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
        selectedSTO?.let {
            selectedSTOName = it.stoName
        }
        LazyRow(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(selectedLTO.isNotNull()){
                Log.e("stos", stos.toString())
                items(stos ?: emptyList(), key = {it.stoId}){ stoItem ->
                    val description = stoItem.stoName
                    val progressState = stoItem.stoState
                    LazyColumnItemCanDelete(
                        item = description,
                        selectedItem = selectedSTOName ?: "",
                        progressState = progressState,

                        delete = {
                            stoViewModel.deleteSTO(stoItem)
                            stoViewModel.clearSelectedSTO()
                        },
                        select = {
                            setSelectedSTOTryNum(stoItem.stoTryNum)
                            selectSTOItem(it, progressState)
                            stoViewModel.setSelectedSTO(stoItem)
                        },
                        listOf(Color.Blue, Color.Green, Color.Red)
                    )
                }
            }

        }

    }
}

