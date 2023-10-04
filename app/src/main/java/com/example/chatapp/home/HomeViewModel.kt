package com.example.chatapp.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.firestore.getRoomsFromFirestoreDB
import com.example.chatapp.database.model.Room


class HomeViewModel : ViewModel() {
    val selectedTabIndex = mutableIntStateOf(0)
    val showLoading = mutableStateOf(false)
    var roomsList = mutableStateOf<List<Room>>(listOf())
    fun getRoomsFromFirestore() {
        showLoading.value = true
        getRoomsFromFirestoreDB({ querySnapshot ->
            roomsList.value = querySnapshot.toObjects(Room::class.java)
            showLoading.value = false
        }, {
        })
    }


}