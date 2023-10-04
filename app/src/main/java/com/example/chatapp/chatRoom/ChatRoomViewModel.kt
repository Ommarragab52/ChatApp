package com.example.chatapp.chatRoom

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.DataUtils
import com.example.chatapp.database.firestore.addMessageToFirestoreDB
import com.example.chatapp.database.firestore.getMessagesFromFirestoreDB
import com.example.chatapp.database.model.Message
import com.example.chatapp.database.model.Room
import com.google.firebase.firestore.DocumentChange
import java.util.Date

class ChatRoomViewModel : ViewModel() {
    var room: Room? = null
    val messageFieldState = mutableStateOf("")
    val messagesListState = mutableStateOf<List<Message>>(listOf())


    fun addMessageToFirestore() {
        if (messageFieldState.value.isEmpty() || messageFieldState.value.isBlank())
            return
        val message = Message(
            content = messageFieldState.value,
            timeDate = Date().time,
            senderId = DataUtils.user?.id,
            senderName = DataUtils.user?.name,
            roomId = room?.roomId
        )

        addMessageToFirestoreDB(
            roomId = room?.roomId!!, message,
            onSuccessListener = {
                messageFieldState.value = ""
            },
            onFailureListener = {
            })

    }


    fun getMessagesFromFirestore() {
        getMessagesFromFirestoreDB(roomId = room?.roomId!!, listener = { snapshots, e ->
            if (e != null) {
                Log.e("Tag", "${e.message}")
                return@getMessagesFromFirestoreDB
            }
            val mutableList = mutableListOf<Message>()
            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        mutableList.add(dc.document.toObject(Message::class.java))
                    }

                    else -> {}
                }
            }

            val newList = mutableListOf<Message>()
            newList.addAll(mutableList)
            newList.addAll(messagesListState.value)
            messagesListState.value = newList

        })
    }

}