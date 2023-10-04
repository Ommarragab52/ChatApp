package com.example.chatapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.chatapp.navigation.ChatNavigation
import com.example.chatapp.ui.theme.ChatAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                Scaffold {
                    ChatApp()
            }
        }
    }
}

@Composable
fun ChatApp() {
    ChatNavigation()
}
