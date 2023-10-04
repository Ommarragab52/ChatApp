package com.example.chatapp.chatRoom

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatapp.DataUtils
import com.example.chatapp.R
import com.example.chatapp.component.AppBar
import com.example.chatapp.database.model.Message
import com.example.chatapp.database.model.Room
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ChatRoomContent(
    viewModel: ChatRoomViewModel,
    navController: NavHostController
) {
    viewModel.room = navController.previousBackStackEntry?.savedStateHandle?.get<Room>("room")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.background), contentScale = ContentScale.FillBounds
            )
    ) {
        Scaffold(containerColor = Color.Transparent,
            topBar = { AppBar(title = viewModel.room?.name ?: "room Name") },
            bottomBar = { SendMessageBox(viewModel) }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it)
            ) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                        .background(Color.White, shape = RoundedCornerShape(10.dp))

                ) {
                    ChatRoomLazyColumn(viewModel)
                }
            }
        }
    }
}


@Composable
fun ChatRoomLazyColumn(viewModel: ChatRoomViewModel) {

    val driverState = remember {
        viewModel.getMessagesFromFirestore()
        derivedStateOf { viewModel.messagesListState.value }
    }

    LazyColumn(modifier = Modifier.fillMaxSize(), reverseLayout = true) {
        items(driverState.value.size) { index ->
            val item = driverState.value[index]
            if (DataUtils.user?.id == item.senderId)
                SentMessageCard(item)
            else ReceivedMessageCard(item)
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun SentMessageCard(message: Message) {
    val messageDate = Date(message.timeDate!!)
    val timeFormat = SimpleDateFormat("hh:mm a")
    val timeDate = timeFormat.format(messageDate)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 1.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = timeDate, modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Bottom)
        )
        Text(
            text = message.content!!,
            style = TextStyle(color = colorResource(id = R.color.white)),
            modifier = Modifier
                .background(
                    colorResource(id = R.color.blue),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 0.dp
                    )
                )
                .padding(10.dp)
        )
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun ReceivedMessageCard(message: Message) {
    val messageDate = Date(message.timeDate!!)
    val timeFormat = SimpleDateFormat("hh:mm a")
    val timeDate = timeFormat.format(messageDate)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 1.dp)
    ) {
        Text(
            text = message.senderName!!.replaceFirstChar { it.uppercase() },
            style = TextStyle(colorResource(id = R.color.grey1), fontSize = 16.sp),
            modifier = Modifier.padding(start = 2.dp, bottom = 2.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = message.content!!,
                style = TextStyle(color = colorResource(id = R.color.black2), fontSize = 18.sp),
                modifier = Modifier
                    .background(
                        colorResource(id = R.color.grey2),
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 16.dp
                        )
                    )
                    .padding(10.dp)
            )
            Text(
                text = timeDate, modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.Bottom)
            )

        }
    }

}

@Composable
fun SendMessageBox(viewModel: ChatRoomViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
    ) {
        Box(modifier = Modifier.width(280.dp)) {
            OutlinedTextField(
                value = viewModel.messageFieldState.value,
                onValueChange = { viewModel.messageFieldState.value = it },
                label = { Text(text = "Type a message") },
                shape = RoundedCornerShape(topEnd = 16.dp),
            )
        }

        Spacer(modifier = Modifier.width(5.dp))
        Button(
            onClick = {
                viewModel.addMessageToFirestore()
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .height(55.dp)
                .align(Alignment.Bottom)
        ) {
            Text(text = "Send", modifier = Modifier.padding(end = 16.dp))
            Icon(imageVector = Icons.Default.Send, contentDescription = "Send Icon")

        }
    }
}
