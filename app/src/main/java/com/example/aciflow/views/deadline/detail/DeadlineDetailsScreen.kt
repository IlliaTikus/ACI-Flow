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
import com.example.aciflow.views.deadline.detail.DeadlineDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlineDetailScreen(deadlineTitle: String, navController: NavController, deadlineDetailViewModel: DeadlineDetailViewModel = viewModel()) {
    val deadline by deadlineDetailViewModel.getDeadline(deadlineTitle).collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Deadline Details") }) }
    ) { innerPadding ->
        deadline?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(text = it.title, style = MaterialTheme.typography.titleSmall)
                Text(text = "Date: ${it.date}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Description: ${it.description}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
