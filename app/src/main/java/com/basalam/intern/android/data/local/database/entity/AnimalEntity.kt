package com.basalam.intern.android.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.basalam.intern.android.util.Constant

@Entity(tableName = Constant.animal)
data class AnimalEntity(

    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "animalId") var animalId: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image") var imageUrl: String
)
