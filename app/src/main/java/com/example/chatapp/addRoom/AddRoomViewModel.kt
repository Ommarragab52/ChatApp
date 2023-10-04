package com.example.chatapp.addRoom

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.firestore.addRoomToFirestoreDB
import com.example.chatapp.database.model.Category.Companion.getCategoriesList
import com.example.chatapp.database.model.Room

class AddRoomViewModel : ViewModel() {
    val roomName = mutableStateOf("")
    val roomNameError = mutableStateOf("")
    val roomDesc = mutableStateOf("")
    val roomDescError = mutableStateOf("")
    var isExpanded = mutableStateOf(false)
    val categoriesList = getCategoriesList()
    var selectedItem = mutableStateOf(categoriesList[0])
    val showLoading = mutableStateOf(false)
    val showMessage = mutableStateOf("")

    private fun validateRoomAuthFields(): Boolean {
        if (roomName.value.isEmpty() || roomName.value.isBlank()) {
            roomNameError.value = "Room Name Required"
            return false
        } else roomNameError.value = ""
        if (roomDesc.value.isEmpty() || roomDesc.value.isBlank()) {
            roomDescError.value = "Room Descrption Required"
            return false
        } else roomDescError.value = ""
        return true
    }

    fun addRoomToFirestore() {
        if (!validateRoomAuthFields()) return
        showLoading.value = true
        val room = Room(null, roomName.value, roomDesc.value, selectedItem.value.id)
        addRoomToFirestoreDB(room,
            {
                showLoading.value = false
                showMessage.value = "Room Added Successfully"
            }, {
                showLoading.value = false
                showMessage.value = it.localizedMessage?.toString() ?: "Something Went Wrong!"
            })
    }
}