package com.gustavozreis.dividespesas.features.viewmodel

import androidx.lifecycle.*
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.spends.models.Spend
import com.gustavozreis.dividespesas.data.spends.repository.SpendRepository
import com.gustavozreis.dividespesas.data.users.UserInstance
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.*

class SpendSharedViewModel(
    private val spendRepository: SpendRepository,
) : ViewModel() {

    var _spendListLiveData = MutableLiveData<List<Spend>>()

    init {
        viewModelScope.launch {
            getSpendList()
        }
    }

    val spendListLiveData: LiveData<List<Spend>> get() = _spendListLiveData

    var _usersAndSpendsData = MutableLiveData<List<String>>()
    val usersAndSpendsData: LiveData<List<String>> get() = _usersAndSpendsData

    private fun getSpendList() {

        viewModelScope.launch {
            val spendList: MutableList<Spend> = mutableListOf()
            val emptyList: MutableList<Spend> = mutableListOf()
            val tempList: List<Spend> = spendRepository.getSpendList()
            for (spend in tempList) {
                spendList.add(spend)
            }
            _spendListLiveData.value = spendList
        }

    }

    fun addNewSpend(
        spendType: String,
        spendValue: Double,
        spendDate: String,
        spendDescription: String,
        spendIndex: Int,
    ) {
        val spendID = UUID.randomUUID().toString()
        val spendObject = Spend(
            spendDate,
            spendDescription,
            spendID,
            spendType,
            UserInstance.currentUser!!.userFirstName,
            spendValue,
            spendIndex
        )
        viewModelScope.launch {
            spendRepository.addSpend(spendObject,
                UserInstance.currentUser!!.userMainDatabaseCollectionId,
                UserInstance.currentUser!!.userMainDatabaseDocumentId)
            getSpendList()
        }
    }

    fun deleteSpend(spendId: String) {
        viewModelScope.launch {
            spendRepository.deleteSpend("path", spendId)
            getSpendList()
        }
    }

    fun updateSpend(
        spendId: String,
        spend: Spend,
    ) {
        viewModelScope.launch {
            spendRepository.updateSpend(spendId, spend)
            getSpendList()
        }
    }

    fun splitSpend() {

        viewModelScope.launch {
            val spendList: List<Spend> = spendRepository.getSpendList()

            var user01: String
            var user02: String
            var user01Spends: MutableList<Double> = mutableListOf()
            var user02Spends: MutableList<Double> = mutableListOf()

            var userNamesList: MutableSet<String> = mutableSetOf()

            // Get users names
            for (spend in spendList) {
                userNamesList.add(spend.spendUser)
            }
            user01 = userNamesList.elementAt(0)
            user02 = userNamesList.elementAt(1)

            for (spend in spendList) {
                when(spend.spendUser) {
                    user01 -> user01Spends.add(spend.spendValue)
                    user02 -> user02Spends.add(spend.spendValue)
                }
            }

            val user01SpendTotal = user01Spends.sum().toString()
            val user02SpendTotal = user02Spends.sum().toString()

            val returnList: MutableList<String> = mutableListOf()
            returnList.add(user01)
            returnList.add(user01SpendTotal)
            returnList.add(user02)
            returnList.add(user02SpendTotal)

            _usersAndSpendsData.value = returnList

        }

    }

}

@Suppress("UNCHECKED_CAST")
class SpendSharedModelFactory(private val helper: FirebaseSpendHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SpendSharedViewModel::class.java)) {
            SpendSharedViewModel(SpendRepository(helper)) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}