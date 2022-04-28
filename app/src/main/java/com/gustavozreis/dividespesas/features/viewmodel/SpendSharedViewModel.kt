package com.gustavozreis.dividespesas.features.viewmodel

import androidx.lifecycle.*
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.spends.models.Spend
import com.gustavozreis.dividespesas.data.spends.repository.SpendRepository
import com.gustavozreis.dividespesas.data.users.UserInstanceMock
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.*

class SpendSharedViewModel(
    private val spendRepository: SpendRepository,
) : ViewModel() {

    var _spendListLiveData = MutableLiveData<List<Spend>>()

    init {
        viewModelScope.launch { getSpendList() }
    }

    val spendListLiveData: LiveData<List<Spend>> get() = _spendListLiveData

    suspend fun getSpendList() {
        val spendList: MutableList<Spend> = mutableListOf()
        val emptyList: MutableList<Spend> = mutableListOf()
            val tempList: List<Spend> = spendRepository.getSpendList()
            for (spend in tempList) {
                spendList.add(spend)
            }
        _spendListLiveData.value = spendList
    }

    suspend fun addNewSpend(spendType: String,
        spendValue: Double,
        spendDate: String,
        spendDescription: String) {

        val spendID = UUID.randomUUID().toString()

        val spendObject = Spend(
            spendDate,
            spendDescription,
            spendID,
            spendType,
            UserInstanceMock.userList[0].userFirstName,
            spendValue
        )

        viewModelScope.launch {
            spendRepository.addSpend(spendObject,
                UserInstanceMock.userList[0].userMainDatabaseCollectionId,
                UserInstanceMock.userList[0].userMainDatabaseDocumentId)
            getSpendList() }

    }

    suspend fun deleteSpend(spendId: String) {
        viewModelScope.launch {
            spendRepository.deleteSpend("path", spendId)
            getSpendList()
        }
    }

}

@Suppress("UNCHECKED_CAST")
class SpendSharedModelFactory (private val helper: FirebaseSpendHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SpendSharedViewModel::class.java)) {
            SpendSharedViewModel(SpendRepository(helper)) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}