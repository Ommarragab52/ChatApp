package com.example.chatapp.addRoom

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.component.AppBar
import com.example.chatapp.component.LoadingDialog
import com.example.chatapp.component.MessageDialog
import com.example.chatapp.navigation.ChatScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddRoomContent(viewModel: AddRoomViewModel, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(R.drawable.background), contentScale = ContentScale.FillBounds)
    ) {
        Scaffold(containerColor = Color.Transparent, topBar = { AppBar("ChatApp") }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(top = 50.dp)
                    .background(Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(0.85f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.elevatedCardElevation(20.dp)
                ) {

                    Text(
                        modifier = Modifier
                            .padding(20.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "Create New Room",
                        style = TextStyle(fontSize = 18.sp, color = Color.Black)
                    )
                    Image(
                        modifier = Modifier
                            .padding(20.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.ic_create_room),
                        contentDescription = "",
                        alignment = Alignment.Center
                    )

                    RoomTextField(
                        viewModel.roomName,
                        viewModel.roomNameError.value,
                        "Enter Room Name"
                    )
                    RoomTextField(
                        viewModel.roomDesc,
                        viewModel.roomDescError.value,
                        "Enter Room Description"
                    )
                    ExposedMenu(viewModel)
                    Button(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .fillMaxHeight(0.3F)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            viewModel.addRoomToFirestore()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue))
                    ) {
                        Text(text = "Create")

                    }
                }
                LoadingDialog(showLoadingState = viewModel.showLoading)
                MessageDialog(showMessageState = viewModel.showMessage){
                    navController.navigate(ChatScreens.HomeScreen.name)
                }
            }
        }
    }
}

@Composable
fun RoomTextField(state: MutableState<String>, isError: String, label: String) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        value = state.value,
        onValueChange = { state.value = it },
        label = {
            Text(
                text = label,
                style = TextStyle(fontSize = 16.sp, color = colorResource(id = R.color.grey2))
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Black
        ), isError = isError.isNotEmpty()
    )
    if (isError.isNotEmpty()) {
        Text(
            text = isError, style = TextStyle(color = Color.Red), modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedMenu(viewModel: AddRoomViewModel) {
    ExposedDropdownMenuBox(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 10.dp), expanded = viewModel.isExpanded.value,
        onExpandedChange = { viewModel.isExpanded.value = !viewModel.isExpanded.value }) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = colorResource(id = R.color.black2)
                    )
                )
                .menuAnchor(),
            readOnly = true,
            value = viewModel.selectedItem.value.name ?: "",
            onValueChange = { },
            leadingIcon = {
                Image(
                    painter = painterResource(id = viewModel.selectedItem.value.imageId!!),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.isExpanded.value) },
            placeholder = {
                Text(
                    text = "Select Room Category",
                    style = TextStyle(fontSize = 16.sp, color = colorResource(id = R.color.grey2))
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Black
            )
        )
        ExposedDropdownMenu(
            expanded = viewModel.isExpanded.value,
            onDismissRequest = {
                viewModel.isExpanded.value = false
            }
        ) {
            viewModel.categoriesList.forEachIndexed { _, category ->
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.padding(vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = category.imageId!!),
                                contentDescription = "",
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(text = category.name!!, style = TextStyle(fontSize = 16.sp))
                        }

                    },
                    onClick = {
                        viewModel.selectedItem.value = category
                        viewModel.isExpanded.value = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}