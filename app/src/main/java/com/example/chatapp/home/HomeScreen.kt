package com.example.chatapp.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.component.AppBar
import com.example.chatapp.component.LoadingDialog
import com.example.chatapp.database.model.Category.Companion.getCategoryImageId
import com.example.chatapp.database.model.Room
import com.example.chatapp.navigation.ChatScreens


@Composable
fun HomeContent(viewModel: HomeViewModel, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.background), contentScale = ContentScale.FillBounds
            )
    ) {
        Scaffold(containerColor = Color.Transparent,
            topBar = { AppBar(title = "ChatApp") },
            floatingActionButton = { FAB(navController) }) {
            Column(modifier = Modifier.padding(paddingValues = it)) {
                Tabs(viewModel)
                RoomsContent(viewModel, navController)
                LoadingDialog(showLoadingState = viewModel.showLoading)
            }
        }
    }
}

@Composable
fun RoomsContent(viewModel: HomeViewModel, navController: NavHostController) {
    val activity = LocalContext.current as Activity
    BackHandler {
        activity.moveTaskToBack(true)
    }
    viewModel.getRoomsFromFirestore()
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(viewModel.roomsList.value.size) { item ->
            CardItem(viewModel.roomsList.value[item]) {
                navController.currentBackStackEntry?.savedStateHandle?.apply {
                    set("room", viewModel.roomsList.value[item])
                }
                navController.navigate(ChatScreens.ChatRoomScreen.name) {
                    popUpTo(ChatScreens.HomeScreen.name) { inclusive}
                }
            }
        }

    }
}

@Composable
fun CardItem(room: Room, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 16.dp)
            .width(80.dp)
            .height(200.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(5.dp),
    ) {

        Image(
            painter = painterResource(
                id = getCategoryImageId(room.categoryId ?: "MOVIES")
            ),
            contentDescription = "",
            modifier = Modifier
                .padding(10.dp)
                .size(90.dp)
                .align(Alignment.CenterHorizontally)

        )
        Text(
            text = room.name!!,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = colorResource(id = R.color.grey1),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )
        Text(
            text = "Members",
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            color = colorResource(id = R.color.black2),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )
    }
}


@Composable
fun FAB(navController: NavHostController) {
    FloatingActionButton(
        onClick = {
            navController.navigate(ChatScreens.AddRoomScreen.name)
        },
        shape = RoundedCornerShape(25.dp),
        containerColor = colorResource(id = R.color.blue),
    ) {
        Icon(
            imageVector = Icons.Default.Add, contentDescription = "Add icon",
            tint = Color.White
        )
    }
}

@Composable
fun Tabs(viewModel: HomeViewModel) {
    val tabsList = listOf("My Rooms", "Browse")
    TabRow(modifier = Modifier.padding(20.dp),
        selectedTabIndex = viewModel.selectedTabIndex.intValue,
        containerColor = Color.Transparent,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(
                    tabPositions[viewModel.selectedTabIndex.intValue]
                ), height = 3.dp,
                color = Color.White
            )
        },
        divider = {}
    ) {
        tabsList.forEachIndexed { index, title ->
            Tab(selected = viewModel.selectedTabIndex.intValue == index,
                onClick = {
                    viewModel.selectedTabIndex.intValue = index
                    when (index) {
                        0 -> {}
                        1 -> {}
                    }
                })
            {

                Text(text = title, style = TextStyle(fontSize = 18.sp))
                Spacer(modifier = Modifier.size(5.dp))
            }
        }
    }

}
