package com.example.aciflow.model.services

import android.util.Log
import com.example.aciflow.model.Deadline
import com.example.aciflow.model.DeadlinePriority
import com.example.aciflow.model.DeadlineTag
import com.example.aciflow.model.ForumPost
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.Date

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
            val forumRef = depRef.collection(FORUM_COLLECTION).orderBy("createdAt", Query.Direction.DESCENDING)
            val listener = forumRef.addSnapshotListener { docs, err ->
                if (err != null) {
                    Log.w("DEBUG", "Listen failed: ", err)
                    return@addSnapshotListener
                }

                val posts = ArrayList<ForumPost>()
                for (doc in docs!!) {
                    val post = doc.toObject<ForumPost>()
                    Log.d("DEBUG", "Read post: $post")
                    posts.add(post)
                }

                trySend(posts)
            }
            awaitClose { listener.remove() }
        }

    fun getDepartmentMembers(
        depRef: DocumentReference
    ): Flow<List<String>> =
        callbackFlow {
            val usersRef = db.collection(USERS_COLLECTION)
            val listener = usersRef.whereEqualTo("department", depRef)
                .addSnapshotListener { docs, err ->
                if ( err != null ){
                    Log.w("DEBUG", "Listen failed: ", err)
                    return@addSnapshotListener
                }

                val userNames = ArrayList<String>()
                for (doc in docs!!){
                    val user = doc.getString("username")
                    Log.d("DEBUG", "Found user: $user")
                    if (user != null) {
                        userNames.add(user)
                    }
                }

                trySend(userNames)
            }
            awaitClose { listener.remove() }
        }

    /*
    userID must be a valid user UID, e.g. -> currentUserID from accountService
     */
    fun getDeadlinesForUser(
        userID: String
    ): Flow<List<Deadline>> = callbackFlow {
        val deadlineRef = db.collection(USERS_COLLECTION)
            .document(userID).collection(DEADLINE_COLLECTION).orderBy("dueDate", Query.Direction.ASCENDING)
        val listener = deadlineRef.addSnapshotListener { docs, err ->
            if ( err != null ){
                Log.w("DEBUG", "Listen failed: ", err)
                return@addSnapshotListener
            }

            val deadlines = ArrayList<Deadline>()
            for (doc in docs!!){
                var deadline = doc.toObject<Deadline>()
                deadline = deadline.copy(id = doc.id)
                Log.d("DEBUG", "Read deadline: $deadline")
                deadlines.add(deadline)
            }

            trySend(deadlines)
        }
        awaitClose { listener.remove() }
    }

    suspend fun getDeadlineById(userID: String, deadlineId: String): Deadline? {
        val deadlineRef = db.collection(USERS_COLLECTION)
            .document(userID)
            .collection(DEADLINE_COLLECTION)
            .document(deadlineId)

        return try {
            val snapshot = deadlineRef.get().await()
            snapshot.toObject<Deadline>()?.copy(id = snapshot.id)
        } catch (e: Exception) {
            Log.e("DEBUG", "Error fetching deadline with ID $deadlineId for user $userID", e)
            null
        }
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
            .addOnSuccessListener { Log.d("DEBUG", "Post written successfully!") }
            .addOnFailureListener { e -> Log.w("DEBUG", "Error writing post document", e) }
            .await()
        Log.d("DEBUG", "Written post: $post")
    }

    /*
    userID must be a valid user UID, e.g. -> currentUserID from accountService
     */
    suspend fun addDeadline(
        userID: String,
        title: String,
        description: String,
        dueDate: Date,
        tag: DeadlineTag?,
        priority: DeadlinePriority?
    ) {
        val deadlineRef = db.collection(USERS_COLLECTION)
            .document(userID).collection(DEADLINE_COLLECTION)
        val deadline = hashMapOf(
            "title" to title,
            "description" to description,
            "dueDate" to Timestamp(dueDate),
            "tag" to tag?.tag,
            "priority" to priority?.priority
        )
        deadlineRef.add(deadline)
            .addOnSuccessListener { Log.d("DEBUG", "Deadline write success: $deadline") }
            .addOnFailureListener { e -> Log.w("DEBUG", "Error writing deadline document", e) }
            .await()
    }

    /*
    userID must be a valid user UID, e.g. -> currentUserID from accountService
    deadlineID must be the id field of the deadline you wish to edit
     */
    suspend fun updateDeadline(
        userID: String,
        deadlineID: String,
        title: String,
        description: String,
        dueDate: Date,
        tag: DeadlineTag?,
        priority: DeadlinePriority
    ){
        val deadlineRef = db.collection(USERS_COLLECTION)
            .document(userID).collection(DEADLINE_COLLECTION)
            .document(deadlineID)
        val deadline = hashMapOf(
            "title" to title,
            "description" to description,
            "dueDate" to Timestamp(dueDate),
            "tag" to tag?.tag,
            "priority" to priority.priority
        )
        deadlineRef.set(deadline)
            .addOnSuccessListener { Log.d("DEBUG", "Deadline edit success: $deadline") }
            .addOnFailureListener { e -> Log.w("DEBUG", "Error editing deadline document", e) }
            .await()
    }

}
