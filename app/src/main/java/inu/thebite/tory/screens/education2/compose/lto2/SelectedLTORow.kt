package inu.thebite.tory.screens.education2.compose.lto2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.ui.theme.fontFamily_Lato

@Composable
fun SelectedLTORow(
    modifier: Modifier = Modifier,
    allLTOs: List<LtoResponse>?,
    selectedLTO: LtoResponse?,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        selectedLTO?.let { selectedLTO ->
            Text(
                text = selectedLTO.name,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = fontFamily_Lato,
                    fontWeight = FontWeight(900),
                    color = Color(0xFF1D1C1D),
                ),
                modifier = Modifier
                    .padding(horizontal = 15.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            LTOButtons(
                selectedLTO = selectedLTO,
                ltoViewModel = ltoViewModel,
                stoViewModel = stoViewModel
            )
        }

    }
}
