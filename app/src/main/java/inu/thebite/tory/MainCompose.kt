package inu.thebite.tory

import android.util.Log
import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
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
import inu.thebite.tory.screens.DataScreen.DataScreen
import inu.thebite.tory.screens.GameScreen
import inu.thebite.tory.screens.HomeScreen
import inu.thebite.tory.screens.DataScreen.LTOViewModel
import inu.thebite.tory.screens.DataScreen.STOViewModel
import inu.thebite.tory.screens.navigation.AllDestinations
import inu.thebite.tory.screens.navigation.AppDrawer
import inu.thebite.tory.screens.navigation.AppNavigationActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainCompose(
    modifier: Modifier = Modifier,
    viewModel: ChildViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val ltoViewModel : LTOViewModel = viewModel()
    val childViewModel : ChildViewModel = viewModel()
    val stoViewModel : STOViewModel = viewModel()


    val selectedChildName by childViewModel.selectedChildName.observeAsState("오전1")
    val selectedChildClass by childViewModel.selectedChildClass.observeAsState("오전반(월수금)")

    val (childDialogOpen, setChildDialogOpen) = rememberSaveable {
        mutableStateOf(false)
    }

    val (isFirst, setIsFirst) = rememberSaveable {
        mutableStateOf(true)
    }
    val childList = arrayOf(
        listOf("오전1", "오전2", "오전3", "오전4", "오전5"),
        listOf("오전6", "오전7", "오전8", "오전9"),
        listOf("오후1", "오후2", "오후3", "오후4", "오후5"),
        listOf("오후6", "오후7", "오후8", "오후9", "오후10", "오후11")

    )

    val classList = listOf<String>(
        "오전반(월수금)",
        "오후반(월수금)",
        "오전반(화목)",
        "오후반(화목)"
    )




    val (selectedChildClassIndex, setSelectedChildClassIndex) = rememberSaveable {
        mutableStateOf(0)
    }

    val (selectedChildrenIndex, setSelectedChildrenIndex) = rememberSaveable {
        mutableStateOf(0)
    }





    if(childDialogOpen){
        Dialog(
            onDismissRequest = { setChildDialogOpen(false) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
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

                SegmentedControl(
                    items = classList,
                    defaultSelectedItemIndex = selectedChildClassIndex,
                    useFixedWidth = true,
                    itemWidth = 140.dp,
                ){
                    Log.e("CustomToggle", "Selected item : ${classList[it]}")
                    setSelectedChildClassIndex(it)
                }
                Spacer(modifier = Modifier.height(30.dp))
                LaunchedEffect(selectedChildrenIndex){

                }
                SegmentedControl(
                    items = childList[selectedChildClassIndex],
                    defaultSelectedItemIndex = selectedChildrenIndex,
                    useFixedWidth = true,
                    itemWidth = (560/childList[selectedChildClassIndex].size).dp,
                ){
                    Log.e("CustomToggle", "Selected item : ${childList[selectedChildClassIndex][it]}")
                    setSelectedChildrenIndex(it)
                }
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.light_gray)
                    ),
                    onClick = {
                        Log.e("아이 선택", "selectedChildName : ${childList[selectedChildClassIndex][selectedChildrenIndex]}")
                        viewModel.setSelectedChildName(childList[selectedChildClassIndex][selectedChildrenIndex])
                        viewModel.setSelectedChildClass(classList[selectedChildClassIndex])
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



                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    columns = GridCells.Fixed(count = 4),
                    verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                    contentPadding = PaddingValues(all = 10.dp)
                ){

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
                            .width(100.dp)
                            .height(50.dp)
                        ){
                            Column(modifier = Modifier.fillMaxSize()) {
                                Text(text = selectedChildClass)
                                Text(text = selectedChildName)
                            }

                        }
                        IconButton(onClick = {
                            setChildDialogOpen(true)
                        }) {
                            Icon(painter = painterResource(id = R.drawable.icon_user), contentDescription = null)
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

                composable(AllDestinations.DATA) {
                    DataScreen(
                        ltoViewModel = ltoViewModel,
                        childViewModel = childViewModel,
                        stoViewModel = stoViewModel
                    )
                }

                composable(AllDestinations.GAME) {
                    GameScreen()
                }
            }
        }
    }
}

@Composable
fun SegmentedControl(
    items: List<String>,
    defaultSelectedItemIndex: Int = 0,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    cornerRadius: Int = 10,
    @ColorRes color: Int = R.color.light_gray,
    onItemSelection: (selectedItemIndex: Int) -> Unit
){
    var selectedIndex by rememberSaveable {
        mutableStateOf(defaultSelectedItemIndex)
    }

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
                                .zIndex(if (selectedIndex == index) 1f else 0f)
                        } else {
                            Modifier
                                .wrapContentSize()
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedIndex == index) 1f else 0f)
                        }
                    }
                    else -> {
                        if(useFixedWidth){
                            Modifier
                                .width(itemWidth)
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedIndex == index) 1f else 0f)
                        } else {
                            Modifier
                                .wrapContentSize()
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedIndex == index) 1f else 0f)
                        }
                    }
                },
                onClick = {
                    selectedIndex = index
                    onItemSelection(selectedIndex)
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
                    1.dp, if(selectedIndex == index) {
                        colorResource(id = color)
                    } else {
                        colorResource(id = color).copy(alpha = 0.75f)
                    }
                ),
                colors = if (selectedIndex == index){
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
                    text = item,
                    fontWeight = FontWeight.Normal,
                    color = if (selectedIndex == index){
                        Color.White
                    } else {
                        colorResource(id = color).copy(alpha = 0.9f)
                    }
                )
            }

        }
    }
}