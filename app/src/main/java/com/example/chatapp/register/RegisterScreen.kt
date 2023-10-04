package com.example.chatapp.register

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.component.MessageDialog
import com.example.chatapp.navigation.ChatScreens

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun SignupContent(
    viewModel: RegisterViewModel,
    navController: NavHostController
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Create Account",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                    )
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.fillMaxHeight(0.4F))
            TextFieldItem(viewModel.nameState, "First Name", viewModel.nameError, false)
            TextFieldItem(viewModel.emailState, "E-mail Address", viewModel.emailError, false)
            TextFieldItem(viewModel.passwordState, "Password", viewModel.passwordError, true)
            ChatButton("Create Account") {
                viewModel.setAuthDataToFireBase()
            }
            TextButton(onClick = {
                navController.navigate(ChatScreens.LoginScreen.name)
            }) {
                Text(text = "Already have an account?", color = colorResource(id = R.color.black2))
            }
            LoadingDialog()
            MessageDialog(showMessageState = viewModel.showMessage) {}
        }
    }
    when (viewModel.events.value){
        ChatScreens.HomeScreen ->{navController.navigate(ChatScreens.HomeScreen.name)}
        else -> {}
    }
}

@Composable
fun ChatButton(title: String, onClick: () -> Unit) {
    TextButton(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = title)
        Spacer(Modifier.size(180.dp))
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = ""
        )

    }
}

@Composable
fun TextFieldItem(
    value: MutableState<String>,
    label: String,
    stateError: MutableState<String>,
    isPassword: Boolean
) {
    TextField(
        value = value.value,
        onValueChange = { newValue -> value.value = newValue },
        label = {
            Text(
                text = label,
                style = TextStyle(color = colorResource(id = R.color.grey), fontSize = 14.sp)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = colorResource(
                id = R.color.blue
            ),
        ),
        textStyle = TextStyle(color = colorResource(id = R.color.black2), fontSize = 20.sp),
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        isError = stateError.value.isNotEmpty(),
        keyboardOptions = if (isPassword)
            KeyboardOptions(keyboardType = KeyboardType.Password)
        else KeyboardOptions.Default,
        visualTransformation = if (isPassword) PasswordVisualTransformation()
        else VisualTransformation.None
    )
    if (stateError.value.isNotEmpty()) {
        Text(
            text = stateError.value,
            style = TextStyle(fontSize = 14.sp, color = Color.Red),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun LoadingDialog(viewModel: RegisterViewModel = viewModel()) {
    if (viewModel.showLoading.value) {
        Dialog(onDismissRequest = {

        }) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(50.dp))
                    .padding(5.dp),
                color = colorResource(id = R.color.blue),
                strokeWidth = 8.dp
            )
        }

    }

}
