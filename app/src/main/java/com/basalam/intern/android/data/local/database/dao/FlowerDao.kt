package com.basalam.intern.android.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.basalam.intern.android.data.local.database.entity.FlowerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FlowerDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(list: List<FlowerEntity>)

    @Update
    suspend fun updateAll(list: List<FlowerEntity>)

    @Query("SELECT * FROM flower")
    fun getAll(): Flow<List<FlowerEntity>>
}