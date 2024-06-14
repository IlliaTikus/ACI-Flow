package com.example.aciflow.model.services

import android.util.Log
import com.example.aciflow.model.ForumPost
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.tasks.await

class StorageService private constructor(private val db: FirebaseFirestore) {

    // Singleton
    companion object {
        @Volatile
        private var instance: StorageService? = null

        fun getStorageService() = instance ?: synchronized(this) {
            instance ?: StorageService(Firebase.firestore).also { instance = it }
        }
    }

    val USERS_COLLECTION = "users"
    val DEP_COLLECTION = "departments"
    val FORUM_COLLECTION = "forum-posts"
    val DEADLINE_COLLECTION = "deadlines"

    fun getForumForDepartment(
        depRef: DocumentReference
    ): Flow<List<ForumPost>> =
        callbackFlow {
            val scope = this.plus(this.coroutineContext)
            val forumRef = depRef.collection(FORUM_COLLECTION)
            val listener = forumRef.addSnapshotListener { docs, err ->
                if ( err != null ){
                    Log.w("DEBUG", "Listen failed: ", err)
                    return@addSnapshotListener
                }

                val posts = ArrayList<ForumPost>()
                for (doc in docs!!){
                    val post = doc.toObject<ForumPost>()
                    Log.d("DEBUG", "Read post: $post")
                    posts.add(post)
                }

                trySend(posts)
            }
            awaitClose { listener.remove() }
        }

    /*
    Author should be the ID of the user writing the post, so ->
        auth.currentUserID
     */
    suspend fun addPost(depRef: DocumentReference, author: String, message: String){
        val forumRef = depRef.collection(FORUM_COLLECTION)
        val authorRef = db.collection(USERS_COLLECTION).document(author)
        val authorName = authorRef.get().await().getString("username")
        val post = hashMapOf(
            "author" to authorRef,
            "authorName" to authorName.orEmpty(),
            "createdAt" to FieldValue.serverTimestamp(),
            "content" to message,
        )
        forumRef.add(post)
            .addOnSuccessListener { Log.d("DEBUG", "Username successfully updated!") }
            .addOnFailureListener { e -> Log.w("DEBUG", "Error updating document", e) }
            .await()
        Log.d("DEBUG", "Written post: $post")
    }

}
