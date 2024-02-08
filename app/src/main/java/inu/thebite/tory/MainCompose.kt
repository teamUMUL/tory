package inu.thebite.tory

import android.util.Log
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.yml.charts.common.extensions.isNotNull
import es.dmoral.toasty.Toasty
import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.schedule.ScheduleTopBar
import inu.thebite.tory.schedule.TodoViewModel
import inu.thebite.tory.screens.centerdashboardscreen.CenterDashboardScreen
import inu.thebite.tory.screens.education.screen.NewEducationScreen
import inu.thebite.tory.screens.education.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.game.viewmodel.GameViewModel
import inu.thebite.tory.screens.management.ManagementScreen
import inu.thebite.tory.screens.navigation.AllDestinations
import inu.thebite.tory.screens.navigation.AppDrawer
import inu.thebite.tory.screens.navigation.AppNavigationActions
import inu.thebite.tory.screens.notice.NoticeScreen
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel
import inu.thebite.tory.screens.setting.viewmodel.CenterViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildClassViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildInfoViewModel
import inu.thebite.tory.screens.teachingboard.HomeScreen
import inu.thebite.tory.screens.teachingboard.viewmodel.CenterSelectViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildClassSelectViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildSelectViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainCompose(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
//    ltoViewModel : LTOViewModel,
    centerSelectViewModel: CenterSelectViewModel,
    childClassSelectViewModel: ChildClassSelectViewModel,
    childSelectViewModel: ChildSelectViewModel,
//    stoViewModel : STOViewModel,
    centerViewModel: CenterViewModel,
    childClassViewModel: ChildClassViewModel,
    childInfoViewModel: ChildInfoViewModel,
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel,
    imageViewModel: ImageViewModel,
    dragAndDropViewModel: DragAndDropViewModel,
    gameViewModel: GameViewModel,
    noticeViewModel: NoticeViewModel,
    todoViewModel: TodoViewModel
//    dragAndDropViewModel : DragAndDropViewModel,
//    gameViewModel : GameViewModel
) {

    val (childDialogOpen, setChildDialogOpen) = rememberSaveable {
        mutableStateOf(false)
    }
    val allCenters by centerSelectViewModel.allCenters.collectAsState()
    val selectedCenter by centerSelectViewModel.selectedCenter.collectAsState()
    val _selectedCenter by centerSelectViewModel.tempSelectedCenter.collectAsState()


    val allChildClasses by childClassSelectViewModel.allChildClasses.collectAsState()
//    val childClasses by childClassSelectViewModel.childClasses.collectAsState()
    val selectedChildClass by childClassSelectViewModel.selectedChildClass.collectAsState()
    val _selectedChildClass by childClassSelectViewModel.tempSelectedChildClass.collectAsState()

//    val allChildInfos by childSelectViewModel.allChildInfos.collectAsState()
    val childInfos by childSelectViewModel.childInfos.collectAsState()
    val selectedChildInfo by childSelectViewModel.selectedChildInfo.collectAsState()
    val _selectedChildInfo by childSelectViewModel.tempSelectedChildInfo.collectAsState()


    LaunchedEffect(_selectedCenter, allCenters) {
        _selectedCenter?.let {
            childClassSelectViewModel.getAllChildClasses(
                it.id
            )
            childClassSelectViewModel.clearTempSelectedChildClass()
            childClassSelectViewModel.clearAllChildClasses()
            childSelectViewModel.clearTempSelectedChildInfo()
            childSelectViewModel.clearChildInfos()
        }
    }

    LaunchedEffect(_selectedChildClass, allChildClasses) {
        _selectedChildClass?.let { selectedChildClass ->
            childSelectViewModel.getAllChildInfos(
                classId = selectedChildClass.id
            )
            childSelectViewModel.clearTempSelectedChildInfo()
            childSelectViewModel.clearChildInfos()
        }
    }

//    LaunchedEffect(allChildInfos) {
//        _selectedChildClass?.let { selectedChildClass ->
//            childSelectViewModel.getAllChildInfos(
//                classId = selectedChildClass.id
//            )
//        }
//    }

    LaunchedEffect(selectedChildInfo){
        selectedChildInfo?.let {selectedChildInfo ->
            devViewModel.getAllDEVs()
            ltoViewModel.getAllLTOs(studentId = selectedChildInfo.id)
            stoViewModel.getAllSTOs(studentId = selectedChildInfo.id)
        }
    }

    if (childDialogOpen) {
        centerSelectViewModel.getAllCenters()
        Dialog(
            onDismissRequest = { setChildDialogOpen(false) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 16.dp
                    )

            ) {
                TopAppBar(
                    title = { Text(text = "유아선택", fontSize = 32.sp, color = Color.Black) },
                    modifier = Modifier.fillMaxWidth(),
                    actions = {
                        IconButton(onClick = { setChildDialogOpen(false) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.size(36.dp),
                                tint = Color.Black
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top

                ) {
                    allCenters?.let {allCenters ->
                        Text(
                            text = "센터",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        CenterSelector(
                            onDismiss = {  },
                            centerSelectViewModel = centerSelectViewModel,
                            childClassSelectViewModel = childClassSelectViewModel,
                            childSelectViewModel = childSelectViewModel,
                            containSelectButton = false
                        )

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "반",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                    )
                    ClassSelector(
                        onDismiss = {  },
                        childClassSelectViewModel = childClassSelectViewModel,
                        childSelectViewModel = childSelectViewModel,
                        containSelectButton = false
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "아이",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                    )
                    ChildSelector(
                        onDismiss = { setChildDialogOpen(false) },
                        childSelectViewModel = childSelectViewModel,
                        containSelectButton = false
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    _selectedCenter?.let { selectedCenter ->
                        _selectedChildInfo?.let { selectedChildInfo ->
                            _selectedChildClass?.let { selectedChildClass ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(50.dp)
                                ) {
                                    Button(
                                        modifier = Modifier
                                            .weight(5f)
                                            .height(55.dp),
                                        shape = RoundedCornerShape(5.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFFBFBFBF)
                                        ),
                                        onClick = {
                                            centerSelectViewModel.clearTempSelectedCenter()
                                            childClassSelectViewModel.clearTempSelectedChildClass()
                                            childSelectViewModel.clearTempSelectedChildInfo()
                                            setChildDialogOpen(false)
                                        }
                                    ) {
                                        Text(
                                            text = "취소",
                                            fontSize = 20.sp
                                        )
                                    }
                                    Button(
                                        modifier = Modifier
                                            .weight(5f)
                                            .height(55.dp),
                                        shape = RoundedCornerShape(5.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF0047B3)
                                        ),
                                        onClick = {
                                            centerSelectViewModel.setSelectedCenter(selectedCenter)
                                            childClassSelectViewModel.setSelectedChildClass(
                                                selectedChildClass
                                            )
                                            childSelectViewModel.setSelectedChildInfo(selectedChildInfo)
                                            noticeViewModel.clearAll()
                                            setChildDialogOpen(false)
                                        }
                                    ) {
                                        Text(
                                            text = "선택하기",
                                            fontSize = 20.sp
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        currentNavBackStackEntry?.destination?.route ?: AllDestinations.CENTERDASHBOARD
    val navigationActions = remember(navController) {
        AppNavigationActions(navController)
    }
    val currentRouteToKorean = when (currentRoute) {
        "CenterDashBoard" -> {
            "Center Dashboard"
        }

        "TeachingBoard" -> {
            "Teaching Board"
        }

        "Education" -> {
            "Education"
        }

        "NoticeBoard" -> {
            "Notice Board"
        }

        "READY" -> {
            "Ready"
        }

        "Setting" -> {
            "Management"
        }

        else -> {
            currentRoute
        }
    }

//    val dummyScheduleList = mutableListOf<DummySchedule>()
//    val dummySTOList = mutableListOf<StoResponse>().toMutableStateList()
//    for (i in 1..8){
//        dummySTOList.add(
//            StoResponse(
//                id = i.toLong(),
//                templateNum = i,
//                status = "준거 도달",
//                name = "같은 사진 매칭(${i} array)",
//                contents = "",
//                count = i,
//                goal = i,
//                goalPercent = i,
//                achievementOrNot = "",
//                urgeType = "",
//                urgeContent = "",
//                enforceContent = "",
//                memo = "",
//                hitGoalDate = "",
//                registerDate = "",
//                delYN = "",
//                round = i,
//                imageList = listOf(),
//                pointList = listOf(),
//                lto = LtoResponse(
//                    id = i.toLong(),
//                    templateNum = i,
//                    status = "",
//                    name = "더미용 LTO ${i}",
//                    contents = "",
//                    game = "",
//                    achieveDate = "",
//                    registerDate = "",
//                    delYN = "",
//                    domain = DomainResponse(
//                        id = i.toLong(),
//                        templateNum = i,
//                        type = "",
//                        status = "",
//                        name = "더미용 Domain ${i}",
//                        contents = "",
//                        useYN = "",
//                        delYN = "",
//                        registerDate = ""
//                    )
//                )
//            )
//        )
//    }
//    for (i in 1..8){
//        dummyScheduleList.add(
//            DummySchedule(
//                date = "2023/11/${i} (월)",
//                stoList = dummySTOList
//            )
//        )
//
//    }

    val gray = Brush.horizontalGradient(
        colors = listOf(Color(0xFFF3F3F3), Color(0xFFF3F3F3))
    )
    val whiteBackground = Brush.horizontalGradient(
        colors = listOf(Color(0xFFEFEFEF), Color(0xFFEFEFEF))
    )
    ModalNavigationDrawer(drawerContent = {
        AppDrawer(
            route = currentRoute,
            navigateToCenterDashboard = { navigationActions.navigateToCenterDashboard() },
            navigateToTeachingBoard = { navigationActions.navigateToTeachingBoard() },
            navigateToSetting = { navigationActions.navigateToSetting() },
            navigateToEducation = { navigationActions.navigateToEducation() },
            navigateToNotice = { navigationActions.navigateToNotice() },
            navigateToReady = { navigationActions.navigateToReady() },
            closeDrawer = { coroutineScope.launch { drawerState.close() } },
            modifier = Modifier
        )
    }, drawerState = drawerState) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(
                        text = "",
                        color = when(currentRoute){
                            AllDestinations.CENTERDASHBOARD -> {
                                Color.Black
                            }
                            AllDestinations.TEACHINGBOARD -> {
                                Color.Black
                            }
                            AllDestinations.EDUCATION -> {
                                Color.White
                            }
                            AllDestinations.NOTICE ->{
                                Color.Black
                            }
                            AllDestinations.READY -> {
                                Color.Black
                            }
                            AllDestinations.SETTING -> {
                                Color.Black
                            }
                            else -> {
                                Color.Black
                            }
                        },
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontFamily = fontFamily_Inter,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),

                            )
                    ) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = when (currentRoute) {
                                AllDestinations.CENTERDASHBOARD -> {
                                    whiteBackground
                                }

                                AllDestinations.TEACHINGBOARD -> {
                                    whiteBackground
                                }

                                AllDestinations.EDUCATION -> {
                                    gray
                                }

                                AllDestinations.NOTICE -> {
                                    gray
                                }

                                AllDestinations.READY -> {
                                    whiteBackground
                                }

                                AllDestinations.SETTING -> {
                                    whiteBackground
                                }

                                else -> {
                                    whiteBackground
                                }
                            }
                        ),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Menu, contentDescription = null,
                                    tint = when(currentRoute){
                                        AllDestinations.CENTERDASHBOARD -> {
                                            Color.Black
                                        }
                                        AllDestinations.TEACHINGBOARD -> {
                                            Color.Black
                                        }
                                        AllDestinations.EDUCATION -> {
                                            Color.Black
                                        }
                                        AllDestinations.NOTICE ->{
                                            Color.Black
                                        }
                                        AllDestinations.READY -> {
                                            Color.Black
                                        }
                                        AllDestinations.SETTING -> {
                                            Color.Black
                                        }
                                        else -> {
                                            Color.Black
                                        }
                                    }
                                )
                            })
                    },
                    actions = {
                        Box(
                            modifier = Modifier
                                .height(40.dp)
                        ) {
                            when(currentRoute){

                                AllDestinations.CENTERDASHBOARD -> {
                                    Row(
                                        modifier = Modifier.fillMaxHeight(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                                            selectedCenter?.let { Text(text = it.name) }
                                            selectedChildClass?.let { Text(text = " > "+it.name) }
                                            selectedChildInfo?.let { Text(text = " > "+it.name) }
                                        }
                                        IconButton(onClick = {
                                            setChildDialogOpen(true)
                                        }) {
                                            Icon(painter = painterResource(id = R.drawable.icon_user), contentDescription = null)
                                        }
                                    }
                                }
                                AllDestinations.TEACHINGBOARD -> {
                                    Row(
                                        modifier = Modifier.fillMaxHeight(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                                            selectedCenter?.let { Text(text = it.name) }
                                            selectedChildClass?.let { Text(text = " > "+it.name) }
                                            selectedChildInfo?.let { Text(text = " > "+it.name) }
                                        }
                                        IconButton(onClick = {
                                            setChildDialogOpen(true)
                                        }) {
                                            Icon(painter = painterResource(id = R.drawable.icon_user), contentDescription = null)
                                        }
                                    }
                                }
                                AllDestinations.EDUCATION -> {
                                    Column {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .fillMaxHeight()
                                        ) {
                                            selectedChildInfo?.let {selectedChildInfo ->
                                                ScheduleTopBar(
                                                    modifier = Modifier.weight(4f),
                                                    selectedChild = selectedChildInfo,
                                                    currentRoute = currentRoute,
                                                    devViewModel = devViewModel,
                                                    ltoViewModel = ltoViewModel,
                                                    stoViewModel = stoViewModel,
                                                    todoViewModel = todoViewModel,
                                                    childSelectViewModel = childSelectViewModel
                                                )
                                            }


                                            Row(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .weight(1f),
                                                horizontalArrangement = Arrangement.End,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {

                                                Row(
                                                    modifier = Modifier.fillMaxHeight(),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    selectedCenter?.let { Text(text = it.name, color = Color.Black) }
                                                    selectedChildClass?.let { Text(text = " > " + it.name, color = Color.Black) }
                                                    selectedChildInfo?.let { Text(text = " > " + it.name, color = Color.Black) }
                                                }
                                                IconButton(onClick = {
                                                    setChildDialogOpen(true)
                                                }) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.icon_user),
                                                        contentDescription = null,
                                                        tint = Color.Black
                                                    )
                                                }
                                            }
                                        }

                                    }

                                }
                                AllDestinations.NOTICE ->{
//                                    Row(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .fillMaxHeight()
//                                            .background(color = Color(0xFF7F5AF0)),
//                                        verticalAlignment = Alignment.CenterVertically,
//                                        horizontalArrangement = Arrangement.SpaceBetween
//                                    ) {
//                                        Text(
//                                            text = "Notice board",
//                                            style = TextStyle(
//                                                fontSize = 33.sp,
//                                                fontFamily = fontFamily_Inter,
//                                                fontWeight = FontWeight(400),
//                                                color = Color(0xFFFFFFFF),
//                                            ),
//                                            modifier = Modifier
//                                                .padding(start = 100.dp)
//                                        )
//                                    }
                                    Row(
                                        modifier = Modifier.fillMaxHeight(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                                            selectedCenter?.let { Text(text = it.name, color = Color.Black) }
                                            selectedChildClass?.let { Text(text = " > "+it.name, color = Color.Black) }
                                            selectedChildInfo?.let { Text(text = " > "+it.name, color = Color.Black) }
                                        }
                                        IconButton(onClick = {
                                            setChildDialogOpen(true)
                                        }) {
                                            Icon(painter = painterResource(id = R.drawable.icon_user), contentDescription = null, tint = Color.Black)
                                        }
                                    }
                                }
                                AllDestinations.READY -> {
                                    Row(
                                        modifier = Modifier.fillMaxHeight(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                                            selectedCenter?.let { Text(text = it.name) }
                                            selectedChildClass?.let { Text(text = " > "+it.name) }
                                            selectedChildInfo?.let { Text(text = " > "+it.name) }
                                        }
                                        IconButton(onClick = {
                                            setChildDialogOpen(true)
                                        }) {
                                            Icon(painter = painterResource(id = R.drawable.icon_user), contentDescription = null)
                                        }
                                    }
                                }
                                AllDestinations.SETTING -> {
                                    Row(
                                        modifier = Modifier.fillMaxHeight(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                                            selectedCenter?.let { Text(text = it.name) }
                                            selectedChildClass?.let { Text(text = " > "+it.name) }
                                            selectedChildInfo?.let { Text(text = " > "+it.name) }
                                        }
                                        IconButton(onClick = {
                                            setChildDialogOpen(true)
                                        }) {
                                            Icon(painter = painterResource(id = R.drawable.icon_user), contentDescription = null)
                                        }
                                    }
                                }
                            }


                        }


                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent)
                )
            }, modifier = Modifier
        ) {
            NavHost(
                navController = navController,
                startDestination = AllDestinations.CENTERDASHBOARD,
                modifier = modifier.padding(it)
            ) {
                composable(AllDestinations.CENTERDASHBOARD) {
                    CenterDashboardScreen(
                        centerViewModel = centerViewModel,
                        childClassViewModel = childClassViewModel,
                        childInfoViewModel = childInfoViewModel,
                        navigateToTeachingBoard = { navController.navigate(AllDestinations.TEACHINGBOARD) }
                    )
//                    CenterHome(
//                        centerSelectViewModel = centerSelectViewModel,
//                        childClassSelectViewModel = childClassSelectViewModel,
//                        childSelectViewModel = childSelectViewModel
//                    )
                }

                composable(AllDestinations.TEACHINGBOARD) {
                    HomeScreen(
                        centerSelectViewModel = centerSelectViewModel,
                        childClassSelectViewModel = childClassSelectViewModel,
                        childSelectViewModel = childSelectViewModel,
                        devViewModel = devViewModel,
                        ltoViewModel = ltoViewModel,
                        stoViewModel = stoViewModel,
                        navigateToEducation = { navController.navigate(AllDestinations.EDUCATION) },
                        navigateToNotice = {navController.navigate(AllDestinations.NOTICE)}
                    )
//                    CenterHome(
//                        centerSelectViewModel = centerSelectViewModel,
//                        childClassSelectViewModel = childClassSelectViewModel,
//                        childSelectViewModel = childSelectViewModel
//                    )
                }

                composable(AllDestinations.EDUCATION) {
                    selectedChildInfo?.let { selectedChildInfo ->
                        NewEducationScreen(
                            selectedChild = selectedChildInfo,
                            devViewModel = devViewModel,
                            ltoViewModel = ltoViewModel,
                            stoViewModel = stoViewModel,
                            imageViewModel = imageViewModel,
                            dragAndDropViewModel = dragAndDropViewModel,
                            gameViewModel = gameViewModel,
                            todoViewModel = todoViewModel,
                            noticeViewModel = noticeViewModel,
                            childSelectViewModel = childSelectViewModel
                        )
                    } ?: NoChildSelect()
//                    EducationScreen(
//                        ltoViewModel = ltoViewModel,
//                        childSelectViewModel = childSelectViewModel,
//                        stoViewModel = stoViewModel,
//                        centerViewModel = centerSelectViewModel,
//                        childInfoViewModel = childSelectViewModel,
//                        childClassViewModel = childClassSelectViewModel,
//                        dragAndDropViewModel = dragAndDropViewModel,
//                        gameViewModel = gameViewModel
//                    )
                }

                composable(AllDestinations.NOTICE) {
                    selectedChildInfo?.let { selectedChildInfo ->
                        NoticeScreen(
                            noticeViewModel = noticeViewModel,
                            stoViewModel = stoViewModel,
                            ltoViewModel = ltoViewModel,
                            navController = navController,
                            selectedChild = selectedChildInfo,
                            childSelectViewModel = childSelectViewModel
                        )
                    } ?: NoChildSelect()
                }

                composable(AllDestinations.SETTING) {
//                    SettingScreen(
//                        centerViewModel = centerViewModel,
//                        childClassViewModel = childClassViewModel,
//                        childInfoViewModel = childInfoViewModel
//                    )
                    ManagementScreen(
                        centerViewModel = centerViewModel,
                        childClassViewModel = childClassViewModel,
                        childInfoViewModel = childInfoViewModel
                    )
                }

                composable(AllDestinations.READY) {
//                    ReadyScreen(
//                        imageViewModel = imageViewModel
//                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildSelector(
    onDismiss: () -> Unit,
    childSelectViewModel: ChildSelectViewModel,
    containSelectButton: Boolean = true
){
    val context = LocalContext.current

    val items by childSelectViewModel.childInfos.collectAsState()

    val selectedChild by childSelectViewModel.tempSelectedChildInfo.collectAsState()

    var isExpanded by remember {
        mutableStateOf(false)
    }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f, label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            expanded = isExpanded,
            onExpandedChange = {
                if (items.isNotNull()){
                    isExpanded = !isExpanded
                } else {
                    Toasty.warning(context, "반을 선택해주세요", Toast.LENGTH_SHORT, true).show()
                }
            }
        ){
            TextField(
                value = selectedChild?.name ?: "아이를 선택해주세요.",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .rotate(rotationState)
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    unfocusedContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    disabledContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                textStyle = TextStyle(
                    color = Color.Black, fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .height(80.dp)
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {isExpanded = false},
                modifier = Modifier
                    .exposedDropdownSize(),
            ){
                items?.let { items ->
                    items.forEach {item ->
                        DropdownMenuItem(
                            text = {
                                Text(text = item.name)
                            },
                            onClick = {
                                childSelectViewModel.setTempSelectedChildInfo(item)
                                isExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }

    if (containSelectButton){
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onDismiss() },
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text(text = "취소")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(modifier = Modifier
                .weight(1f)
                .height(50.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0047B3)
                ),
                onClick = {
                    selectedChild?.let {selectedChild ->
                        childSelectViewModel.setSelectedChildInfo(selectedChild)
                    }
                    onDismiss()
                },

                ) {
                Text(text = "선택")
            }
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassSelector(
    onDismiss: () -> Unit,
    childClassSelectViewModel: ChildClassSelectViewModel,
    childSelectViewModel: ChildSelectViewModel,
    containSelectButton: Boolean = true
){
    val items by childClassSelectViewModel.allChildClasses.collectAsState()

    val context = LocalContext.current

    val selectedChildClass by childClassSelectViewModel.tempSelectedChildClass.collectAsState()

    var isExpanded by remember {
        mutableStateOf(false)
    }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f, label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            expanded = isExpanded,
            onExpandedChange = {
                Log.d("allChildClasses",items.toString())
                if (items == null){
                    Toasty.warning(context, "센터를 선택해주세요", Toast.LENGTH_SHORT, true).show()
                } else {
                    isExpanded = !isExpanded
                }
            }
        ){
            TextField(
                value = selectedChildClass?.name ?: "반을 선택해주세요.",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .rotate(rotationState)
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    unfocusedContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    disabledContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                textStyle = TextStyle(
                    color = Color.Black, fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .height(80.dp)
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {isExpanded = false},
                modifier = Modifier
                    .exposedDropdownSize(),
            ){
                items?.let { items ->
                    items.forEach {item ->
                        DropdownMenuItem(
                            text = {
                                Text(text = item.name)
                            },
                            onClick = {
                                childClassSelectViewModel.setTempSelectedChildClass(item)
                                isExpanded = false
                            }
                        )
                    }
                }

            }
        }
    }
    if (containSelectButton){
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onDismiss() },
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text(text = "취소")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(modifier = Modifier
                .weight(1f)
                .height(50.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0047B3)
                ),
                onClick = {
                    selectedChildClass?.let {selectedChildClass ->
                        childClassSelectViewModel.setSelectedChildClass(selectedChildClass)
                    }
                    childSelectViewModel.clearTempSelectedChildInfo()
                    childSelectViewModel.clearChildInfos()
                    childSelectViewModel.clearSelectedChildInfo()
                    onDismiss()

                },

                ) {
                Text(text = "선택")
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterSelector(
    onDismiss: () -> Unit,
    centerSelectViewModel: CenterSelectViewModel,
    childSelectViewModel: ChildSelectViewModel,
    childClassSelectViewModel: ChildClassSelectViewModel,
    containSelectButton: Boolean = true
){
    val context = LocalContext.current

    val items by centerSelectViewModel.allCenters.collectAsState()

    val selectedCenter by centerSelectViewModel.tempSelectedCenter.collectAsState()

    var isExpanded by remember {
        mutableStateOf(false)
    }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f, label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            expanded = isExpanded,
            onExpandedChange = {
                isExpanded = !isExpanded
            }
        ){
            TextField(
                value = selectedCenter?.name ?: "반을 선택해주세요.",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .rotate(rotationState)
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    unfocusedContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    disabledContainerColor= MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                textStyle = TextStyle(
                    color = Color.Black, fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .height(80.dp)
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {isExpanded = false},
                modifier = Modifier
                    .exposedDropdownSize(),
            ){
                items.forEach {item ->
                    DropdownMenuItem(
                        text = {
                            Text(text = item.name)
                        },
                        onClick = {
                            centerSelectViewModel.setTempSelectedCenter(item)
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
    if (containSelectButton){
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onDismiss() },
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text(text = "취소")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(modifier = Modifier
                .weight(1f)
                .height(50.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0047B3)
                ),
                onClick = {
                    selectedCenter?.let {selectedCenter ->
                        centerSelectViewModel.setSelectedCenter(selectedCenter)
                    }
//                onConfirm(chainText)
//                                viewModel.selectedChildClass = selectedChildClass
                    childSelectViewModel.clearChildInfos()
                    childClassSelectViewModel.clearTempSelectedChildClass()
                    childSelectViewModel.clearTempSelectedChildInfo()
                    childSelectViewModel.clearSelectedChildInfo()
                    onDismiss()

                },

                ) {
                Text(text = "선택")
            }
        }
    }

}

@Composable
fun CenterControl(
    items: List<CenterResponse>,
    selectedCenter: CenterResponse?,
    centerSelectViewModel: CenterSelectViewModel,
    childClassSelectViewModel: ChildClassSelectViewModel,
    childSelectViewModel: ChildSelectViewModel,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    cornerRadius: Int = 10,
    @ColorRes color: Int = R.color.light_gray,
) {
    Row(
        modifier = Modifier
    ) {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                modifier = when (index) {
                    0 -> {
                        if (useFixedWidth) {
                            Modifier
                                .width(itemWidth)
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedCenter == item) 1f else 0f)
                        } else {
                            Modifier
                                .wrapContentSize()
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedCenter == item) 1f else 0f)
                        }
                    }

                    else -> {
                        if (useFixedWidth) {
                            Modifier
                                .width(itemWidth)
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedCenter == item) 1f else 0f)
                        } else {
                            Modifier
                                .wrapContentSize()
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedCenter == item) 1f else 0f)
                        }
                    }
                },
                onClick = {
                    if (selectedCenter == item) {
                        centerSelectViewModel.clearTempSelectedCenter()
                        childClassSelectViewModel.clearAllChildClasses()
                    } else {
                        centerSelectViewModel.setTempSelectedCenter(item)
//                        childClassSelectViewModel.getChildClassesByCenter(item)
                    }
                    childSelectViewModel.clearChildInfos()
                    childClassSelectViewModel.clearTempSelectedChildClass()
                    childSelectViewModel.clearTempSelectedChildInfo()
                },
                shape = when (index) {
                    //왼쪽 바깥
                    0 -> RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = 0,
                        bottomStartPercent = cornerRadius,
                        bottomEndPercent = 0
                    )
                    //오른쪽 끝
                    items.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = 0,
                        bottomEndPercent = cornerRadius
                    )
                    //가운데 버튼들
                    else -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 0,
                        bottomStartPercent = 0,
                        bottomEndPercent = 0
                    )
                },
                border = BorderStroke(
                    1.dp, if (selectedCenter == item) {
                        colorResource(id = color)
                    } else {
                        colorResource(id = color).copy(alpha = 0.75f)
                    }
                ),
                colors = if (selectedCenter == item) {
                    ButtonDefaults.outlinedButtonColors(
                        //선택된 경우 색
                        containerColor = colorResource(id = color)
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        //선택 안된 경우 색
                        containerColor = Color.Transparent
                    )
                },
            ) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Normal,
                    color = if (selectedCenter == item) {
                        Color.White
                    } else {
                        colorResource(id = color).copy(alpha = 0.9f)
                    }
                )
            }

        }
    }
}

