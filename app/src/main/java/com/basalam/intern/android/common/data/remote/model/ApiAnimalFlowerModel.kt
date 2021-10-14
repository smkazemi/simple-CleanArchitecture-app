package com.basalam.intern.android.common.data.remote.model

import com.google.gson.annotations.SerializedName

data class ApiAnimalFlowerModel(

    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("image") var imageUrl: String
)