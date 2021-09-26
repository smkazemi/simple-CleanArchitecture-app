package com.basalam.intern.android.data.remote

import com.basalam.intern.android.data.remote.model.DataModel
import com.basalam.intern.android.util.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BasalamService {

    @GET(Constant.endPoint_intern)
    suspend fun getData(@Query("kind") kind: String) : Response<DataModel>
}