@Composable
fun ChildClassControl(
    items: List<ChildClassResponse>,
    selectedChildClass: ChildClassResponse?,
    childClassSelectViewModel: ChildClassSelectViewModel,
    childSelectViewModel: ChildSelectViewModel,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    cornerRadius: Int = 10,
    @ColorRes color: Int = R.color.light_gray,
) {
    Row(
        modifier = Modifier
    ) {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                modifier = when (index) {
                    0 -> {
                        if (useFixedWidth) {
                            Modifier
                                .width(itemWidth)
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedChildClass == item) 1f else 0f)
                        } else {
                            Modifier
                                .wrapContentSize()
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedChildClass == item) 1f else 0f)
                        }
                    }

                    else -> {
                        if (useFixedWidth) {
                            Modifier
                                .width(itemWidth)
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedChildClass == item) 1f else 0f)
                        } else {
                            Modifier
                                .wrapContentSize()
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedChildClass == item) 1f else 0f)
                        }
                    }
                },
                onClick = {
                    if (selectedChildClass == item) {
                        childClassSelectViewModel.clearTempSelectedChildClass()

                    } else {
                        childClassSelectViewModel.setTempSelectedChildClass(item)
//                        childSelectViewModel.getChildInfosByClass(
//                            item
//                        )
                    }
                    childSelectViewModel.clearTempSelectedChildInfo()
                    childSelectViewModel.clearChildInfos()
                },
                shape = when (index) {
                    //왼쪽 바깥
                    0 -> RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = 0,
                        bottomStartPercent = cornerRadius,
                        bottomEndPercent = 0
                    )
                    //오른쪽 끝
                    items.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = 0,
                        bottomEndPercent = cornerRadius
                    )
                    //가운데 버튼들
                    else -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 0,
                        bottomStartPercent = 0,
                        bottomEndPercent = 0
                    )
                },
                border = BorderStroke(
                    1.dp, if (selectedChildClass == item) {
                        colorResource(id = color)
                    } else {
                        colorResource(id = color).copy(alpha = 0.75f)
                    }
                ),
                colors = if (selectedChildClass == item) {
                    ButtonDefaults.outlinedButtonColors(
                        //선택된 경우 색
                        containerColor = colorResource(id = color)
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        //선택 안된 경우 색
                        containerColor = Color.Transparent
                    )
                },
            ) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Normal,
                    color = if (selectedChildClass == item) {
                        Color.White
                    } else {
                        colorResource(id = color).copy(alpha = 0.9f)
                    }
                )
            }

        }
    }
}

