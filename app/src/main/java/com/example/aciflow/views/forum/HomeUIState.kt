package com.example.aciflow.views.forum

import com.example.aciflow.model.ForumPost

data class HomeUIState(
    val departmentName: String? = null,
    val posts: List<ForumPost> = emptyList()
)
