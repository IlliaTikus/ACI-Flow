package com.example.aciflow.model

sealed class DeadlinePriority(val priority: String) {
    object Low : DeadlinePriority("low-priority") {
        override fun toString() = "low-priority"
    }
    object Important : DeadlinePriority("important") {
        override fun toString() = "important"
    }
    object Urgent : DeadlinePriority("urgent") {
        override fun toString() = "urgent"
    }
}