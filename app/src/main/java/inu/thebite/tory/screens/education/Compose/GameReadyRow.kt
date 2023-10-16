package inu.thebite.tory.screens.education.Compose

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.R
import inu.thebite.tory.database.STO.STOEntity
import inu.thebite.tory.screens.education.getResourceIdByName
import inu.thebite.tory.screens.game.DragAndDropViewModel
import inu.thebite.tory.screens.game.GameItem


@Composable
fun gameReadyRow(
    context : Context,
    dragAndDropViewModel : DragAndDropViewModel,
    selectedSTO : STOEntity,
    mainGameItem : String,
    setAddGameItem : (Boolean) -> Unit,
    setMainGameItem : (String) -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
    ) {
        if(selectedSTO.isNotNull()){
            Box(modifier = Modifier
                .width(100.dp)
                .fillMaxHeight()
                .padding(5.dp)
                .clickable {
                    setAddGameItem(true)

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
            ) {
                items(selectedSTO.gameItems ?: emptyList()) { selectedGameItem ->
                    val imageResource = getResourceIdByName(selectedGameItem, context)
                    val isSelected = selectedGameItem == mainGameItem

                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
                            .clickable {
                                // Update the selected item when clicked
                                if (isSelected) {
                                    setMainGameItem("")
                                    dragAndDropViewModel.clearMainItem()
                                } else {
                                    setMainGameItem(selectedGameItem)
                                    dragAndDropViewModel.setMainItem(
                                        GameItem(
                                            name = selectedGameItem,
                                            image = imageResource
                                        )
                                    )

                                }
                            },
                        painter = painterResource(id = imageResource),
                        contentDescription = null,
                        alpha = if (isSelected) 1.0f else 0.5f
                    )
                }
            }
        }
    }
}

