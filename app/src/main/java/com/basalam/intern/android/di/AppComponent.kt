package com.basalam.intern.android.di

import android.content.Context
import com.basalam.intern.android.ui.listOfAnimalFlower.AnimalFlowerViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ApiModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }


    fun injectVieModel(viewModel: AnimalFlowerViewModel)

}