package com.example.aciflow.views.deadlines

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aciflow.common.ext.toFormattedString
import com.example.aciflow.model.DeadlinePriority
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService
import com.example.aciflow.nav.Screen
import java.time.LocalDateTime

@Composable
fun DeadlinesScreen(navController: NavController, onDeadline: () -> Unit) {
    val factory = DeadlinesViewModelFactory(
        StorageService.getStorageService(),
        AccountService.getAccountService(),
    )
    val viewModel: DeadlinesViewModel = viewModel(factory = factory)
    DeadlinesScreenContent(viewModel = viewModel, navController = navController) { onDeadline() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlinesScreenContent(viewModel: DeadlinesViewModel, navController: NavController, onDeadline: () -> Unit) {
    val deadlines by viewModel.deadlines.collectAsState()
    val deadlinesByTag = deadlines.groupBy { it.tag }

    Scaffold(topBar = { TopAppBar(title = { Text("Deadlines") }) }, floatingActionButton = {
        FloatingActionButton(onClick = { onDeadline() }) {
            Icon(Icons.Default.Add, contentDescription = "Add Deadline")
        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            deadlinesByTag.forEach { (tag, deadlines) ->
                item {
                        Spacer(modifier = Modifier.height(16.dp))
                    Text(text = tag ?: "No Tag", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(deadlines) { deadline ->
                    val isOverdue = deadline.dueDate?.let { it.time < System.currentTimeMillis() } == true
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                navController.navigate("${Screen.DeadlineDetail}/${deadline.id}")
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = deadline.title.orEmpty(),
                                    style = if (isOverdue) MaterialTheme.typography.titleSmall.copy(color = Color.Gray) else MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    text = deadline.dueDate?.toFormattedString().orEmpty(),
                                    style = if (isOverdue) MaterialTheme.typography.titleSmall.copy(color = Color.Gray) else MaterialTheme.typography.titleSmall
                                )
                            }
                            when (deadline.priority.toString()) {
                                DeadlinePriority.Low.toString() -> Text("🟢", fontSize = MaterialTheme.typography.titleSmall.fontSize)
                                DeadlinePriority.Important.toString() -> Text("🟡", fontSize = MaterialTheme.typography.titleSmall.fontSize)
                                DeadlinePriority.Urgent.toString() -> Text("🔴", fontSize = MaterialTheme.typography.titleSmall.fontSize)
                            }
                        }
                    }
                }
            }
        }
    }
}


