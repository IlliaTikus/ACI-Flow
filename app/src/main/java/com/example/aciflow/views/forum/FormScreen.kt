package com.example.aciflow.views.forum

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Card
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumScreen(navController: NavController, forumViewModel: ForumViewModel = viewModel()) {
    val posts by forumViewModel.posts.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Forum") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            posts.forEach { post ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = post.user, style = MaterialTheme.typography.titleSmall)
                        Text(text = post.content, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
