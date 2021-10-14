package com.basalam.intern.android.common.data.remote.model

import com.google.gson.annotations.SerializedName

data class DataModel(

    @SerializedName("data") var data: List<ApiAnimalFlowerModel>

)