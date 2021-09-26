package com.basalam.intern.android.ui.listOfAnimalFlower

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.basalam.intern.android.data.remote.model.AnimalFlowerModel
import com.basalam.intern.android.data.remote.response.ApiResponse
import com.basalam.intern.android.data.remote.response.NetworkStatus
import com.basalam.intern.android.data.repository.AnimalFlowerRepository
import com.basalam.intern.android.util.Constant
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AnimalFlowerViewModel : ViewModel() {

    @Inject
    lateinit var repository: AnimalFlowerRepository

    /**
     * get list of animals and flowers from server then return them in a Hashmap
     * @return hashMap of two list
     * */
    fun getData() = liveData<ApiResponse<HashMap<String, List<AnimalFlowerModel>>>>(Dispatchers.IO) {

        val animals: List<AnimalFlowerModel>
        val flowers: List<AnimalFlowerModel>

        // loading
        emit(ApiResponse.loading())

        // get animals list from server
        val animalResponse = repository.getAnimals()

        // check for server response and server data
        // if ok then get flower list from server
        // else return error response to viewModel
        if (animalResponse.status == NetworkStatus.SUCCESS && animalResponse.data != null) {
            animals = animalResponse.data.data

            // get flower list from server
            val flowerResponse = repository.getFlowers()

            // similar checks for server response and server data
            if (flowerResponse.status == NetworkStatus.SUCCESS && flowerResponse.data != null) {
                flowers = flowerResponse.data.data

                //
                val result = hashMapOf(
                    Constant.flower to flowers,
                    Constant.animal to animals
                )

                emit(ApiResponse.success(result))

            } else {

                emit(ApiResponse.error(flowerResponse.errorMessage.toString()))

            }

        } else {

            emit(ApiResponse.error(animalResponse.errorMessage.toString()))
        }
    }
}