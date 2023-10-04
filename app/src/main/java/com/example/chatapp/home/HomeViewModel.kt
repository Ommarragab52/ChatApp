package com.example.chatapp.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.firestore.getRoomsFromFirestoreDB
import com.example.chatapp.database.model.Room
import com.example.chatapp.navigation.ChatScreens
import com.google.gson.Gson


class HomeViewModel : ViewModel() {
    val selectedTabIndex = mutableStateOf(0)
    val showLoading = mutableStateOf(false)
    var events = mutableStateOf<ChatScreens?>(null)
    var roomsList = mutableStateOf<List<Room>>(listOf())
    fun getRoomsFromFirestore(){
        showLoading.value=true
        getRoomsFromFirestoreDB({querySnapshot->
            roomsList.value = querySnapshot.toObjects(Room::class.java)
            showLoading.value=false
        },{

        })
    }

    fun convertToJson(room: Room) : String {
        val gson = Gson().newBuilder().create()
        return gson.toJson(room)
    }
}