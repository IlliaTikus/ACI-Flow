package com.example.aciflow.views.forum

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Post(val user: String, val content: String)

class ForumViewModel : ViewModel() {
    private val _posts = MutableStateFlow(listOf(
        Post("Alice", "Hello, this is a forum post."),
        Post("Bob", "Welcome to the forum!")
    ))
    val posts: StateFlow<List<Post>> = _posts
}
