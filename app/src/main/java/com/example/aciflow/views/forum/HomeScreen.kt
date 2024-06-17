package com.example.aciflow.views.forum

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aciflow.R
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService

@Composable
fun HomeScreen(onPost: () -> Unit) {
    val factory = HomeScreenViewModelFactory(
        storageService = StorageService.getStorageService(),
        accountService = AccountService.getAccountService()
    )
    val viewModel: HomeScreenViewModel = viewModel(factory = factory)

    HomeScreenContent(viewModel = viewModel) { onPost() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(viewModel: HomeScreenViewModel, onPost: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Welcome to ACI Flow!") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onPost() }) {
                Icon(Icons.Default.Add, contentDescription = "Add Post")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.banner2),
                contentDescription = "ACI Flow Logo",
                modifier = Modifier
            )

            uiState.posts.forEach { post ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = post.authorName.orEmpty(),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = post.content.orEmpty(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}