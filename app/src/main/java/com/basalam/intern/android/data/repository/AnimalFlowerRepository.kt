package com.basalam.intern.android.data.repository

import android.content.Context
import com.basalam.intern.android.data.remote.BasalamService
import com.basalam.intern.android.data.remote.model.AnimalFlowerModel
import com.basalam.intern.android.data.remote.model.DataModel
import com.basalam.intern.android.data.remote.response.ApiResponse
import com.basalam.intern.android.data.remote.response.NetworkResponse
import com.basalam.intern.android.data.remote.response.NetworkStatus.*
import com.basalam.intern.android.util.Constant
import com.basalam.intern.android.util.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AnimalFlowerRepository @Inject constructor(val api: BasalamService, @ApplicationContext val context: Context) {

    private val mappedData: HashMap<String, List<AnimalFlowerModel>> = hashMapOf(
        Constant.flower to listOf(),
        Constant.animal to listOf()
    )

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
    suspend fun getData(): ApiResponse<HashMap<String, List<AnimalFlowerModel>>> {

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
    ): ApiResponse<HashMap<String, List<AnimalFlowerModel>>> {

        if (animalsResponse.status == SUCCESS && flowerResponse.status == SUCCESS) {

            if (animalsResponse.data != null) {

                mapAnimals(animalsResponse.data)

                return if (flowerResponse.data != null) {

                    mapFlower(flowerResponse.data)

                    ApiResponse.success(mappedData)

                } else {

                    ApiResponse.error(" flower response has null body ")
                }

            } else {

                return ApiResponse.error(" animals response has null body ")
            }

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

    private fun mapAnimals(data: DataModel) {

        mappedData[Constant.animal] = data.data
    }

    private fun mapFlower(data: DataModel) {
        mappedData[Constant.flower] = data.data
    }

}