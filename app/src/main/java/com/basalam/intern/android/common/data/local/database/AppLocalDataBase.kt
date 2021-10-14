package com.basalam.intern.android.common.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.basalam.intern.android.common.data.local.database.dao.AnimalDao
import com.basalam.intern.android.common.data.local.database.dao.FlowerDao
import com.basalam.intern.android.common.data.local.database.entity.AnimalEntity
import com.basalam.intern.android.common.data.local.database.entity.FlowerEntity

@Database(entities = [AnimalEntity::class, FlowerEntity::class], version = 1,exportSchema = false)
abstract class AppLocalDataBase : RoomDatabase() {

    abstract fun animalDao(): AnimalDao

    abstract fun flowerDao(): FlowerDao
}