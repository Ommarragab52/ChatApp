package com.example.chatapp.splash

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.DataUtils
import com.example.chatapp.database.firestore.getUserFromFirestoreDB
import com.example.chatapp.database.model.User
import com.example.chatapp.navigation.ChatScreens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashViewModel : ViewModel() {
    private val auth = Firebase.auth
    val events = mutableStateOf<ChatScreens?>(null)

    private fun isCurrentUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun checkUser() {
        if (isCurrentUserLoggedIn()) {
            getUserFromFirestoreDB(auth.currentUser?.uid!!, {
                events.value=ChatScreens.HomeScreen
                DataUtils.user = it.toObject(User::class.java)
                DataUtils.firebaseUser = auth.currentUser
            }, {
                events.value = ChatScreens.LoginScreen
            })
        }else events.value = ChatScreens.LoginScreen

    }


}