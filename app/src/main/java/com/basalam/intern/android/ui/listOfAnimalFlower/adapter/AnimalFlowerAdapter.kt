package com.basalam.intern.android.ui.listOfAnimalFlower.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basalam.intern.android.data.remote.model.AnimalFlowerModel
import com.basalam.intern.android.databinding.ItemRecAnimalFlowerBinding
import com.basalam.intern.android.ui.listOfAnimalFlower.AnimalFlowerListFragment
import com.basalam.intern.android.util.toLog

class AnimalFlowerAdapter(
    val animalList: List<AnimalFlowerModel>,
    val flowerList: List<AnimalFlowerModel>,
    val fragment: AnimalFlowerListFragment
) :
    RecyclerView.Adapter<AnimalFlowerAdapter.VH>() {

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

        holder.bind(animalList[position], flowerList.find { it.id == animalList[position].id })

    }

    override fun getItemCount() = animalList.size
}