package inu.thebite.tory.screens.education2.compose.dialog.sto


import android.annotation.SuppressLint
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import inu.thebite.tory.R
import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun AddGeneralGameItemDialog(
    selectedSTO : StoResponse,
    setAddGameItem : (Boolean) -> Unit,
    stoViewModel : STOViewModel,
    imageViewModel: ImageViewModel
){
    val allCategories by imageViewModel.allCategories.collectAsState()

    val selectedGameItems by remember {
        mutableStateOf(selectedSTO.imageList.toMutableList())
    }
    val selectedIdxMap = remember {
        mutableStateMapOf<String, Boolean>()
    }
    for (selectedGameItem in selectedSTO.imageList) {
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
                    allCategories?.let {allCategories ->
                        items(allCategories) { category ->
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    modifier = Modifier.padding(start = 10.dp),
                                    text = category,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                LazyRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                ) {
                                    items(imageViewModel.getImagesByCategory(category)){ image ->
                                        val isSelected = selectedIdxMap[category] ?: false
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(image.url)
                                                .crossfade(true)
                                                .build(),
                                            placeholder = painterResource(id = R.drawable.icon_edit),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .weight(1.0f)
                                                .padding(10.dp)
                                                .clickable {
                                                    if (isSelected) {
                                                        selectedGameItems.removeIf { it.substringBefore("_") == category }
                                                        selectedIdxMap[category] = false
                                                    } else {
                                                        selectedGameItems.add(image.name)
                                                        selectedIdxMap[category] = true
                                                    }
                                                },
                                            alpha =  if (isSelected) 1.0f else 0.5f
                                        )
                                    }

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
                        val generalModeImageList = mutableListOf<String>()
                        selectedGameItems.forEach { selectedGameItem ->
                            val generalModeImageName = selectedGameItem.substringBefore("_")+"_1"
                            generalModeImageList.add(generalModeImageName)
                        }
                        stoViewModel.updateSTOImageList(selectedSTO, generalModeImageList)
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