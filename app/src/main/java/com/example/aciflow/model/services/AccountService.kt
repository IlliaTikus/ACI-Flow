package com.example.aciflow.model.services

import android.util.Log
import com.example.aciflow.model.Account
import com.example.aciflow.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.tasks.await

class AccountService private constructor(private val auth: FirebaseAuth) {

    // Singleton
    companion object{
        @Volatile
        private var instance: AccountService? = null

        fun getAccountService() = instance ?: synchronized(this) {
            instance ?: AccountService(Firebase.auth).also { instance = it }
        }
    }

    private val db = Firebase.firestore
    private val DEFAULT_DEPARTMENT_ID = "3Cx84murEOWtynAY539u"
    val USERS_COLLECTION = "users"
    val DEP_COLLECTION = "departments"

    val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    val currentUserEmail: String
        get() = auth.currentUser?.email.orEmpty()

    val hasUser: Boolean
        get() = auth.currentUser != null

    val currentAccount: Flow<Account>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser?.let { Account(it.uid, it.email) } ?: Account())
            }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    val currentUser: Flow<User>
        get() = callbackFlow {
            val userRef = db.collection(USERS_COLLECTION).document(currentUserId)
            val listener = userRef.addSnapshotListener { snap, err ->
                if ( err != null ){
                    Log.w("DEBUG", "Listen failed: ", err)
                    return@addSnapshotListener
                }
                if(snap != null && snap.exists()){
                    snap.toObject<User>()?.let { trySend(it) }
                }
            }
            awaitClose { listener.remove() }
        }

    suspend fun getDepartmentName(defRef: DocumentReference): String =
        defRef.get().await().getString("name").orEmpty()

    //suspend fun getUserNameForReference(userRef: DocumentReference): String =
    //    userRef.get().await().getString("username").orEmpty()

    suspend fun createUser(username: String, email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password).await()
        addUser(username, currentUserId)
    }

    suspend fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).await()
        Log.i("DEBUG", currentUser.first().toString())
    }

    suspend fun updateUserName(newUserName: String){
        val userRef = db.collection(USERS_COLLECTION).document(currentUserId)
        userRef.update("username", newUserName)
            .addOnSuccessListener { Log.d("DEBUG", "Username successfully updated!") }
            .addOnFailureListener { e -> Log.w("DEBUG", "Error updating document", e) }
            .await()
    }

    fun resetPassword(){
        auth.sendPasswordResetEmail(currentUserEmail)
    }

    fun signOut() {
        auth.signOut()
    }

    private suspend fun addUser(username: String, userID: String){
        //hardcoded default department
        val depRef = db.collection(DEP_COLLECTION).document(DEFAULT_DEPARTMENT_ID)
        //create user with default department and semester assignment
        val user = User(
            username = username,
            department = depRef,
            semester = 1
        )
        Log.d("DEBUG", "Adding user: $user")
        db.collection(USERS_COLLECTION).document(userID).set(user).await()
    }

}