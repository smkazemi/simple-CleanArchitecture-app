package com.basalam.intern.android.data.repository

import android.content.Context
import com.basalam.intern.android.data.remote.BasalamService
import com.basalam.intern.android.data.remote.model.DataModel
import com.basalam.intern.android.data.remote.response.NetworkResponse
import com.basalam.intern.android.util.Constant
import com.basalam.intern.android.util.NetworkUtils
import javax.inject.Inject

class AnimalFlowerRepository @Inject constructor(val api: BasalamService, val context: Context) {

    suspend fun getAnimals(): NetworkResponse<DataModel?> {

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

    suspend fun getFlowers(): NetworkResponse<DataModel?> {

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

}