package inu.thebite.tory.screens.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import kotlinx.coroutines.selects.select
import java.nio.file.Files.delete

@Composable
fun SettingScreen(
    centerViewModel: CenterViewModel,
    childClassViewModel: ChildClassViewModel,
    childInfoViewModel: ChildInfoViewModel
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        //센터
        SettingItemRow("센터", listOf("토리"))
        //반
        SettingItemRow("반", listOf("월수금(오전)", "월수금(오후)", "화목(오전)", "화목(오후)"))
        //아이
        SettingItemRow("아이" , listOf("김토리", "김토릐", "안토리", "안토릐"))

    }
}

@Composable
fun SettingItemRow(
    settingType : String,
    settingList : List<String>
){
    val rowHeight = LocalConfiguration.current.screenHeightDp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dp(rowHeight / 3.0f))
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
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp),
                    text = settingType,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
                OutlinedButton(
                    modifier = Modifier
                        .padding(end = 10.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.secondary),
                    onClick = {

                    }
                ){
                    Text(
                        text = "추가",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }
            }
            Divider(
                thickness = 2.dp, color = MaterialTheme.colorScheme.primary
            )
            LazyRow{
                items(settingList){settingName ->
                    Card(
                        modifier = Modifier
                            .padding(14.dp)
                            .widthIn(min = 80.dp)
                            .height(70.dp)
                            .border(width = 2.dp, color = MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp))
                            .clickable {

                            },

                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Row(modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = settingName,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                            Icon(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable {

                                    },
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,

                                )
                        }
                    }
                }

            }

        }
    }
}
