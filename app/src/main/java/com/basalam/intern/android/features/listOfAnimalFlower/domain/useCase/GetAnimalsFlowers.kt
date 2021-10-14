package com.basalam.intern.android.features.listOfAnimalFlower.domain.useCase

import com.basalam.intern.android.common.data.mapper.AnimalFlowerMapper
import com.basalam.intern.android.common.data.remote.response.ApiResponse
import com.basalam.intern.android.common.domain.model.AnimalFlowerModel
import com.basalam.intern.android.common.domain.repositories.AnimalFlowerRepository
import javax.inject.Inject

class GetAnimalsFlowers @Inject constructor(private val repository: AnimalFlowerRepository) {

    suspend operator fun invoke(): ApiResponse<AnimalFlowerModel> {

        val response = repository.loadData()

        if (response.data == null) {

            return response

        } else if (response.data.animals != null) {

            return if (response.data.flowers != null) {

                val res = AnimalFlowerMapper(flowers = response.data.flowers).map(animals = response.data.animals)

                ApiResponse.success(res)

            } else {

                ApiResponse.error(" flower response has null body ")
            }

        } else {

            return ApiResponse.error(" animals response has null body ")
        }


    }

}