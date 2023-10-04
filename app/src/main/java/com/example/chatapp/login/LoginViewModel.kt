package com.example.chatapp.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.chatapp.DataUtils
import com.example.chatapp.database.firestore.getUserFromFirestoreDB
import com.example.chatapp.database.model.User
import com.example.chatapp.navigation.ChatScreens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    val emailState = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val showLoading = mutableStateOf(false)
    val showMessage = mutableStateOf("")
    val events = mutableStateOf<ChatScreens?>(null)



    private val auth = Firebase.auth

    private fun validateFields(): Boolean {
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
        auth.signInWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getUserFromFirestore(task.result.user?.uid!!)
                } else {
                    showLoading.value = false
                    showMessage.value = task.exception?.localizedMessage ?: "Something went wrong!!"
                }
            }
    }

    private fun getUserFromFirestore(uid: String) {
        showLoading.value = true
        getUserFromFirestoreDB(
            uid,
            onSuccessListener = {
                showLoading.value = false
                val user = it.toObject(User::class.java)
                DataUtils.user = user
                DataUtils.firebaseUser = auth.currentUser
                events.value = ChatScreens.HomeScreen
            },
            onFailureListener = {
                showLoading.value = false
                showMessage.value = it.localizedMessage ?: "Something went wrong!!"
            })
    }

}