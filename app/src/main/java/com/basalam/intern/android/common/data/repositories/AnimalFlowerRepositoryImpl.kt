package com.basalam.intern.android.common.data.repositories

import android.content.Context
import com.basalam.intern.android.common.data.local.database.AppLocalDataBase
import com.basalam.intern.android.common.data.local.database.entity.AnimalEntity
import com.basalam.intern.android.common.data.local.database.entity.FlowerEntity
import com.basalam.intern.android.common.data.mapper.AnimalFlowerMapper
import com.basalam.intern.android.common.data.remote.BasalamService
import com.basalam.intern.android.common.data.remote.model.ApiAnimalFlowerModel
import com.basalam.intern.android.common.data.remote.model.DataModel
import com.basalam.intern.android.common.data.remote.response.ApiResponse
import com.basalam.intern.android.common.data.remote.response.NetworkResponse
import com.basalam.intern.android.common.data.remote.response.NetworkStatus.*
import com.basalam.intern.android.common.domain.model.AnimalFlowerModel
import com.basalam.intern.android.common.domain.model.LocalSearchByNameModel
import com.basalam.intern.android.common.domain.repositories.AnimalFlowerRepository
import com.basalam.intern.android.common.util.Constant
import com.basalam.intern.android.common.util.NetworkUtils
import com.basalam.intern.android.common.util.listMapper
import com.basalam.intern.android.common.util.toLog
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AnimalFlowerRepositoryImpl @Inject constructor(
    val api: BasalamService,
    @ApplicationContext val context: Context,
    val db: AppLocalDataBase
) : AnimalFlowerRepository {

    private suspend fun getAnimals(): NetworkResponse<DataModel?> {

        if (NetworkUtils.isNetworkAvailable(context)) {

            try {

                val response = api.getData(Constant.animal)

                return if (response.isSuccessful) {

                    NetworkResponse.success(response.body())

                } else {

                    NetworkResponse.error(" response error : " + response.message())
                }

            } catch (e: Exception) {

                return NetworkResponse.error(" exception : " + e.message.toString())
            }


        } else {

            return (NetworkResponse.networkError())
        }

    }

    private suspend fun getFlowers(): NetworkResponse<DataModel?> {

        if (NetworkUtils.isNetworkAvailable(context)) {

            try {

                val response = api.getData(Constant.flower)

                return if (response.isSuccessful) {

                    NetworkResponse.success(response.body())

                } else {

                    NetworkResponse.error(" response error : " + response.message())
                }

            } catch (e: Exception) {

                return NetworkResponse.error(" exception : " + e.message.toString())
            }


        } else {

            return (NetworkResponse.networkError())
        }

    }

    /**
     * call api and getting data of animals and flowers
     * @return successful ApiResponse with data of map of animals and flowers list
     * if error occurred return error ApiResponse with data of null*/
    private suspend fun getData(): ApiResponse<AnimalFlowerModel> {

        val animalsResponse = getAnimals()
        val flowerResponse = getFlowers()

        return makeData(animalsResponse, flowerResponse)

    }

    /**
     * check response of animal and flower api call
     * @return hashMap of their list, if response is not successful return error response
     * */
    private fun makeData(
        animalsResponse: NetworkResponse<DataModel?>,
        flowerResponse: NetworkResponse<DataModel?>
    ): ApiResponse<AnimalFlowerModel> {

        if (animalsResponse.status == SUCCESS && flowerResponse.status == SUCCESS) {

            val result =
                AnimalFlowerMapper(flowers = flowerResponse.data?.data).map(animals = animalsResponse.data?.data)

            return ApiResponse.success(result)

        } else {

            when (animalsResponse.status) {

                ERROR -> {
                    return ApiResponse.error(animalsResponse.errorMessage.toString())
                }

                NETWORK_ERROR -> {
                    return ApiResponse.networkError()
                }

            }

            when (flowerResponse.status) {

                ERROR -> {
                    return ApiResponse.error(flowerResponse.errorMessage.toString())

                }
                NETWORK_ERROR -> {
                    return ApiResponse.networkError()
                }
            }

            return ApiResponse.error(" unknown error")


        }

    }

    /**
     * load data from database if exist
     * if dataBase is empty or need to update it then first update database by getting new data from server second load saved data */
    override suspend fun loadData(needUpdate: Boolean): ApiResponse<AnimalFlowerModel> {

        if (needUpdate) {

            needUpdate.toString().toLog("need update")

            return refreshData(false)

        } else {

            val animals = loadAnimals()
            val flowers = loadFlowers()

            return if (animals.isEmpty() || flowers.isEmpty()) {

                refreshData(true)

            } else {

                ApiResponse.success(AnimalFlowerMapper(flowers = flowers).map(animals = animals))

            }

        }

    }

    /**
     *
     * */
    override suspend fun searchData(query: String): LocalSearchByNameModel {

        val animals = db.animalDao().searchByName("%$query%")

        val flowers = db.flowerDao().searchByName("%$query%")

        return LocalSearchByNameModel(
            animalsName = animals,
            flowersName = flowers
        )

    }

    /**
     * getting new data from server, update/save database then return saved/updated data*/
    private suspend fun refreshData(isEmptyDatabase: Boolean): ApiResponse<AnimalFlowerModel> {

        val apiResponse = getData()

        return if (apiResponse.status == SUCCESS) {

            if (isEmptyDatabase) {
                saveData(apiResponse.data!!)
            } else {
                updateData(apiResponse.data!!)
            }

            val animals = loadAnimals()
            val flowers = loadFlowers()

            ApiResponse.success(AnimalFlowerMapper(flowers = flowers).map(animals = animals))

        } else {

            apiResponse
        }

    }

    private suspend fun updateData(data: AnimalFlowerModel) {
        val animals = data.animals
        val flowers = data.flowers

        db.animalDao()
            .updateAll(animals!!.listMapper { AnimalEntity(animalId = it.id, name = it.name, imageUrl = it.imageUrl) })
        db.flowerDao()
            .updateAll(flowers!!.listMapper { FlowerEntity(flowerId = it.id, name = it.name, imageUrl = it.imageUrl) })
    }

    private suspend fun saveData(data: AnimalFlowerModel) {
        val animals = data.animals
        val flowers = data.flowers

        db.animalDao()
            .insertAll(animals!!.listMapper { AnimalEntity(animalId = it.id, name = it.name, imageUrl = it.imageUrl) })
        db.flowerDao()
            .insertAll(flowers!!.listMapper { FlowerEntity(flowerId = it.id, name = it.name, imageUrl = it.imageUrl) })
    }

    private suspend fun loadAnimals(): List<ApiAnimalFlowerModel> {

        val animals = db.animalDao().getAll()

        return (animals.listMapper { ApiAnimalFlowerModel(id = it.animalId, name = it.name, imageUrl = it.imageUrl) })

    }

    private suspend fun loadFlowers(): List<ApiAnimalFlowerModel> {

        val animals = db.flowerDao().getAll()

        return (animals.listMapper { ApiAnimalFlowerModel(id = it.flowerId, name = it.name, imageUrl = it.imageUrl) })

    }
}