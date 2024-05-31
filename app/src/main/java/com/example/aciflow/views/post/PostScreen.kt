package com.example.aciflow.views.post

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(navController: NavController, postViewModel: PostViewModel = viewModel()) {
    val description by postViewModel.description.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("New Post") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                postViewModel.publishPost()
                navController.popBackStack()
            }) {
                Icon(Icons.Default.Send, contentDescription = "Publish Post")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = description,
                onValueChange = { postViewModel.updateDescription(it) },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
        }
    }
}
