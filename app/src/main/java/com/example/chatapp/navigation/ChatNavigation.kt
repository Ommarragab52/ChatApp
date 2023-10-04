package com.example.chatapp.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chatapp.addRoom.AddRoomContent
import com.example.chatapp.addRoom.AddRoomViewModel
import com.example.chatapp.chatRoom.ChatRoomContent
import com.example.chatapp.chatRoom.ChatRoomViewModel
import com.example.chatapp.home.HomeContent
import com.example.chatapp.home.HomeViewModel
import com.example.chatapp.login.LoginContent
import com.example.chatapp.login.LoginViewModel
import com.example.chatapp.register.RegisterViewModel
import com.example.chatapp.register.SignupContent
import com.example.chatapp.splash.SplashScreen
import com.example.chatapp.splash.SplashViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun ChatNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ChatScreens.SplashScreen.name) {
        composable(ChatScreens.SplashScreen.name) {
            val viewModel: SplashViewModel = viewModel()
            SplashScreen(viewModel, navController)
        }
        composable(ChatScreens.RegisterScreen.name) {
            val viewModel: RegisterViewModel = viewModel()
            SignupContent(viewModel, navController)
        }
        composable(ChatScreens.LoginScreen.name) {
            val viewModel: LoginViewModel = viewModel()
            LoginContent(viewModel, navController)
        }
        composable(ChatScreens.HomeScreen.name) {
            val viewModel: HomeViewModel = viewModel()
            HomeContent(viewModel, navController)
        }
        composable(ChatScreens.AddRoomScreen.name) {
            val viewModel: AddRoomViewModel = viewModel()
            AddRoomContent(viewModel, navController)
        }
        composable(route = "${ChatScreens.ChatRoomScreen.name}/{room}",
            arguments = listOf(navArgument("room")
            {
                type = NavType.StringType
            }
            )) {
            val viewModel: ChatRoomViewModel = viewModel()
            val roomJson = it.arguments?.getString("room")
            ChatRoomContent(viewModel = viewModel, navController = navController, roomJson!!)

        }
    }
}


