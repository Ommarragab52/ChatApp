package com.example.chatapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.chatapp.R

@Composable
fun LoadingDialog(showLoadingState: MutableState<Boolean>) {
    if (showLoadingState.value) {
        Dialog(onDismissRequest = {
        }) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(50.dp))
                    .padding(5.dp),
                color = colorResource(id = R.color.blue),
                strokeWidth = 8.dp
            )
        }
    }
}

@Composable
fun MessageDialog(showMessageState: MutableState<String>, onOkClick: () -> Unit) {
    if (showMessageState.value.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = {
                showMessageState.value = ""
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onOkClick()
                        showMessageState.value = ""
                    }
                ) {
                    Text("OK")
                }
            },
            text = { Text(text = showMessageState.value, style = TextStyle(fontSize = 20.sp)) }
        )
    }
}