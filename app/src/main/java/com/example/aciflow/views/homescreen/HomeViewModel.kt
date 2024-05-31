package com.example.aciflow.views.homescreen

import androidx.compose.runtime.mutableStateOf
import com.example.aciflow.views.AciFlowViewModel

class HomeViewModel(
) : AciFlowViewModel() {

    var uiState = mutableStateOf(HomeUIState())
        private set

}

/*class HomeViewModelFactory(private val accountService: AccountService): ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(accountService = accountService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
