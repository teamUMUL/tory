package inu.thebite.tory.screens.education.compose.sto

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import inu.thebite.tory.R
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education.screen.clickableWithNoRipple
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel
import inu.thebite.tory.ui.theme.fontFamily_Lato


@Composable
fun CardSelector(
    modifier: Modifier = Modifier,
    imageViewModel: ImageViewModel,
    stoViewModel: STOViewModel,
    ltoViewModel: LTOViewModel,
    selectedSTO: StoResponse?,
    dragAndDropViewModel: DragAndDropViewModel
) {
    val mainItem by dragAndDropViewModel.mainItem.collectAsState()

    val (cardSelectDialog, setCardSelectDialog) = remember {
        mutableStateOf(false)
    }




    Column(
        modifier = modifier
            .padding(top = 10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .weight(1.5f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "사진 선택",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = fontFamily_Lato,
                    fontWeight = FontWeight(900),
                    color = Color(0xFF1D1C1D),

                    )
            )
            Image(
                painter = painterResource(id = R.drawable.icon_add_card),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickableWithNoRipple {
                        setCardSelectDialog(true)
                    },
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(8.5f)
                .background(Color(0xFFD1D1D1))

        ) {
            selectedSTO?.let { selectedSTO ->
                items(
                    imageViewModel.findImagesByNames(selectedSTO.imageList) ?: emptyList()
                ) { selectedImage ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(selectedImage.url)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.icon_edit),
                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
                            .clickable {
                                if (selectedImage == mainItem) {
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