package com.basalam.intern.android.common.data.mapper

import com.basalam.intern.android.common.data.remote.model.ApiAnimalFlowerModel
import com.basalam.intern.android.common.domain.model.AnimalFlowerModel

class AnimalFlowerMapper(private val flowers: List<ApiAnimalFlowerModel>?) :
    Mapper<List<ApiAnimalFlowerModel>?, AnimalFlowerModel> {

    override fun map(animals: List<ApiAnimalFlowerModel>?): AnimalFlowerModel {
        return AnimalFlowerModel(
            animals = animals,
            flowers = flowers
        )
    }
}