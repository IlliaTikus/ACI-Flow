package com.example.aciflow.model

sealed class DeadlinePriority(val priority: String) {
    data object Low : DeadlinePriority("low-priority")
    data object Important : DeadlinePriority("important")
    data object Urgent : DeadlinePriority("urgent")
}
