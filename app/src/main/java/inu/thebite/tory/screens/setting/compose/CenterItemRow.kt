package inu.thebite.tory.screens.setting.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.retrofit.RetrofitApi
import inu.thebite.tory.screens.setting.viewmodel.CenterViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildClassViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildInfoViewModel

@Composable
fun CenterItemRow(
    settingType : String,
    allCenters : List<CenterResponse>,
    selectedCenter : CenterResponse?,
    centerViewModel: CenterViewModel,
    childClassViewModel : ChildClassViewModel,
    childInfoViewModel : ChildInfoViewModel,
    setAddCenterDialog : (Boolean) -> Unit,
    setUpdateCenterDialg : (Boolean) -> Unit
){
    val rowHeight = LocalConfiguration.current.screenHeightDp
    val service = RetrofitApi.apiService

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp, max = Dp(rowHeight / 3.0f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Divider(
                thickness = 2.dp, color = MaterialTheme.colorScheme.primary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        text = "$settingType : ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    )
                    selectedCenter?.let {
                        Text(
                            modifier = Modifier
                                .padding(start = 10.dp),
                            text = it.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    selectedCenter?.let {
                        OutlinedButton(
                            modifier = Modifier
                                .padding(end = 10.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.secondary),
                            onClick = {
                                setUpdateCenterDialg(true)
                            }
                        ){
                            Text(
                                text = "수정",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        }
                    }

                    selectedCenter?.let {
                        OutlinedButton(
                            modifier = Modifier
                                .padding(end = 10.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.secondary),
                            onClick = {
                                centerViewModel.deleteCenter(
                                    selectedCenter
                                )
                                centerViewModel.clearSelectedCenter()
                                childClassViewModel.clearSelectedChildClass()
                                childInfoViewModel.clearSelectedChildInfo()
                            }
                        ){
                            Text(
                                text = "삭제",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        }
                    }

                    OutlinedButton(
                        modifier = Modifier
                            .padding(end = 10.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.secondary),
                        onClick = {
                            setAddCenterDialog(true)
                        }
                    ){
                        Text(
                            text = "추가",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )
                    }

                }

            }
            Divider(
                thickness = 2.dp, color = MaterialTheme.colorScheme.primary
            )
            LazyRow{
                items(allCenters){center ->
                    Card(
                        modifier = Modifier
                            .padding(14.dp)
                            .widthIn(min = 80.dp)
                            .height(70.dp)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                if (selectedCenter == center) {
                                    centerViewModel.clearSelectedCenter()
                                } else {
                                    centerViewModel.setSelectedCenter(center)
                                }
                                childClassViewModel.clearSelectedChildClass()
                                childInfoViewModel.clearSelectedChildInfo()
                                childClassViewModel.getChildClassesByCenter(
                                    center
                                )
                            },
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if(selectedCenter == center) MaterialTheme.colorScheme.secondary  else Color.Transparent
                        )
                    ) {
                        Row(modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = center.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        }
                    }
                }

            }

        }
    }
}