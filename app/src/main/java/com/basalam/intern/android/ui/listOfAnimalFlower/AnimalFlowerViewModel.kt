package com.basalam.intern.android.ui.listOfAnimalFlower

import androidx.lifecycle.ViewModel
import com.basalam.intern.android.data.remote.model.AnimalFlowerModel
import com.basalam.intern.android.data.remote.response.ApiResponse
import com.basalam.intern.android.data.repository.AnimalFlowerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class AnimalFlowerViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var repository: AnimalFlowerRepository

    /**
     * get list of animals and flowers from server then return them in Hashmap format
     * @return hashMap of two list
     * */
    fun getData() = flow<ApiResponse<HashMap<String, List<AnimalFlowerModel>>>>() {

        // loading
        emit(ApiResponse.loading())

        // get animals and flowers list from server
        val data = repository.getData()

        emit(data)

    }.flowOn(Dispatchers.IO)
}