package com.basalam.intern.android.ui.listOfAnimalFlower.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimalFlowerItemModel(
    var animalName: String,
    var animalUrlImage: String,
    var flowerName: String,
    var flowerUrlImage: String,
    var simillarLettersCount : String,
    var simillarLetters: Array<String>

) : Parcelable
