package inu.thebite.tory.screens.notice.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.ui.theme.fontFamily_Inter

@Composable
fun NoticeItemTopBar(
    lto: LtoResponse,
    gradient: Brush,
    expandedState: MutableState<Boolean>,

) {

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState.value) 180f else 0f, label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .fillMaxHeight()
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000),
                    clip = false
                )
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 10.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = gradient,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .fillMaxHeight()
                    .weight(0.22f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "LTO",
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 24.sp,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFFFFFF),
                        letterSpacing = 0.24.sp,
                    )

                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.56f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = lto.name,
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 24.sp,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),

                        letterSpacing = 0.24.sp,
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.22f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Drop-Down Arrow",
                    modifier = Modifier
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            expandedState.value = !expandedState.value
                        }
                        .rotate(rotationState)
                        .graphicsLayer(alpha = 0.99f)
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(gradient, blendMode = BlendMode.SrcAtop)
                            }
                        }
                        .size(40.dp)
                )
            }
        }
    }
}