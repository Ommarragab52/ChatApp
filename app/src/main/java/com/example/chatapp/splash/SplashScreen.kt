package com.example.chatapp.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.navigation.ChatScreens


@Composable
fun SplashScreen(viewModel: SplashViewModel, navController: NavHostController) {
    SplashContent()
    viewModel.checkUser()
    when (viewModel.events.value) {
        ChatScreens.LoginScreen ->
            navController.navigate(ChatScreens.LoginScreen.name)
        else ->
            navController.navigate(ChatScreens.HomeScreen.name)
    }

}

@Composable
fun SplashContent() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (logo, signature) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Splash logo",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)

                }
                .fillMaxSize(0.3F)
        )
        Image(
            painter = painterResource(id = R.drawable.signature),
            contentDescription = "Signature",
            modifier = Modifier
                .constrainAs(signature) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(0.5F)
                .fillMaxHeight(0.2F)
        )
    }
}
