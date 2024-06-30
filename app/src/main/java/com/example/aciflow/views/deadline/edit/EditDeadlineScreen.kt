package com.example.aciflow.views.deadline.edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aciflow.AppState
import com.example.aciflow.R
import com.example.aciflow.model.DeadlinePriority
import com.example.aciflow.model.DeadlineTag
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDeadlineScreen(navController: NavController, appState: AppState) {
    val factory = EditDeadlineViewModelFactory(accountService = AccountService.getAccountService(), storageService  = StorageService.getStorageService(), appState = appState)
    val viewModel: EditDeadlineViewModel = viewModel(factory = factory)

    val uiState by viewModel.uiState.collectAsState()
//    val title by editDeadlineViewModel.title.collectAsState()
//    val date by editDeadlineViewModel.date.collectAsState()
//    val description by editDeadlineViewModel.description.collectAsState()
//    val priority by editDeadlineViewModel.priority.collectAsState()
//    val tag by editDeadlineViewModel.tag.collectAsState()
//
    var showPriorityDropdown by remember { mutableStateOf(false) }
    var showTagDropdown by remember { mutableStateOf(false) }
//
    val deadlinePriorityInstances = listOf(DeadlinePriority.Low, DeadlinePriority.Important, DeadlinePriority.Urgent)
    val deadlineTagInstances = listOf(DeadlineTag.Excercise, DeadlineTag.Project, DeadlineTag.Exam)

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    uiState.dueDate?.let {
        calendar.time = it
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            viewModel.updateDueDate(context, calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val today = Calendar.getInstance()
    today.add(Calendar.DAY_OF_YEAR, 1)
    datePickerDialog.datePicker.minDate = today.timeInMillis

    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            viewModel.updateReminder(Timestamp(calendar.time))
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

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
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.saveDeadline(context)
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
                value = uiState.title,
                onValueChange = { newValue ->
                    viewModel.updateTitle(newValue)
                },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        datePickerDialog.show()
                    }
                    .border(1.dp, color = Color.Gray, MaterialTheme.shapes.small)
            ) {
                Text(
                    text = uiState.dueDate?.let { dateFormatter.format(it) } ?: "",
                    modifier = Modifier.padding(16.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        timePickerDialog.show() // Show the time picker when the box is clicked
                    }
                    .border(1.dp, color = Color.Gray, MaterialTheme.shapes.small)
            ) {
                Text(
                    text = uiState.reminder?.toDate()?.let { timeFormat.format(it) } ?: "",
                    modifier = Modifier.padding(16.dp)
                )
            }
            OutlinedTextField(
                value = uiState.description,
                onValueChange = { newValue ->
                    viewModel.updateDescription(newValue)
                },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp)
                    .padding(bottom = 8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Button(onClick = { showTagDropdown = true }) {
                    Text(text = uiState.tag?.tag ?: "Select Tag")
                }
                DropdownMenu(
                    expanded = showTagDropdown,
                    onDismissRequest = { showTagDropdown = false }
                ) {
                    deadlineTagInstances.forEach { tagInstance ->
                        DropdownMenuItem(text = { Text(text = tagInstance.tag) }, onClick = {
                            viewModel.updateTag(tagInstance)
                            showTagDropdown = false
                        })
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Button(onClick = { showPriorityDropdown = true }) {
                    Text(text = uiState.priority?.priority ?: "Select Priority")
                }
                DropdownMenu(
                    expanded = showPriorityDropdown,
                    onDismissRequest = { showPriorityDropdown = false }
                ) {
                    deadlinePriorityInstances.forEach { priorityInstance ->
                        DropdownMenuItem(text = { Text(text = priorityInstance.priority) }, onClick = {
                            viewModel.updatePriority(priorityInstance)
                            showPriorityDropdown = false
                        })
                    }
                }
            }

        }
    }
}
