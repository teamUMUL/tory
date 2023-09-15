package inu.thebite.tory.screens.DataScreen.Compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
