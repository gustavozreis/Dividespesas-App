package com.gustavozreis.dividespesas.data.usecases

import com.gustavozreis.dividespesas.data.repository.SpendRepository
import com.gustavozreis.dividespesas.data.models.Spend

class CheckSpendListUseCaseImpl(
    private val spendRepository: SpendRepository
) : CheckSpendListUseCase {

    override suspend fun getSpendList(): List<Spend> {
        return spendRepository.getSpendList()
    }
}