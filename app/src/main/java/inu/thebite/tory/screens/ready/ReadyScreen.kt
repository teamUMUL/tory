package inu.thebite.tory.screens.ready

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import inu.thebite.tory.R
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel

@Composable
fun ReadyScreen(
    imageViewModel: ImageViewModel
){
    val allImages by imageViewModel.allImages.collectAsState()

    LazyRow{
        allImages?.let {allImages ->
            items(allImages){image ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image.url)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.icon_edit),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(300.dp),
                )
            }
        }

    }
}