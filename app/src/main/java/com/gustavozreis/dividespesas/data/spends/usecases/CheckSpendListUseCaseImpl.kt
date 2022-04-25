package com.gustavozreis.dividespesas.data.spends.usecases

import com.gustavozreis.dividespesas.data.spends.repository.SpendRepository
import com.gustavozreis.dividespesas.data.spends.models.Spend

class CheckSpendListUseCaseImpl(
    private val spendRepository: SpendRepository
) : CheckSpendListUseCase {

    override suspend fun getSpendList(): List<Spend> {
        return spendRepository.getSpendList()
    }
}