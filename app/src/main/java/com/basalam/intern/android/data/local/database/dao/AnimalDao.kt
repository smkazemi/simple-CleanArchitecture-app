package com.basalam.intern.android.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.basalam.intern.android.data.local.database.entity.AnimalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDao {

    @Insert
    suspend fun insertAll(list: List<AnimalEntity>)

    @Update
    suspend fun updateAll(list: List<AnimalEntity>)

    @Query("SELECT * FROM animal")
    fun getAll(): Flow<List<AnimalEntity>>
}