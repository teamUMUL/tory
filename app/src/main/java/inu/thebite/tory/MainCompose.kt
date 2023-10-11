package inu.thebite.tory

import androidx.annotation.ColorRes
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import inu.thebite.tory.database.Center.CenterEntity
import inu.thebite.tory.database.ChildClass.ChildClassEntity
import inu.thebite.tory.database.ChildInfo.ChildInfoEntity
import inu.thebite.tory.screens.HomeScreen
import inu.thebite.tory.screens.education.EducationScreen
import inu.thebite.tory.screens.education.LTOViewModel
import inu.thebite.tory.screens.education.STOViewModel
import inu.thebite.tory.screens.navigation.AllDestinations
import inu.thebite.tory.screens.navigation.AppDrawer
import inu.thebite.tory.screens.navigation.AppNavigationActions
import inu.thebite.tory.screens.setting.viewmodel.CenterViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildClassViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildInfoViewModel
import inu.thebite.tory.screens.setting.SettingScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainCompose(
    modifier: Modifier = Modifier,
    childViewModel: ChildViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val ltoViewModel : LTOViewModel = viewModel()
    val childViewModel : ChildViewModel = viewModel()
    val stoViewModel : STOViewModel = viewModel()
    val centerViewModel : CenterViewModel = viewModel()
    val childClassViewModel : ChildClassViewModel = viewModel()
    val childInfoViewModel : ChildInfoViewModel = viewModel()


    val (childDialogOpen, setChildDialogOpen) = rememberSaveable {
        mutableStateOf(false)
    }

    val _selectedCenter by childViewModel.selectedCenter.collectAsState()
    val _selectedChildClass by childViewModel.selectedChildClass.collectAsState()
    val _selectedChildInfo by childViewModel.selectedChildInfo.collectAsState()


    val selectedCenter by centerViewModel.selectedCenter.collectAsState()
    val allCenters by centerViewModel.allCenters.collectAsState()

    val childClasses by childClassViewModel.childClasses.collectAsState()
    val selectedChildClass by childClassViewModel.selectedChildClass.collectAsState()

    val childInfos by childInfoViewModel.childInfos.collectAsState()
    val selectedChildInfo by childInfoViewModel.selectedChildInfo.collectAsState()

    LaunchedEffect(selectedCenter){
        selectedCenter?.let {
            childClassViewModel.getChildClassesByCenterName(
                it.centerName
            )
        }
    }

    LaunchedEffect(selectedChildClass){
        selectedChildClass?.let { selectedChildClass ->
            childInfoViewModel.getChildInfosByCenterNameAndClassName(
                selectedChildClass.centerName,
                selectedChildClass.className
            )
        }
    }

    if(childDialogOpen){
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
                    title = { Text(text = "유아선택", fontSize = 32.sp, color=Color.Black)},
                    modifier = Modifier.fillMaxWidth(),
                    actions = {
                        IconButton(onClick = { setChildDialogOpen(false) }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null, modifier = Modifier.size(36.dp), tint = Color.Black)
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
                    allCenters.let{
                        Text(
                            text = "센터",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        CenterControl(
                            items = allCenters,
                            selectedCenter = _selectedCenter,
                            childViewModel = childViewModel,
                            childClassViewModel =  childClassViewModel,
                            childInfoViewModel = childInfoViewModel
                        )

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    childClasses?.let{ childClasses ->
                        Text(
                            text = "반",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .padding(start = 10.dp, bottom = 10.dp)
                        )
                        ChildClassControl(
                            items = childClasses,
                            selectedChildClass = _selectedChildClass,
                            childViewModel = childViewModel,
                            childInfoViewModel = childInfoViewModel
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    childInfos?.let { childInfos ->
                        Text(
                            text = "아이",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .padding(start = 10.dp, bottom = 10.dp)
                        )
                        ChildInfoControl(
                            items = childInfos,
                            selectedChildInfo = _selectedChildInfo,
                            childViewModel = childViewModel
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    _selectedCenter?.let { _selectedCenter ->
                        _selectedChildInfo?.let { _selectedChildInfo ->
                            _selectedChildClass?.let{_selectedChildClass ->
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(55.dp),
                                    shape = RoundedCornerShape(5.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(id = R.color.light_gray)
                                    ),
                                    onClick = {
                                        centerViewModel.setSelectedCenter(_selectedCenter)
                                        childClassViewModel.setSelectedChildClass(_selectedChildClass)
                                        childInfoViewModel.setSelectedChildInfo(_selectedChildInfo)
                                        ltoViewModel.clearSelectedLTO()
                                        stoViewModel.clearSelectedSTO()
                                        setChildDialogOpen(false)
                                    }
                                ){
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

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: AllDestinations.HOME
    val navigationActions = remember(navController){
        AppNavigationActions(navController)
    }

    ModalNavigationDrawer(drawerContent = {
        AppDrawer(
            route = currentRoute,
            navigateToHome = { navigationActions.navigateToHome()},
            navigateToGame = { navigationActions.navigateToGame()},
            navigateToData = { navigationActions.navigateToData()},
            closeDrawer = { coroutineScope.launch { drawerState.close() }},
            modifier = Modifier
        )
    }, drawerState = drawerState) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = currentRoute) },
                    modifier = Modifier.fillMaxWidth(),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Menu, contentDescription = null
                            )
                        })
                    },
                    actions = {
                        Box(modifier = Modifier
                            .height(50.dp)
                        ){
                            Row(
                                modifier = Modifier.fillMaxHeight(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                                    selectedCenter?.let { Text(text = it.centerName) }
                                    selectedChildClass?.let { Text(text = " > "+it.className) }
                                    selectedChildInfo?.let { Text(text = " > "+it.childName) }
                                }
                                IconButton(onClick = {
                                    setChildDialogOpen(true)
                                }) {
                                    Icon(painter = painterResource(id = R.drawable.icon_user), contentDescription = null)
                                }
                            }


                        }

                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent))
            }, modifier = Modifier
        ) {
            NavHost(
                navController = navController, startDestination = AllDestinations.HOME, modifier = modifier.padding(it)
            ) {

                composable(AllDestinations.HOME) {
                    HomeScreen()
                }

                composable(AllDestinations.EDUCATION) {
                    EducationScreen(
                        ltoViewModel = ltoViewModel,
                        childViewModel = childViewModel,
                        stoViewModel = stoViewModel,
                        centerViewModel = centerViewModel,
                        childInfoViewModel = childInfoViewModel,
                        childClassViewModel = childClassViewModel
                    )
                }

                composable(AllDestinations.SETTING) {
                    SettingScreen(
                        centerViewModel = centerViewModel,
                        childClassViewModel = childClassViewModel,
                        childInfoViewModel = childInfoViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun CenterControl(
    items: List<CenterEntity>,
    selectedCenter: CenterEntity?,
    childViewModel: ChildViewModel,
    childClassViewModel: ChildClassViewModel,
    childInfoViewModel: ChildInfoViewModel,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    cornerRadius: Int = 10,
    @ColorRes color: Int = R.color.light_gray,
){
    Row(
        modifier = Modifier
    ) {
        items.forEachIndexed{ index, item ->
            OutlinedButton(
                modifier = when(index){
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
                        if(useFixedWidth){
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
                    if(selectedCenter == item){
                        childViewModel.clearSelectedCenter()
                        childViewModel.clearSelectedChildClass()
                        childViewModel.clearSelectedChildInfo()

                        childClassViewModel.clearChildClasses()
                        childInfoViewModel.clearChildInfos()
                    } else {
                        childViewModel.setSelectedCenter(item)
                        childClassViewModel.getChildClassesByCenterName(item.centerName)
                    }
                },
                shape = when(index) {
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
                    1.dp, if (selectedCenter == item){
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
                    text = item.centerName,
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
    items: List<ChildClassEntity>,
    selectedChildClass: ChildClassEntity?,
    childViewModel: ChildViewModel,
    childInfoViewModel: ChildInfoViewModel,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    cornerRadius: Int = 10,
    @ColorRes color: Int = R.color.light_gray,
){
    Row(
        modifier = Modifier
    ) {
        items.forEachIndexed{ index, item ->
            OutlinedButton(
                modifier = when(index){
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
                        if(useFixedWidth){
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
                    if(selectedChildClass == item){
                        childViewModel.clearSelectedChildClass()
                        childViewModel.clearSelectedChildInfo()

                        childInfoViewModel.clearChildInfos()
                    } else {
                        childViewModel.setSelectedChildClass(item)

                        childInfoViewModel.getChildInfosByCenterNameAndClassName(
                            item.centerName,
                            item.className
                        )
                    }
                },
                shape = when(index) {
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
                    1.dp, if (selectedChildClass == item){
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
                    text = item.className,
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
    items: List<ChildInfoEntity>,
    selectedChildInfo: ChildInfoEntity?,
    childViewModel : ChildViewModel,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    cornerRadius: Int = 10,
    @ColorRes color: Int = R.color.light_gray,
){
    Row(
        modifier = Modifier
    ) {
        items.forEachIndexed{ index, item ->
            OutlinedButton(
                modifier = when(index){
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
                        if(useFixedWidth){
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
                    if(selectedChildInfo == item){
                        childViewModel.clearSelectedChildInfo()
                    } else {
                        childViewModel.setSelectedChildInfo(item)
                    }
                },
                shape = when(index) {
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
                    1.dp, if (selectedChildInfo == item){
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
                    text = item.childName,
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