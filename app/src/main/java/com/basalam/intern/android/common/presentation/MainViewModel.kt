package com.basalam.intern.android.common.presentation

import androidx.lifecycle.ViewModel
import com.basalam.intern.android.common.data.remote.model.ApiAnimalFlowerModel

class MainViewModel : ViewModel() {

    var apiAnimalList: List<ApiAnimalFlowerModel>?=null
    var flowerListApi: List<ApiAnimalFlowerModel>?=null
}