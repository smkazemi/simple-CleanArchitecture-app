package com.basalam.intern.android.features.listOfAnimalFlower.presentation

import androidx.lifecycle.ViewModel
import com.basalam.intern.android.common.data.remote.response.ApiResponse
import com.basalam.intern.android.common.domain.model.AnimalFlowerModel
import com.basalam.intern.android.common.domain.model.LocalSearchByNameModel
import com.basalam.intern.android.features.listOfAnimalFlower.domain.useCase.GetAnimalsFlowers
import com.basalam.intern.android.features.listOfAnimalFlower.domain.useCase.LocalSearchFlowersAnimals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class AnimalFlowerViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var getAnimalFlowerUseCase: GetAnimalsFlowers

    @Inject
    lateinit var searchAnimalFlowers: LocalSearchFlowersAnimals

    /**
     * get list of animals and flowers from server then return them in Hashmap format
     * @return hashMap of two list
     * */
    fun getData() = flow<ApiResponse<AnimalFlowerModel>>() {

        // loading
        emit(ApiResponse.loading())

        // get animals and flowers list from server
        val data = getAnimalFlowerUseCase()

        emit(data)

    }.flowOn(Dispatchers.IO)

    /**
     * search on name of flowers and animals saved in local database
     * */
    fun searchData(query: String) = flow<LocalSearchByNameModel> {

        emit(searchAnimalFlowers(query))

    }.flowOn(Dispatchers.IO)
}