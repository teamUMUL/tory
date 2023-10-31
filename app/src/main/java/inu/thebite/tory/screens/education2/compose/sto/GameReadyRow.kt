package inu.thebite.tory.screens.education2.compose.sto

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.yml.charts.common.extensions.isNotNull
import coil.compose.AsyncImage
import coil.request.ImageRequest
import inu.thebite.tory.R
import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel


@Composable
fun GameReadyRow(
//    dragAndDropViewModel : DragAndDropViewModel,
    mainItem : ImageResponse?,
    selectedSTO : StoResponse,
    setAddGameItem : (Boolean) -> Unit,
    dragAndDropViewModel: DragAndDropViewModel,
    imageViewModel: ImageViewModel
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
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
                items(imageViewModel.findImagesByNames(selectedSTO.imageList)) { selectedImage ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(selectedImage.url)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.icon_edit),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
                            .clickable {
                                if(selectedImage == mainItem){
                                    dragAndDropViewModel.clearMainItem()
                                } else {
                                    dragAndDropViewModel.setMainItem(selectedImage)
                                }

                            },
                        alpha = if (selectedImage == mainItem) 1.0f else 0.5f
                    )
                }
            }
        }
    }
}