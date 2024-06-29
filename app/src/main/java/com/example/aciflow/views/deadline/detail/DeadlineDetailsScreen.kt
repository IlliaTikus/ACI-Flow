package com.example.aciflow.views.deadline.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aciflow.AppState
import com.example.aciflow.R
import com.example.aciflow.common.ext.toFormattedString
import com.example.aciflow.model.DeadlinePriority
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(16.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                },
                title = { Text("Add Deadline") }
            )
        },
    ) { innerPadding ->
        deadline?.let { deadline ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(text = deadline.title ?: "", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = deadline.dueDate?.toFormattedString().orEmpty(),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Description: ${deadline.description ?: ""}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(32.dp))

                deadline.tag?.let { tag ->
                    Text(text = "Tag: $tag", style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                deadline.priority?.let { priority ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Priority:", style = MaterialTheme.typography.titleSmall)
                        when (priority.toString()) {
                            DeadlinePriority.Low.toString() -> Text("🟢", fontSize = MaterialTheme.typography.titleSmall.fontSize)
                            DeadlinePriority.Important.toString() -> Text("🟡", fontSize = MaterialTheme.typography.titleSmall.fontSize)
                            DeadlinePriority.Urgent.toString() -> Text("🔴", fontSize = MaterialTheme.typography.titleSmall.fontSize)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}