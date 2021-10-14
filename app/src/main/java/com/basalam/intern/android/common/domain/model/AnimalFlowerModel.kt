package com.basalam.intern.android.common.domain.model

import com.basalam.intern.android.common.data.remote.model.ApiAnimalFlowerModel

data class AnimalFlowerModel(
    val animals: List<ApiAnimalFlowerModel>?,
    val flowers: List<ApiAnimalFlowerModel>?
)