package com.example.aciflow.views.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfileState(val name: String, val email: String)

class ProfileViewModel : ViewModel() {
    private val _profileState = MutableStateFlow(ProfileState("John Doe", "john.doe@example.com"))
    val profileState: StateFlow<ProfileState> = _profileState
}
