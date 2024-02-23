package inu.thebite.tory.screens.education.compose.sidebar

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import es.dmoral.toasty.Toasty
import inu.thebite.tory.R
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.schedule.TodoViewModel
import inu.thebite.tory.screens.education.compose.dialog.dev.AddDEVDialog
import inu.thebite.tory.screens.education.compose.dialog.dev.UpdateDEVDialog
import inu.thebite.tory.screens.education.compose.dialog.lto.AddLTODialog
import inu.thebite.tory.screens.education.compose.dialog.lto.UpdateLTOItemDialog
import inu.thebite.tory.screens.education.compose.dialog.sto.AddSTODialog
import inu.thebite.tory.screens.education.compose.dialog.sto.UpdateSTODialog
import inu.thebite.tory.screens.education.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.teachingboard.dialog.RecentListDialog
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildSelectViewModel

var currentToast: Toast? = null
@Composable
fun Sidebar(
    childSelectViewModel: ChildSelectViewModel,
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel,
    todoViewModel: TodoViewModel
){
    val context = LocalContext.current
    val selectedChild by childSelectViewModel.selectedChildInfo.collectAsState()

    val selectedDEV by devViewModel.selectedDEV.collectAsState()

    val selectedLTO by ltoViewModel.selectedLTO.collectAsState()

    val selectedSTO by stoViewModel.selectedSTO.collectAsState()

    //    LaunchedEffect(Unit){
//        devViewModel.setSelectedDEV(
//            devEntity = DomainResponse(
//                id = 1L,
//                templateNum = 1,
//                type = "",
//                status = "",
//                name = "1. 매칭",
//                contents = "",
//                useYN = "",
//                delYN = "",
//                registerDate = ""
//            )
//        )
//        setAddLTODialog(true)
//    }

    //DEV 추가 다이얼로그
    val (addDEVDialog, setAddDEVDialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if(addDEVDialog){
        selectedChild?.let { selectedChild ->
            AddDEVDialog(
                context = context,
                selectedChild = selectedChild,
                devViewModel = devViewModel,
                setDEVDialog = {setAddDEVDialog(it)}
            )
        } ?: run {
            currentToast?.cancel()
            val newToast = Toasty.warning(context, "발달영역을 추가할 아이를 선택해주세요", Toast.LENGTH_SHORT, true)
            newToast.show()

            currentToast = newToast

            setAddDEVDialog(false)
        }
    }

    //DEV 수정 다이얼로그
    val (updateDEVDialog, setUpdateDEVDialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if(updateDEVDialog){
        selectedChild?.let { selectedChild ->
            selectedDEV?.let { selectedDEV ->
                UpdateDEVDialog(
                    context = context,
                    selectedChild = selectedChild,
                    devViewModel = devViewModel,
                    setDEVDialog = {setUpdateDEVDialog(it)},
                    selectedDEV = selectedDEV
                )
            } ?: run {
                currentToast?.cancel()

                val newToast = Toasty.warning(context, "수정할 발달영역을 선택해주세요", Toast.LENGTH_SHORT, true)
                newToast.show()

                currentToast = newToast

                setUpdateDEVDialog(false)
            }
        }
    }

    //LTO 추가 다이얼로그
    val (addLTODialog, setAddLTODialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if(addLTODialog){
        selectedChild?.let { selectedChild ->
            selectedDEV?.let {selectedDEV ->
                AddLTODialog(
                    context = context,
                    selectedChild = selectedChild,
                    setAddLTOItem = { setAddLTODialog(it) },
                    selectedDEV = selectedDEV,
                    ltoViewModel = ltoViewModel
                )
            } ?: run {
                currentToast?.cancel()

                val newToast = Toasty.warning(context, "LTO를 추가할 발달영역을 선택해주세요", Toast.LENGTH_SHORT, true)
                newToast.show()

                currentToast = newToast

                setAddLTODialog(false)
            }
        }
    }

    //LTO 수정 다이얼로그
    val (updateLTODialog, setUpdateLTODialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if (updateLTODialog) {
        selectedLTO?.let { selectedLTO ->
            UpdateLTOItemDialog(
                context = context,
                setUpdateLTOItem = { setUpdateLTODialog(it) },
                selectedLTO = selectedLTO,
                ltoViewModel = ltoViewModel
            )
        } ?: run {
            currentToast?.cancel()

            val newToast = Toasty.warning(context, "수정할 LTO를 선택해주세요", Toast.LENGTH_SHORT, true)
            newToast.show()

            currentToast = newToast

            setUpdateLTODialog(false)
        }

    }

    //STO 추가 다이얼로그
    val (addSTODialog, setAddSTODialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if (addSTODialog) {
        selectedLTO?.let { selectedLTO ->
            AddSTODialog(
                context = context,
                setAddSTOItem = { setAddSTODialog(it) },
                stoViewModel = stoViewModel,
                selectedLTO = selectedLTO
            )
        } ?: run {
            currentToast?.cancel()

            val newToast = Toasty.warning(context, "STO를 추가할LTO를 선택해주세요", Toast.LENGTH_SHORT, true)
            newToast.show()

            currentToast = newToast

            setAddSTODialog(false)
        }
    }

    val (updateSTODialog, setUpdateSTODialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if(updateSTODialog){
        selectedSTO?.let {selectedSTO ->
            UpdateSTODialog(
                context = context,
                stoViewModel = stoViewModel,
                selectedSTO = selectedSTO,
                setUpdateSTOItem = {setUpdateSTODialog(it)},
            )
        } ?: run {
            currentToast?.cancel()

            val newToast = Toasty.warning(context, "수정할 STO를 선택해주세요", Toast.LENGTH_SHORT, true)
            newToast.show()

            currentToast = newToast

            setUpdateSTODialog(false)
        }
    }

    val (recentTodoDialog, setRecentTodoDialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if  (recentTodoDialog){
        selectedChild?.let { RecentListDialog(setRecentListDialog = {setRecentTodoDialog(it)}, todoViewModel = todoViewModel, selectedChild = it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3)),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .padding(top = 30.dp),
            onClick = {
                setAddDEVDialog(true)
            }
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_dev_add), contentDescription = null, tint = Color(0xFF8E8E8E))
        }
        IconButton(
            onClick = {
                setUpdateDEVDialog(true)
            }
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_dev_update), contentDescription = null, tint = Color(0xFF8E8E8E))
        }
        IconButton(
            onClick = {
                setAddLTODialog(true)
            }
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_lto_add), contentDescription = null, tint = Color(0xFF8E8E8E))
        }
        IconButton(
            onClick = {
                setUpdateLTODialog(true)
            }
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_lto_update), contentDescription = null, tint = Color(0xFF8E8E8E))
        }
        IconButton(
            onClick = {
                setAddSTODialog(true)
            }
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_sto_add), contentDescription = null, tint = Color(0xFF8E8E8E))
        }
        IconButton(
            onClick = {
                setUpdateSTODialog(true)
            }
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_sto_update), contentDescription = null, tint = Color(0xFF8E8E8E))
        }
        IconButton(
            onClick = {
                setRecentTodoDialog(true)
            }
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_todo), contentDescription = null, tint = Color.Unspecified)
        }
    }

}