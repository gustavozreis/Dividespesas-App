package com.gustavozreis.dividespesas.data.usecases

import com.gustavozreis.dividespesas.data.models.Spend

interface CheckSpendListUseCase {

    suspend fun getSpendList(): List<Spend>

}