package com.gustavozreis.dividespesas.features.checkspend

import androidx.lifecycle.*
import com.gustavozreis.dividespesas.data.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.models.Spend
import com.gustavozreis.dividespesas.data.repository.SpendRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

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

        val spendObject = Spend(
            spendDate,
            spendDescription,
            "091280391280938",
            spendType,
            "Gustavo",
            spendValue
        )

        spendRepository.addSpend(spendObject, "kllj8b8by5DFGMpAW6SK")
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