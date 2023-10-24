//package inu.thebite.tory.screens.education.Compose
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import inu.thebite.tory.R
//import inu.thebite.tory.database.LTO.LTOEntity
//import inu.thebite.tory.screens.education.LTOViewModel
//import inu.thebite.tory.screens.education.STOViewModel
//
//
//@Composable
//fun LTOItemsRow(
//    ltoViewModel: LTOViewModel,
//    selectedDevIndex: Int,
//    selectedLTO: LTOEntity?,
//    ltos : List<LTOEntity>?,
//    stoViewModel: STOViewModel,
//    selectedChildName: String,
//    selectedChildClass: String,
//    setAddLTOItem: (Boolean) -> Unit,
//    selectLTOItem: (it:String,progressState: Int) -> Unit,
//) {
//    var selectedLTOName : String = ""
//    // LTOItemsRow 내용
//    Row(modifier = Modifier
//        .fillMaxWidth()
//        .height(90.dp)
//        .background(Color.Transparent)
//    ){
//        Box(modifier = Modifier
//            .width(100.dp)
//            .fillMaxHeight()
//            .padding(5.dp),
//            contentAlignment = Alignment.Center,
//        ){
//            Column(modifier = Modifier
//                .fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    modifier = Modifier.fillMaxWidth(),
//                    text = "LTO",
//                    textAlign = TextAlign.Center,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 22.sp
//                )
//                Text(
//                    modifier = Modifier.fillMaxWidth(),
//                    text = "Item",
//                    textAlign = TextAlign.Center,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 22.sp
//                )
//            }
//        }
//        Box(modifier = Modifier
//            .width(100.dp)
//            .fillMaxHeight()
//            .padding(5.dp)
//            .clickable {
//                setAddLTOItem(true)
//            },
//            contentAlignment = Alignment.Center
//        ){
//            Column(modifier = Modifier
//                .fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ){
//                Icon(
//                    modifier = Modifier
//                        .size(80.dp),
//                    painter = painterResource(id = R.drawable.icon_add_square_light),
//                    contentDescription = null
//                )
//            }
//
//        }
//
//        selectedLTO?.let {
//            selectedLTOName = it.ltoName
//        }
//        LazyRow(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            items(ltos ?: emptyList()){ ltoItem ->
//                LazyColumnItemCanDelete(
//                    item = ltoItem.ltoName,
//                    selectedLTOName,
//                    ltoItem.ltoState,
//                    delete = {
//                        stoViewModel.deleteSTOsByCriteria(selectedChildClass,selectedChildName,stoViewModel.developZoneItems[selectedDevIndex], it)
//                        ltoViewModel.clearSelectedLTO()
//                        ltoViewModel.deleteLTO(ltoItem)
//                    },
//                    select = {
//                        stoViewModel.clearSelectedSTO()
//                        ltoViewModel.setSelectedLTO(ltoItem)
//                        selectLTOItem(it, ltoItem.ltoState)
//                    },
//                    listOf(Color.Blue, Color.Red, Color.Green)
//                )
//            }
//        }
//    }
//}