package com.shadhin.android_jetpack.view.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CustomerDataDao {
    @Insert
    suspend fun insertAll(vararg customer: CutomerModel): List<Long>

    @Query("SELECT * FROM cutomermodel ORDER BY uuid DESC Limit 10 ")
    suspend fun getAllUser(): List<CutomerModel>
}