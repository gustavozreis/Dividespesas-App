package com.gustavozreis.dividespesas.base.viewmodels

import androidx.lifecycle.*
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.spends.models.Spend
import com.gustavozreis.dividespesas.data.spends.repository.SpendRepository
import com.gustavozreis.dividespesas.data.users.UserInstanceMock
import com.gustavozreis.dividespesas.data.users.firebase.FirebaseUserHelper
import com.gustavozreis.dividespesas.data.users.repository.UserRepository
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.*

class BaseViewModel(
    private val userRepository: UserRepository
) : ViewModel(){



}

@Suppress("UNCHECKED_CAST")
class BaseViewModelFactory (private val helper: FirebaseUserHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BaseViewModel::class.java)) {
            BaseViewModel(UserRepository(helper)) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}