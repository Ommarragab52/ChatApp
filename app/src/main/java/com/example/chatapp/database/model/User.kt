package com.example.chatapp.database.model

data class User(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null
) {
    companion object {
        const val COLLECTION_NAME = "Users"
    }
}
