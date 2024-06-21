package com.example.aciflow.views.deadline.edit

import android.view.Menu
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aciflow.R
import com.example.aciflow.model.DeadlinePriority
import com.example.aciflow.model.DeadlineTag
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
    val priority by editDeadlineViewModel.priority.collectAsState()
    val tag by editDeadlineViewModel.tag.collectAsState()

    var showPriorityDropdown by remember { mutableStateOf(false) }
    var showTagDropdown by remember { mutableStateOf(false) }

    val deadlinePriorityInstances = listOf(DeadlinePriority.Low, DeadlinePriority.Important, DeadlinePriority.Urgent)
    val deadlineTagInstances = listOf(DeadlineTag.Excercise, DeadlineTag.Project, DeadlineTag.Exam)

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
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)){
                Button(onClick = { showPriorityDropdown = true }) {
                    Text(text = priority?.priority ?: "Select Priority")
                }
            DropdownMenu(
                expanded = showPriorityDropdown,
                onDismissRequest = { showPriorityDropdown = false }
            ) {
                deadlinePriorityInstances.forEach { priorityInstance ->
                    DropdownMenuItem(text = { Text(text = priorityInstance.priority) }, onClick = {
                        editDeadlineViewModel.updatePriority(priorityInstance)
                        showPriorityDropdown = false
                    })
                }
            }
            }

            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)){
                Button(onClick = { showTagDropdown = true }) {
                    Text(text = tag?.tag ?: "Select Tag")
                }
                DropdownMenu(
                    expanded = showTagDropdown,
                    onDismissRequest = { showTagDropdown = false }
                ) {
                    deadlineTagInstances.forEach { tagInstance ->
                        DropdownMenuItem(text = { Text(text = tagInstance.tag) }, onClick = {
                            editDeadlineViewModel.updateTag(tagInstance)
                            showTagDropdown = false
                        })
                    }
                }
            }

        }
    }
}
