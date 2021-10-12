package com.basalam.intern.android.ui.listOfAnimalFlower.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basalam.intern.android.data.remote.model.AnimalFlowerModel
import com.basalam.intern.android.databinding.ItemRecAnimalFlowerBinding
import com.basalam.intern.android.ui.listOfAnimalFlower.AnimalFlowerListFragment
import com.basalam.intern.android.util.toLog
import kotlin.math.max

class AnimalFlowerAdapter(
    var animalList: List<AnimalFlowerModel>,
    var flowerList: List<AnimalFlowerModel>,
    val fragment: AnimalFlowerListFragment
) :
    RecyclerView.Adapter<AnimalFlowerAdapter.VH>() {

    private var filteredFlowers: MutableList<AnimalFlowerModel> = flowerList as MutableList<AnimalFlowerModel>
    private var filteredAnimals: MutableList<AnimalFlowerModel> = animalList as MutableList<AnimalFlowerModel>

    inner class VH(val binding: ItemRecAnimalFlowerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(animalModel: AnimalFlowerModel, flowerModel: AnimalFlowerModel?) {

            if (flowerModel != null) {

                val similarLetters = findSimilarLetters(animalModel.name, flowerModel.name)

                val itemModel = AnimalFlowerItemModel(
                    animalName = animalModel.name,
                    animalUrlImage = animalModel.imageUrl,
                    flowerName = flowerModel.name,
                    flowerUrlImage = flowerModel.imageUrl,
                    simillarLetters = similarLetters,
                    simillarLettersCount = similarLetters.size.toString()
                )

                binding.model = itemModel
                binding.owner = fragment
                binding.executePendingBindings()

            } else {
                "${animalModel.id}".toLog(" cannot find similar id for ")
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

        holder.bind(filteredAnimals[position], filteredFlowers.find { it.id == filteredAnimals[position].id })

    }

    override fun getItemCount() = max(filteredAnimals.size, filteredFlowers.size)

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
        filteredAnimals = animalList.filter { animals.contains(it.name) } as MutableList<AnimalFlowerModel>
        filteredFlowers = flowerList.filter { flowers.contains(it.name) } as MutableList<AnimalFlowerModel>

        // in order to that corresponding flower to the each animal be exist, we check that all animals in filteredAnimals have the corresponding flower in filteredFlowers
        // find corresponding flowers to the animals in filteredAnimals and add its to filteredFlowers if not exits
        filteredAnimals.forEach { filteredAnimalItem ->
            val similarFlower = flowerList.find { it.id == filteredAnimalItem.id }
            if (similarFlower != null && !filteredFlowers.contains(similarFlower)) {
                filteredFlowers.add(similarFlower)
            }
        }

        // in order to that corresponding animal to the each flower be exist, we check that all flowers in filteredFlowers have the corresponding animal in filteredAnimals
        // find corresponding animals to the flowers in filteredFlowers and add its to filteredAnimals if not exits
        filteredFlowers.forEach { filteredFlowerItem ->
            val similarAnimal = animalList.find { it.id == filteredFlowerItem.id }
            if (similarAnimal != null && !filteredAnimals.contains(similarAnimal)) {
                filteredAnimals.add(similarAnimal)
            }
        }

        notifyDataSetChanged()
    }
}