package com.basalam.intern.android.common.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.basalam.intern.android.common.data.local.database.entity.FlowerEntity

@Dao
interface FlowerDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(list: List<FlowerEntity>)

    @Update
    suspend fun updateAll(list: List<FlowerEntity>)

    @Query("SELECT * FROM flower")
    suspend fun getAll(): List<FlowerEntity>

    @Query("SELECT name FROM flower WHERE name LIKE :query")
    suspend fun searchByName(query: String): List<String>
}