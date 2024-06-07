package com.example.aciflow.views.deadline.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import com.example.aciflow.R
import com.example.aciflow.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDeadlineScreen(
    navController: NavController,
    editDeadlineViewModel: EditDeadlineViewModel = viewModel()
) {
    val title by editDeadlineViewModel.title.collectAsState()
    val date by editDeadlineViewModel.date.collectAsState()
    val description by editDeadlineViewModel.description.collectAsState()

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
                title = { Text("Edit Deadline") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editDeadlineViewModel.saveDeadline()
                navController.popBackStack()
            }) {
                Icon(Icons.Default.Check, contentDescription = "Save Deadline")
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
                value = title,
                onValueChange = { editDeadlineViewModel.updateTitle(it) },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            OutlinedTextField(
                value = date,
                onValueChange = { editDeadlineViewModel.updateDate(it) },
                label = { Text("Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            OutlinedTextField(
                value = description,
                onValueChange = { editDeadlineViewModel.updateDescription(it) },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}
