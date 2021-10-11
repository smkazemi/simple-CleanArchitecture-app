package com.basalam.intern.android.data.mapper

import com.basalam.intern.android.data.remote.model.AnimalFlowerModel
import com.basalam.intern.android.util.Constant

class AnimalFlowerMapper(private val flowers: List<AnimalFlowerModel>) :
    Mapper<List<AnimalFlowerModel>, HashMap<String, List<AnimalFlowerModel>>> {

    override fun map(animals: List<AnimalFlowerModel>): HashMap<String, List<AnimalFlowerModel>> {
        return hashMapOf(
            Constant.animal to animals,
            Constant.flower to flowers
        )
    }
}