@Composable
fun ChildInfoControl(
    items: List<StudentResponse>,
    selectedChildInfo: StudentResponse?,
    childSelectViewModel: ChildSelectViewModel,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    cornerRadius: Int = 10,
    @ColorRes color: Int = R.color.light_gray,
) {
    Row(
        modifier = Modifier
    ) {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                modifier = when (index) {
                    0 -> {
                        if (useFixedWidth) {
                            Modifier
                                .width(itemWidth)
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedChildInfo == item) 1f else 0f)
                        } else {
                            Modifier
                                .wrapContentSize()
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedChildInfo == item) 1f else 0f)
                        }
                    }

                    else -> {
                        if (useFixedWidth) {
                            Modifier
                                .width(itemWidth)
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedChildInfo == item) 1f else 0f)
                        } else {
                            Modifier
                                .wrapContentSize()
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedChildInfo == item) 1f else 0f)
                        }
                    }
                },
                onClick = {
                    if (selectedChildInfo == item) {
                        childSelectViewModel.clearTempSelectedChildInfo()
                    } else {
                        childSelectViewModel.setTempSelectedChildInfo(item)
                    }
                },
                shape = when (index) {
                    //왼쪽 바깥
                    0 -> RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = 0,
                        bottomStartPercent = cornerRadius,
                        bottomEndPercent = 0
                    )
                    //오른쪽 끝
                    items.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = 0,
                        bottomEndPercent = cornerRadius
                    )
                    //가운데 버튼들
                    else -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 0,
                        bottomStartPercent = 0,
                        bottomEndPercent = 0
                    )
                },
                border = BorderStroke(
                    1.dp, if (selectedChildInfo == item) {
                        colorResource(id = color)
                    } else {
                        colorResource(id = color).copy(alpha = 0.75f)
                    }
                ),
                colors = if (selectedChildInfo == item) {
                    ButtonDefaults.outlinedButtonColors(
                        //선택된 경우 색
                        containerColor = colorResource(id = color)
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        //선택 안된 경우 색
                        containerColor = Color.Transparent
                    )
                },
            ) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Normal,
                    color = if (selectedChildInfo == item) {
                        Color.White
                    } else {
                        colorResource(id = color).copy(alpha = 0.9f)
                    }
                )
            }

        }
    }
}

@Composable
fun NoChildSelect(

){

//    val (noticePdfDialog, setNoticePdfDialog) = remember {
//        mutableStateOf(false)
//    }
//    if (noticePdfDialog) {
//        val exampleURL = "http://192.168.35.225:8081/notices/4/reports?year=2023&month=12&date=12"
//
//        Dialog(
//            properties = DialogProperties(
//                usePlatformDefaultWidth = false,
//
//            ),
//            onDismissRequest = { setNoticePdfDialog(false) }
//        ) {
//
//            AndroidView(factory = { context ->
//                WebView(context).apply {
//                    webViewClient = WebViewClient()
//                    loadUrl(exampleURL)
//                    settings.javaScriptEnabled = true
//                }
//            })
//
//        }
//    }
//    LaunchedEffect(Unit){
//        setNoticePdfDialog(true)
//    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "아이를 선택해주세요",
            style = TextStyle(
                fontSize = 28.sp,
                lineHeight = 28.sp,
                fontFamily = fontFamily_Inter,
                fontWeight = FontWeight(600),
                color = Color.Black,
                letterSpacing = 0.24.sp,
            )
        )

    }
}
