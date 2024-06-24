package com.example.aciflow.views.deadline_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aciflow.AppState
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService
import com.example.aciflow.views.deadline.detail.DeadlineDetailViewModel
import com.example.aciflow.views.deadline.detail.DeadlineDetailViewModelFactory
import com.example.aciflow.views.post.PostViewModel
import com.example.aciflow.views.post.PostViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlineDetailScreen(
    navController: NavController,
    appState: AppState,
    deadlineId: String
) {
    val factory = DeadlineDetailViewModelFactory(
        accountService = AccountService.getAccountService(),
        storageService = StorageService.getStorageService()
    )
    val viewModel: DeadlineDetailViewModel = viewModel(factory = factory)
    viewModel.getDeadlineById(deadlineId)

    val deadline by viewModel.deadline.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Deadline Details") }) }
    ) { innerPadding ->
        deadline?.let { deadline ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(text = deadline.title ?: "", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Date: ${deadline.dueDate ?: ""}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Description: ${deadline.description ?: ""}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(16.dp))

                deadline.tag?.let { tag ->
                    Text(text = "Tag: $tag", style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                deadline.priority?.let { priority ->
                    Text(text = "Priority: $priority", style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
