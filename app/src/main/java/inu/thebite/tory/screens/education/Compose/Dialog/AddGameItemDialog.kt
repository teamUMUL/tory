package inu.thebite.tory.screens.education.Compose.Dialog

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils.split
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import inu.thebite.tory.database.STO.STOEntity
import inu.thebite.tory.screens.education.GameViewModel
import inu.thebite.tory.screens.education.STOViewModel
import inu.thebite.tory.screens.education.getResourceIdByName
import inu.thebite.tory.screens.game.GameItem


@Composable
fun AddSameGameItemsDialog(
    context : Context,
    selectedSTO : STOEntity,
    setAddGameItem : (Boolean) -> Unit,
    setMainItem : (String) -> Unit,
    stoViewModel : STOViewModel,
){
    val selectedGameItems by remember {
        mutableStateOf(selectedSTO.gameItems.toMutableList())
    }
    val selectedIdxMap = remember {
        mutableStateMapOf<String, Int>()
    }
    for (selectedGameItem in selectedSTO!!.gameItems) {
        val parts = selectedGameItem.split("_")
        if (parts.size == 2) {
            val category = parts[0]
            val index = parts[1].toIntOrNull()
            if (index != null) {
                // 카테고리와 인덱스를 selectedIdxMap에 추가
                selectedIdxMap[category] = index-1
            }
        }
    }
    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = {
            setAddGameItem(false)
        }
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)

            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier
                            .padding(start = 20.dp),
                        text = "교육준비",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        modifier = Modifier
                            .padding(end = 20.dp),
                        onClick ={
                            setAddGameItem(false)
                        }
                    ){
                        Icon(
                            modifier = Modifier
                                .size(40.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                ) {
                    items(
                        listOf(
                            "spoon",
                            "cup",
                            "ball",
                            "block",
                            "clock",
                            "colorpencil",
                            "doll",
                            "scissor",
                            "socks",
                            "toothbrush"
                        )
                    ) { GameItemCategory ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = GameItemCategory,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                items(3){ i ->
                                    val i = i + 1
                                    val imageName = "${GameItemCategory}_${i}"
                                    val imageResource = getResourceIdByName(imageName, context)
                                    val isSelected = i - 1 == selectedIdxMap.getOrDefault(GameItemCategory, -1)
                                    Image(
                                        modifier = Modifier
                                            .weight(1.0f)
                                            .padding(10.dp)
                                            .clickable {
                                                if (isSelected) {
                                                    selectedGameItems.remove(imageName)
                                                    selectedIdxMap[GameItemCategory] = -1
                                                } else {
                                                    selectedGameItems.removeAll {
                                                        it.startsWith(
                                                            GameItemCategory
                                                        )
                                                    }
                                                    selectedGameItems.add(imageName)
                                                    selectedIdxMap[GameItemCategory] = i - 1
                                                }
//
//                                                selectedSTO.gameItems = selectedGameItems
//                                                stoViewModel.updateSTO(selectedSTO)
                                            },
                                        painter = painterResource(id = imageResource),
                                        contentDescription = null,
                                        alpha =  if (isSelected) 1.0f else 0.5f
                                    )
                                }

                            }
                        }
                    }

                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(10.dp),
                    onClick = {
                        Log.e("선택된 게임들", selectedGameItems.toList().toString())
                        selectedSTO.gameItems = selectedGameItems
                        setMainItem("")
                        stoViewModel.updateSTO(selectedSTO)
                        setAddGameItem(false)
                    },
                    shape = RoundedCornerShape(12.dp)
                ){
                    Text(
                        text = "카드 준비",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun AddGeneralGameItem(
    context : Context,
    selectedSTO : STOEntity,
    setAddGameItem : (Boolean) -> Unit,
    setMainItem : (String) -> Unit,
    stoViewModel : STOViewModel,
){
    val selectedGameItems by remember {
        mutableStateOf(selectedSTO.gameItems.toMutableList())
    }
    val selectedIdxMap = remember {
        mutableStateMapOf<String, Boolean>()
    }
    for (selectedGameItem in selectedSTO!!.gameItems) {
        val parts = selectedGameItem.split("_")
        if (parts.size == 2) {
            val category = parts[0]
            selectedIdxMap[category] = true
        }
    }

    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = {
            setAddGameItem(false)
        }
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)

            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier
                            .padding(start = 20.dp),
                        text = "교육준비",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        modifier = Modifier
                            .padding(end = 20.dp),
                        onClick ={
                            setAddGameItem(false)
                        }
                    ){
                        Icon(
                            modifier = Modifier
                                .size(40.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                ) {
                    items(
                        listOf(
                            "spoon",
                            "cup",
                            "ball",
                            "block",
                            "clock",
                            "colorpencil",
                            "doll",
                            "scissor",
                            "socks",
                            "toothbrush"
                        )
                    ) { GameItemCategory ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = GameItemCategory,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                items(3){ i ->
                                    val i = i + 1
                                    val imageName = "${GameItemCategory}_${i}"
                                    val imageResource = getResourceIdByName(imageName, context)
                                    val isSelected = selectedIdxMap[GameItemCategory] ?: false
                                    Image(
                                        modifier = Modifier
                                            .weight(1.0f)
                                            .padding(10.dp)
                                            .clickable {
                                                if (isSelected) {
                                                    selectedGameItems.removeIf { it.contains(GameItemCategory) }
                                                    selectedIdxMap[GameItemCategory] = false
                                                } else {
                                                    selectedGameItems.add(imageName)
                                                    selectedIdxMap[GameItemCategory] = true
                                                }
//                                                Log.e("추가된 게임아이템", selectedGameCategory.toString())
//                                                selectedSTO.gameItems = selectedGameCategory
//                                                stoViewModel.updateSTO(selectedSTO)
                                            },
                                        painter = painterResource(id = imageResource),
                                        contentDescription = null,
                                        alpha =  if (isSelected) 1.0f else 0.5f
                                    )
                                }

                            }
                        }
                    }

                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(10.dp),
                    onClick = {

                        selectedSTO.gameItems = selectedGameItems
                        setMainItem("")
                        stoViewModel.updateSTO(selectedSTO)
                        setAddGameItem(false)
                    },
                    shape = RoundedCornerShape(12.dp)
                ){
                    Text(
                        text = "카드 준비",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

fun extractItemsFromList(inputList: List<String>): List<String> {
    return inputList.mapNotNull { item ->
        val parts = item.split("_")
        if (parts.isNotEmpty()) {
            parts[0]
        } else {
            null
        }
    }
}