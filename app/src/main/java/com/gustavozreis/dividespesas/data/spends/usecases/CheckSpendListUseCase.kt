package com.gustavozreis.dividespesas.data.spends.usecases

import com.gustavozreis.dividespesas.data.spends.models.Spend

interface CheckSpendListUseCase {

    suspend fun getSpendList(): List<Spend>

}