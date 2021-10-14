package com.basalam.intern.android.features.listOfAnimalFlower.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basalam.intern.android.common.data.remote.model.ApiAnimalFlowerModel
import com.basalam.intern.android.databinding.ItemRecAnimalFlowerBinding
import com.basalam.intern.android.features.listOfAnimalFlower.presentation.AnimalFlowerListFragment
import com.basalam.intern.android.common.util.toLog
import com.basalam.intern.android.features.listOfAnimalFlower.domain.model.AnimalFlowerItemModel
import kotlin.math.max

class AnimalFlowerAdapter(
    var apiAnimalList: List<ApiAnimalFlowerModel>,
    var flowerListApi: List<ApiAnimalFlowerModel>,
    val fragment: AnimalFlowerListFragment
) :
    RecyclerView.Adapter<AnimalFlowerAdapter.VH>() {

    private var filteredFlowerApis: MutableList<ApiAnimalFlowerModel> = flowerListApi as MutableList<ApiAnimalFlowerModel>
    private var filteredApiAnimals: MutableList<ApiAnimalFlowerModel> = apiAnimalList as MutableList<ApiAnimalFlowerModel>

    inner class VH(val binding: ItemRecAnimalFlowerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(apiAnimalModel: ApiAnimalFlowerModel, flowerModelApi: ApiAnimalFlowerModel?) {

            if (flowerModelApi != null) {

                val similarLetters = findSimilarLetters(apiAnimalModel.name, flowerModelApi.name)

                val itemModel = AnimalFlowerItemModel(
                    animalName = apiAnimalModel.name,
                    animalUrlImage = apiAnimalModel.imageUrl,
                    flowerName = flowerModelApi.name,
                    flowerUrlImage = flowerModelApi.imageUrl,
                    simillarLetters = similarLetters,
                    simillarLettersCount = similarLetters.size.toString()
                )

                binding.model = itemModel
                binding.owner = fragment
                binding.executePendingBindings()

            } else {
                "${apiAnimalModel.id}".toLog(" cannot find similar id for ")
            }

        }

        private fun findSimilarLetters(animal: String, flower: String): Array<String> {

            val animalCharSet: MutableSet<Char> = HashSet()
            val flowerCharSet: MutableSet<Char> = HashSet()

            for (c in animal.toCharArray()) {
                animalCharSet.add(c)
            }
            for (c in flower.toCharArray()) {
                flowerCharSet.add(c)
            }

            // Stores the intersection of set1 and set2 inside set1
            animalCharSet.retainAll(flowerCharSet)

            animalCharSet.forEach {
                it.toString().toLog("$animal/$flower")
            }

            return animalCharSet.map { it.toString() }.toTypedArray()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val binding = ItemRecAnimalFlowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return VH(binding)

    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.bind(filteredApiAnimals[position], filteredFlowerApis.find { it.id == filteredApiAnimals[position].id })

    }

    override fun getItemCount() = max(filteredApiAnimals.size, filteredFlowerApis.size)

    /**
     * @param animals is a list of name of animals like query searched in database
     * @param flowers is a list of name of flowers like query searched in database
     *
     * show new list according to animals and flowers in parameters
     * */
    fun updateList(
        animals: List<String>,
        flowers: List<String>
    ) {

        // find all corresponding animal/flower models to the animal/flower name
        filteredApiAnimals = apiAnimalList.filter { animals.contains(it.name) } as MutableList<ApiAnimalFlowerModel>
        filteredFlowerApis = flowerListApi.filter { flowers.contains(it.name) } as MutableList<ApiAnimalFlowerModel>

        // in order to that corresponding flower to the each animal be exist, we check that all animals in filteredAnimals have the corresponding flower in filteredFlowers
        // find corresponding flowers to the animals in filteredAnimals and add its to filteredFlowers if not exits
        filteredApiAnimals.forEach { filteredAnimalItem ->
            val similarFlower = flowerListApi.find { it.id == filteredAnimalItem.id }
            if (similarFlower != null && !filteredFlowerApis.contains(similarFlower)) {
                filteredFlowerApis.add(similarFlower)
            }
        }

        // in order to that corresponding animal to the each flower be exist, we check that all flowers in filteredFlowers have the corresponding animal in filteredAnimals
        // find corresponding animals to the flowers in filteredFlowers and add its to filteredAnimals if not exits
        filteredFlowerApis.forEach { filteredFlowerItem ->
            val similarAnimal = apiAnimalList.find { it.id == filteredFlowerItem.id }
            if (similarAnimal != null && !filteredApiAnimals.contains(similarAnimal)) {
                filteredApiAnimals.add(similarAnimal)
            }
        }

        notifyDataSetChanged()
    }
}