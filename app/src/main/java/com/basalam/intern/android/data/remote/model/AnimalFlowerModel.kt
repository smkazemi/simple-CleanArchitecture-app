package com.basalam.intern.android.data.remote.model

import com.google.gson.annotations.SerializedName

data class AnimalFlowerModel(

    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("image") var imageUrl: String
)