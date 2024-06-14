package com.example.aciflow.views.group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService
import kotlinx.coroutines.flow.first

@Composable
fun GroupScreen() {
    val factory = GroupViewModelFactory(
        StorageService.getStorageService(),
        AccountService.getAccountService()
    )
    val viewModel: GroupViewModel = viewModel(factory = factory)

    GroupScreenContent(viewModel = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreenContent(viewModel: GroupViewModel){
    val members by viewModel.groupMembers.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Group") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            members.forEach { member ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = member, style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        }
    }
}