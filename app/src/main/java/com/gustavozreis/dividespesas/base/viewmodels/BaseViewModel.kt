package com.gustavozreis.dividespesas.base.viewmodels

import androidx.lifecycle.*
import com.gustavozreis.dividespesas.data.users.firebase.FirebaseUserHelper
import com.gustavozreis.dividespesas.data.users.model.User
import com.gustavozreis.dividespesas.data.users.repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.*

class BaseViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    fun createUser(
        userFirstName: String,
        userLastName: String,
        userEmail: String,
        userPassword: String,
        ) {
        viewModelScope.launch {
            userRepository.createUser(
                userEmail,
                userPassword,
                userFirstName,
                userLastName,
            )
        }
    }

    fun getUserFromEmail(email: String): User? {
        var user: User? = null
        viewModelScope.launch {
            user = userRepository.getUserFromEmail(email)
        }
        return user
    }

}

@Suppress("UNCHECKED_CAST")
class BaseViewModelFactory(private val helper: FirebaseUserHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BaseViewModel::class.java)) {
            BaseViewModel(UserRepository(helper)) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}