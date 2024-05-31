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
import com.example.aciflow.nav.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlinesScreen(
    navController: NavController,
    deadlinesViewModel: DeadlinesViewModel = viewModel()
) {
    val deadlines by deadlinesViewModel.deadlines.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Deadlines") }) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            deadlines.forEach { deadline ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate(Screen.DeadlineDetail.createRoute(deadline.title))
                        },
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = deadline.title, style = MaterialTheme.typography.titleSmall)
                        Text(text = deadline.date, style = MaterialTheme.typography.bodySmall)
                        if (deadline.isUrgent) {
                            Text(text = "!", color = MaterialTheme.colorScheme.error)
                        }
                    }

                }
            }
        }
    }
}