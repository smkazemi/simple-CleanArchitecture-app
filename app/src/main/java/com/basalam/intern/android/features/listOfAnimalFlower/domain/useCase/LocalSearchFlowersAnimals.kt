package com.basalam.intern.android.features.listOfAnimalFlower.domain.useCase

import com.basalam.intern.android.common.domain.repositories.AnimalFlowerRepository
import javax.inject.Inject

class LocalSearchFlowersAnimals @Inject constructor(private val repository: AnimalFlowerRepository) {

    suspend operator fun invoke(query: String) = repository.searchData(query)
}