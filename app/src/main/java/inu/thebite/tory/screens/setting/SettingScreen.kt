package inu.thebite.tory.screens.setting

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.CenterSelectViewModel
import inu.thebite.tory.ChildClassSelectViewModel
import inu.thebite.tory.ChildSelectViewModel
import inu.thebite.tory.screens.setting.compose.CenterItemRow
import inu.thebite.tory.screens.setting.compose.ChildClassItemRow
import inu.thebite.tory.screens.setting.compose.ChildInfoItemRow
import inu.thebite.tory.screens.setting.dialog.AddCenterDialog
import inu.thebite.tory.screens.setting.dialog.AddChildClassDialog
import inu.thebite.tory.screens.setting.dialog.AddChildInfoDialog
import inu.thebite.tory.screens.setting.viewmodel.CenterViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildClassViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildInfoViewModel

@Composable
fun SettingScreen(
    centerViewModel: CenterViewModel,
    childClassViewModel: ChildClassViewModel,
    childInfoViewModel: ChildInfoViewModel,

){
    val context = LocalContext.current

    val centers by centerViewModel.centers.collectAsState()
    val selectedCenter by centerViewModel.selectedCenter.collectAsState()
    val allCenters by centerViewModel.allCenters.collectAsState()

    val childClasses by childClassViewModel.childClasses.collectAsState()
    val selectedChildClass by childClassViewModel.selectedChildClass.collectAsState()
    val allChildClasses by childClassViewModel.allChildClasses.observeAsState(emptyList())

    val childInfos by childInfoViewModel.childInfos.collectAsState()
    val selectedChildInfo by childInfoViewModel.selectedChildInfo.collectAsState()
    val allChildInfos by childInfoViewModel.allChildInfos.observeAsState(emptyList())

    val settingTypeList = listOf(
        "센터",
        "반",
        "아이"
    )

    val (addCenterDialog, setAddCenterDialog) = rememberSaveable{
        mutableStateOf(false)
    }
    val (addChildClassDialog, setAddChildClassDialog) = rememberSaveable{
        mutableStateOf(false)
    }
    val (addChildInfoDialog, setAddChildInfoDialog) = rememberSaveable{
        mutableStateOf(false)
    }

    val (updateCenterDialog, setUpdateCenterDialog) = rememberSaveable{
        mutableStateOf(false)
    }
    val (updateChildClassDialog, setUpdateChildClassDialog) = rememberSaveable{
        mutableStateOf(false)
    }
    val (updateChildInfoDialog, setUpdateChildInfoDialog) = rememberSaveable{
        mutableStateOf(false)
    }

    LaunchedEffect(Unit){
        centerViewModel.getAllCenters()
        childClassViewModel.getAllChildClasses()
        childInfoViewModel.getAllChildInfos()
    }

    LaunchedEffect(selectedCenter, allChildClasses){
        selectedCenter?.let {
            childClassViewModel.getChildClassesByCenter(
                it
            )
        }
    }

    LaunchedEffect(selectedChildClass, allChildInfos){
        selectedChildClass?.let { selectedChildClass ->
                childInfoViewModel.getChildInfosByClass(
                    selectedChildClass
                )
        }
    }

    if(addCenterDialog){
        AddCenterDialog(
            centerViewModel = centerViewModel,
            childClassViewModel = childClassViewModel,
            childInfoViewModel = childInfoViewModel,
            setAddCenterDialog = {setAddCenterDialog(it)},
            isUpdate = false,
            selectedCenter = selectedCenter,
            childClasses = childClasses
        )
    }
    if(updateCenterDialog){
        AddCenterDialog(
            centerViewModel = centerViewModel,
            childClassViewModel = childClassViewModel,
            childInfoViewModel = childInfoViewModel,
            setAddCenterDialog = {setUpdateCenterDialog(it)},
            isUpdate = true,
            selectedCenter = selectedCenter,
            childClasses = childClasses
        )
    }
    if(addChildClassDialog){
        AddChildClassDialog(
            selectedCenter = selectedCenter,
            childClassViewModel = childClassViewModel,
            setAddChildClassDialog = {setAddChildClassDialog(it)},
            selectedChildClass = selectedChildClass,
            childInfoViewModel = childInfoViewModel,
            childInfos = childInfos,
            isUpdate = false
        )
    }
    if(updateChildClassDialog){
        AddChildClassDialog(
            selectedCenter = selectedCenter,
            childClassViewModel = childClassViewModel,
            setAddChildClassDialog = {setUpdateChildClassDialog(it)},
            selectedChildClass = selectedChildClass,
            childInfoViewModel = childInfoViewModel,
            childInfos = childInfos,
            isUpdate = true
        )
    }
    if(addChildInfoDialog){
        AddChildInfoDialog(
            context = context,
            childInfos = childInfos,
            selectedChildClass = selectedChildClass,
            childInfoViewModel = childInfoViewModel,
            setAddChildInfoDialog = {setAddChildInfoDialog(it)},
            selectedChildInfo = selectedChildInfo,
            isUpdate = false
        )
    }
    if(updateChildInfoDialog){
        AddChildInfoDialog(
            context = context,
            childInfos = childInfos,
            selectedChildClass = selectedChildClass,
            childInfoViewModel = childInfoViewModel,
            setAddChildInfoDialog = {setUpdateChildInfoDialog(it)},
            selectedChildInfo = selectedChildInfo,
            isUpdate = true
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn{
            items(settingTypeList){settingType ->
                when(settingType){
                    "센터" -> {
                        if(allCenters.isNotNull()){
                            Log.e("allCenters", allCenters.toString())
                            CenterItemRow(
                                settingType = settingType,
                                allCenters = allCenters,
                                selectedCenter = selectedCenter,
                                centerViewModel = centerViewModel,
                                childClassViewModel = childClassViewModel,
                                childInfoViewModel = childInfoViewModel,
                                setAddCenterDialog = {setAddCenterDialog(it)},
                                setUpdateCenterDialg = {setUpdateCenterDialog(it)}
                            )
                        }
                    }
                    "반" -> {
                        ChildClassItemRow(
                            settingType = settingType,
                            childClasses = childClasses,
                            selectedChildClass = selectedChildClass,
                            selectedCenter = selectedCenter,
                            childClassViewModel = childClassViewModel,
                            childInfoViewModel = childInfoViewModel,
                            setAddChildClassDialog = {setAddChildClassDialog(it)},
                            setUpdateChildClassDialog = {setUpdateChildClassDialog(it)}
                        )
                    }
                    "아이" -> {
                        ChildInfoItemRow(
                            settingType = settingType,
                            childInfos = childInfos,
                            selectedChildInfo = selectedChildInfo,
                            selectedChildClass = selectedChildClass,
                            childInfoViewModel = childInfoViewModel,
                            setAddChildInfoDialog = {setAddChildInfoDialog(it)},
                            setUpdateChildInfoDialog = {setUpdateChildInfoDialog(it)}
                        )
                    }
                }

            }
        }
    }
}




