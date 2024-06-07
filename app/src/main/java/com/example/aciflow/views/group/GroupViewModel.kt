package com.example.aciflow.views.group

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class GroupMember(val name: String)

class GroupViewModel : ViewModel() {
    private val _members = MutableStateFlow(listOf(
        GroupMember("Alice"),
        GroupMember("Bob")
    ))
    val members: StateFlow<List<GroupMember>> = _members
}
