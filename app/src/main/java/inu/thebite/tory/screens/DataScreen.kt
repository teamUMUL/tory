@file:Suppress("NAME_SHADOWING")

package inu.thebite.tory.screens

import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import inu.thebite.tory.R
import kotlinx.coroutines.selects.select

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen (
    ltoViewModel: LTOViewModel = viewModel(),
    stoViewModel: STOViewModel = viewModel()
){
    val scrollState = rememberScrollState()
    val (addLTOItem, setAddLTOItem) = rememberSaveable {
        mutableStateOf(false)
    }
    val (addSTOItem, setAddSTOItem) = rememberSaveable {
        mutableStateOf(false)
    }
    val (selectedLTO, setSelectedLTO) = rememberSaveable {
        mutableStateOf("")
    }
    val (selectedLTOIndex, setSelectedLTOIndex) = rememberSaveable {
        mutableStateOf(0)
    }
    var inputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var isGraphSelected by rememberSaveable {
        mutableStateOf(false)
    }
    var progressState by rememberSaveable {
        mutableStateOf(-1)
    }
    var selectedDevIndex by rememberSaveable {
        mutableStateOf(0)
    }

    var itemList by remember { mutableStateOf(ltoViewModel.getLTOWithOneData(selectedDevIndex)) }

    val developZoneItems = listOf<String>(
        "1. 학습준비",
        "2. 매칭",
        "3. 동작모방",
        "4. 언어모방",
        "5. 변별",
        "6. 지시따라하기",
        "7. 요구하기",
        "8. 명명하기",
        "9. 인트라",
        "10. 가나다"
    )
    val detailList = listOf<String>(
        "진행중",
        "중지",
        "완료"
    )
    var detailListIndex by remember {
        mutableStateOf(-1)
    }
    val selectedDevItem = rememberSaveable{ mutableStateOf<String?>("1. 학습준비") }

    val cornerRadius = 8.dp

    if (addLTOItem){
        Dialog(
            onDismissRequest = {setAddLTOItem(false)},
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 16.dp
                    )
            ){
                TextField(
                    value = inputValue,
                    onValueChange = {
                        inputValue = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = { Text(text = "Enter your LTO") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    ),
                    textStyle = TextStyle(
                        color = Color.Black, fontSize = TextUnit.Unspecified,
                        fontFamily = FontFamily.SansSerif
                    ),
                    maxLines = 2,
                    singleLine = false
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        ltoViewModel.addOrUpdateLTO(selectedDevIndex, inputValue.text, -1)
                        setAddLTOItem(false)
                        inputValue = TextFieldValue("")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ){
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, Modifier.size(40.dp))
                }
            }
        }
    }
    if (addSTOItem){
        Dialog(
            onDismissRequest = {setAddSTOItem(false)},
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 16.dp
                    )
            ){
                TextField(
                    value = inputValue,
                    onValueChange = {
                        inputValue = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = { Text(text = "Enter your STO") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    ),
                    textStyle = TextStyle(
                        color = Color.Black, fontSize = TextUnit.Unspecified,
                        fontFamily = FontFamily.SansSerif
                    ),
                    maxLines = 2,
                    singleLine = false
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        stoViewModel.addOrUpdateLTO(selectedDevIndex, inputValue.text, -1)
                        setAddSTOItem(false)
                        inputValue = TextFieldValue("")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ){
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, Modifier.size(40.dp))
                }
            }
        }
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
    ){
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 2.dp)
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
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
                        text = "발달영역",
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
            LazyRow(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(developZoneItems){ developZoneItem ->
                    LazyColumnItem(item = developZoneItem, selectedDevItem,
                        select = {
                        selectedDevIndex = developZoneItems.indexOf(it)
                        setSelectedLTO("")
                    })
                }
            }
        }
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 2.dp)
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
                        text = "LTO",
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
            Box(modifier = Modifier
                .width(100.dp)
                .fillMaxHeight()
                .padding(5.dp)
                .clickable {
                    setAddLTOItem(true)
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
            LazyRow(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(ltoViewModel.getLTOWithOneData(selectedDevIndex)){ ltoItem ->
                    val (description, progressState) = ltoItem.split(": ")
                    LazyColumnItemCanDelete(
                        item = description,
                        selectedLTO,
                        progressState.toInt(),
                        delete = {
                            ltoViewModel.removeLTO(selectedDevIndex, it)
                            setSelectedLTO("")
                        },
                        select = {
                            setSelectedLTO(it)
                            setSelectedLTOIndex(ltoViewModel.getLTO(selectedDevIndex).first.indexOf(it))
                            detailListIndex = progressState.toInt()
                        }
                    )
                }
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(5.dp)
            .background(Color.Transparent)
            .border(4.dp, MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp)),
        ){
            Box(modifier = Modifier
                .width(100.dp)
                .fillMaxHeight()
                .padding(5.dp),
                contentAlignment = Alignment.Center
            ){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Details",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Box(modifier = Modifier
                .fillMaxSize(),
            ){
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier
                        .width(500.dp)
                        .fillMaxHeight()
                    ){
                        Row(modifier = Modifier
                            .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp),
                                text = selectedLTO,
                                fontSize = 35.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            if(selectedLTO != ""){
                                OutlinedButton(
                                    modifier = Modifier
                                        .size(40.dp),
                                    border = BorderStroke(2.dp, Color.Black),
                                    shape = RoundedCornerShape(5.dp),
                                    onClick = {
                                        isGraphSelected = !isGraphSelected
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if(isGraphSelected) MaterialTheme.colorScheme.secondary else Color.Transparent
                                    ),
                                    contentPadding = PaddingValues(2.dp)
                                ){
                                    Icon(
                                        modifier = Modifier
                                            .size(35.dp),
                                        painter = painterResource(id = R.drawable.icon_chart_inner),
                                        contentDescription = null,
                                        tint = Color.Black)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                OutlinedButton(
                                    modifier = Modifier
                                        .size(40.dp),
                                    border = BorderStroke(2.dp, Color.Black),
                                    shape = RoundedCornerShape(5.dp),
                                    onClick = {

                                    },
                                    contentPadding = PaddingValues(2.dp)
                                ){
                                    Icon(
                                        modifier = Modifier
                                            .size(35.dp),
                                        painter = painterResource(id = R.drawable.icon_edit),
                                        contentDescription = null,
                                        tint = Color.Black)
                                }
                            }
                        }
                    }
                    if(selectedLTO != ""){
                        Box(modifier = Modifier
                            .width(300.dp)
                            .fillMaxHeight()
                        ){
                            Row(modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ){
                                detailList.forEachIndexed { index, item ->
                                    OutlinedButton(
                                        onClick = {
                                            detailListIndex = index
                                            progressState = index
                                            ltoViewModel.addOrUpdateLTO(selectedDevIndex, selectedLTO, index)
                                        },
                                        shape =  when (index) {
                                            // left outer button
                                            0 -> RoundedCornerShape(topStart = cornerRadius, topEnd = 0.dp, bottomStart = cornerRadius, bottomEnd = 0.dp)
                                            // right outer button
                                            detailList.size - 1 -> RoundedCornerShape(topStart = 0.dp, topEnd = cornerRadius, bottomStart = 0.dp, bottomEnd = cornerRadius)
                                            // middle button
                                            else -> RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
                                        },
                                        border = BorderStroke(1.dp, if(detailListIndex == index) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(alpha = 0.75f)),
                                        modifier = when (index) {
                                            0 ->
                                                Modifier
                                                    .offset(0.dp, 0.dp)
                                                    .zIndex(if (detailListIndex == index) 1f else 0f)
                                            else ->
                                                Modifier
                                                    .offset((-1 * index).dp, 0.dp)
                                                    .zIndex(if (detailListIndex == index) 1f else 0f)
                                        },
                                        colors =
                                        if(detailListIndex == index){
                                            ButtonDefaults.outlinedButtonColors(
                                                containerColor =
                                                when (index) {
                                                    0 -> {
                                                        Color.Blue
                                                    }
                                                    1 -> {
                                                        Color.Green
                                                    }
                                                    else -> {
                                                        Color.Red
                                                    }
                                                },
                                                contentColor = Color.White
                                            )
                                        } else {
                                            ButtonDefaults.outlinedButtonColors(
                                                containerColor =
                                                when (index) {
                                                    0 -> {
                                                        Color.Blue.copy(alpha = 0.2f)
                                                    }
                                                    1 -> {
                                                        Color.Green.copy(alpha = 0.2f)
                                                    }
                                                    else -> {
                                                        Color.Red.copy(alpha = 0.2f)
                                                    }
                                                },
                                                contentColor = Color.Black
                                            )
                                        }

                                    ){
                                        Text(
                                            text = detailList[index]
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //STO ITEM -----------------------------------------------------------------------------
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
            LazyRow(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(stoViewModel.getLTOWithOneData(selectedDevIndex)){ ltoItem ->
                    val (description, progressState) = ltoItem.split(": ")
                    LazyColumnItemCanDelete(
                        item = description,
                        selectedLTO,
                        progressState.toInt(),
                        delete = {
                            stoViewModel.removeLTO(selectedDevIndex, it)
                            setSelectedLTO("")
                        },
                        select = {
                            setSelectedLTO(it)
                            setSelectedLTOIndex(stoViewModel.getLTO(selectedDevIndex).first.indexOf(it))
                            detailListIndex = progressState.toInt()
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun LazyColumnItem(item : String, selectedItem: MutableState<String?>,  select:(String) -> Unit){
    val isSelected = selectedItem.value == item
    Card(
        modifier = Modifier
            .padding(14.dp)
            .widthIn(min = 80.dp)
            .height(70.dp)
            .clickable {
                selectedItem.value = if (isSelected) selectedItem.value else item
                select(item)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = if(isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary)
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = item,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

@Composable
fun LazyColumnItemCanDelete(item: String, selectedItem: String, progressState:Int, delete: (String) -> Unit, select:(String) -> Unit){
    var _selectedItem = selectedItem
    val isSelected = _selectedItem == item
    Card(
        modifier = Modifier
            .padding(14.dp)
            .widthIn(min = 80.dp)
            .height(70.dp)
            .clickable {
                _selectedItem = if (isSelected) _selectedItem else item
                select(item)
            },

        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            if(progressState == 0){
                if(isSelected) Color.Blue.copy(alpha = 0.75f) else Color.Blue.copy(alpha = 0.2f)
            }
            else if(progressState == 1){
                if(isSelected) Color.Green.copy(alpha = 0.75f) else Color.Green.copy(alpha = 0.2f)
            }
            else if(progressState == 2){
                if(isSelected) Color.Red.copy(alpha = 0.75f) else Color.Red.copy(alpha = 0.2f)
            }
            else{
                if(isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
            }
        )
    ) {
        Row(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = item,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            if(isSelected){
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            delete(item)
                        },
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,

                )
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddLTOItemDialog(
    inputValue: TextFieldValue,
    setAddLTOItem: (Boolean) -> Unit,
    ltoViewModel: LTOViewModel,
    selectedDevIndex: Int
) {
    // AddLTOItemDialog 내용
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddSTOItemDialog(
    inputValue: TextFieldValue,
    setAddSTOItem: (Boolean) -> Unit,
    stoViewModel: STOViewModel,
    selectedDevIndex: Int
) {
    // AddSTOItemDialog 내용
}

@Composable
private fun DevelopZoneRow(developZoneItems: List<String>, selectedDevIndex: Int, setSelectedLTO: (String) -> Unit) {
    // DevelopZoneRow 내용
}

@Composable
private fun LTOItemsRow(
    ltoViewModel: LTOViewModel,
    selectedDevIndex: Int,
    setSelectedLTO: (String) -> Unit,
    detailListIndex: Int,
    setDetailListIndex: (Int) -> Unit
) {
    // LTOItemsRow 내용
}

@Composable
private fun DetailsRow(
    selectedLTO: String,
    isGraphSelected: Boolean,
    progressState: Int,
    setGraphSelected: (Boolean) -> Unit,
    setDetailListIndex: (Int) -> Unit,
    ltoViewModel: LTOViewModel,
    selectedDevIndex: Int
) {
    // DetailsRow 내용
}

@Composable
private fun STOItemsRow(
    stoViewModel: STOViewModel,
    selectedDevIndex: Int,
    setSelectedLTO: (String) -> Unit,
    detailListIndex: Int,
    setDetailListIndex: (Int) -> Unit
) {
    // STOItemsRow 내용
}
