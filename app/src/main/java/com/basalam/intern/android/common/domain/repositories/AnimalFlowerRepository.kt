package com.basalam.intern.android.common.domain.repositories

import com.basalam.intern.android.common.data.remote.model.ApiAnimalFlowerModel
import com.basalam.intern.android.common.data.remote.response.ApiResponse
import com.basalam.intern.android.common.domain.model.AnimalFlowerModel
import com.basalam.intern.android.common.domain.model.LocalSearchByNameModel

interface AnimalFlowerRepository {

    suspend fun loadData(needUpdate: Boolean = false): ApiResponse<AnimalFlowerModel>

    suspend fun searchData(query: String): LocalSearchByNameModel
}