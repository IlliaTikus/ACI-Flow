package com.example.aciflow.views.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aciflow.AppState
import com.example.aciflow.R
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService
import com.example.aciflow.theme.AppTheme
import com.example.aciflow.views.profile.ProfileViewModel
import com.example.aciflow.views.profile.ProfileViewModelFactory
import com.google.firebase.firestore.DocumentReference

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(navController: NavController, appState: AppState) {
    val factory = PostViewModelFactory(accountService = AccountService.getAccountService(), storageService  = StorageService.getStorageService(), appState = appState)
    val viewModel: PostViewModel = viewModel(factory = factory)

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(AppTheme.dimens.paddingNormal)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                },
                title = { Text("New Post") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.publishPost()
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
                value = uiState.description,
                onValueChange = { newValue ->
                    viewModel.updateDescription(newValue)
                },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
        }
    }
}
