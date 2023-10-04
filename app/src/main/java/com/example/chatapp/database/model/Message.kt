package com.example.chatapp.database.model

data class Message(
    var id: String? = null,
    val content: String? = null,
    val timeDate: Long? = null,
    val senderId: String? = null,
    val senderName: String? = null,
    val roomId: String? = null
) {
    companion object {
        const val COLLECTION_NAME = "Messages"
    }
}
