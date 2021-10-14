package com.basalam.intern.android.common.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.basalam.intern.android.common.data.local.database.entity.AnimalEntity

@Dao
interface AnimalDao {

    @Insert
    suspend fun insertAll(list: List<AnimalEntity>)

    @Update
    suspend fun updateAll(list: List<AnimalEntity>)

    @Query("SELECT * FROM animal")
    suspend fun getAll(): List<AnimalEntity>

    @Query("SELECT name FROM animal WHERE name LIKE :query")
    suspend fun searchByName(query: String): List<String>
}