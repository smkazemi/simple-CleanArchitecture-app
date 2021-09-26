package com.basalam.intern.android.ui.main

import androidx.lifecycle.ViewModel
import com.basalam.intern.android.data.remote.model.AnimalFlowerModel
import com.basalam.intern.android.data.remote.model.DataModel

class MainViewModel : ViewModel() {

    var animalList: List<AnimalFlowerModel>?=null
    var flowerList: List<AnimalFlowerModel>?=null
}