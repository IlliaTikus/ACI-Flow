package com.example.aciflow.model

sealed class DeadlineTag(val tag: String){
    data object Excercise : DeadlineTag("excercise")
    data object Project : DeadlineTag("project")
    data object Exam : DeadlineTag("exam")
}
