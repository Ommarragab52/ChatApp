package com.example.chatapp.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.DataUtils
import com.example.chatapp.database.firestore.addUserToFirestoreDB
import com.example.chatapp.database.model.User
import com.example.chatapp.navigation.ChatScreens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel : ViewModel() {

    val nameState = mutableStateOf("")
    val nameError = mutableStateOf("")
    val emailState = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val showLoading = mutableStateOf(false)
    val showMessage = mutableStateOf("")
    val events = mutableStateOf<ChatScreens?>(null)
    private val auth = Firebase.auth

    private fun validateFields(): Boolean {
        if (nameState.value.isEmpty() || nameState.value.isBlank()) {
            nameError.value = "Name Required"
            return false
        } else nameError.value = ""

        if (emailState.value.isEmpty() || emailState.value.isBlank()) {
            emailError.value = "Email Required"
            return false
        } else emailError.value = ""

        if (passwordState.value.isEmpty() || passwordState.value.isBlank()) {
            passwordError.value = "Password Required"
            return false
        } else passwordError.value = ""

        return true
    }

    fun setAuthDataToFireBase() {
        if (!validateFields()) return
        showLoading.value = true
        auth.createUserWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showLoading.value = false
                    addUserToFirestore(task.result.user?.uid!!)

                } else {
                    showLoading.value = false
                    showMessage.value = task.exception?.localizedMessage ?: "Something went wrong!!"
                }

            }
    }

    private fun addUserToFirestore(uid:String) {
        showLoading.value = true
        addUserToFirestoreDB(
            User(
                id =uid,
                name = nameState.value,
                email = emailState.value
            ), onCompleteListener = {
                val user = User(uid,nameState.value,emailState.value)
                DataUtils.user = user
                DataUtils.firebaseUser = auth.currentUser
                showLoading.value = false
                events.value = ChatScreens.HomeScreen
            }, onFailureListener = {
                showLoading.value = false
                showMessage.value= it.localizedMessage?.toString() ?: "Something went wrong"
            }
        )
    }

}