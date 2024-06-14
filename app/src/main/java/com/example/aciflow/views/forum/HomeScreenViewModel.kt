package com.example.aciflow.views.forum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService
import com.example.aciflow.views.AciFlowViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first

class HomeScreenViewModel(
    private val storageService: StorageService,
    private val accountService: AccountService
) : AciFlowViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    init {
        launchCatching {
            val department = accountService.currentUser.first().department!!
            _uiState.value = _uiState.value.copy(
                departmentName = accountService.getDepartmentName(department)
            )
            storageService.getForumForDepartment(
                department
            ).distinctUntilChanged().collect { posts ->
                _uiState.value = _uiState.value.copy(posts = posts)
            }
        }
    }

//    fun testPost(){
//        launchCatching {
//            storageService.addPost(accountService.currentUser.first().department!!,
//                accountService.currentUserId, "TESTING POST \nthis is funny post")
//        }
//    }
}

class HomeScreenViewModelFactory(
    private val storageService: StorageService,
    private val accountService: AccountService
): ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeScreenViewModel::class.java)){
            return HomeScreenViewModel(storageService = storageService, accountService = accountService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
