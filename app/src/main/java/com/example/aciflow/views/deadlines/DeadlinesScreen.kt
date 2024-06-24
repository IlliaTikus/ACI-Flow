package com.example.aciflow.views.deadlines

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aciflow.common.ext.toFormattedString
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService
import com.example.aciflow.nav.Screen

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
    Scaffold(topBar = { TopAppBar(title = { Text("Deadlines") }) }, floatingActionButton = {
        FloatingActionButton(onClick = { onDeadline() }) {
            Icon(Icons.Default.Add, contentDescription = "Add Deadline")
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            deadlines.forEach { deadline ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                    navController.navigate("${Screen.DeadlineDetail}/$deadline.id")
                }    // todo: navigate to deadline detail screen with deadline id
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = deadline.title.orEmpty(),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = deadline.dueDate?.toFormattedString().orEmpty(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                }
            }
        }
    }
}


