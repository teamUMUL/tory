package inu.thebite.tory.screens.management

import android.text.TextPaint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.screens.management.compose.CenterColumn
import inu.thebite.tory.screens.management.compose.ChildClassRow
import inu.thebite.tory.screens.management.compose.ChildInfoRow
import inu.thebite.tory.screens.setting.dialog.AddChildInfoDialog
import inu.thebite.tory.screens.setting.viewmodel.CenterViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildClassViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildInfoViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import java.lang.Integer.min

@Composable
fun ManagementScreen(
    centerViewModel: CenterViewModel,
    childClassViewModel: ChildClassViewModel,
    childInfoViewModel: ChildInfoViewModel
) {
    val context = LocalContext.current

    val centers by centerViewModel.centers.collectAsState()
    val selectedCenter by centerViewModel.selectedCenter.collectAsState()
    val allCenters by centerViewModel.allCenters.collectAsState()

    val selectedChildClass by childClassViewModel.selectedChildClass.collectAsState()
    val allChildClasses by childClassViewModel.allChildClasses.collectAsState()

    val selectedChildInfo by childInfoViewModel.selectedChildInfo.collectAsState()
    val allChildInfos by childInfoViewModel.allChildInfos.collectAsState()



    LaunchedEffect(Unit) {
        centerViewModel.getAllCenters()
    }

    LaunchedEffect(selectedCenter, allChildClasses) {
        selectedCenter?.let {
            childClassViewModel.getAllChildClasses(
                it.id
            )
            childClassViewModel.clearSelectedChildClass()
//            childClassViewModel.clearAllChildClasses()

            childInfoViewModel.clearSelectedChildInfo()
//            childInfoViewModel.clearAllChildInfos()
        }
    }

    LaunchedEffect(selectedChildClass, allChildInfos) {
        selectedChildClass?.let { selectedChildClass ->
            childInfoViewModel.getAllChildInfos(
                selectedChildClass.id
            )
            childInfoViewModel.clearSelectedChildInfo()
        } ?: run {
            childInfoViewModel.clearAllChildInfos()
        }
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Divider(thickness = 1.dp, color = Color.LightGray)
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CenterColumn(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFF3F3F3)),
                centerViewModel = centerViewModel
            )
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp), color = Color.LightGray
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ChildClassRow(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color(0xFF0047B3),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp),
                    centerViewModel = centerViewModel,
                    childClassViewModel = childClassViewModel
                )

                ChildInfoRow(
                    modifier = Modifier
                        .weight(7f),
                    childClassViewModel = childClassViewModel,
                    childInfoViewModel = childInfoViewModel
                )
            }

        }
    }
}



