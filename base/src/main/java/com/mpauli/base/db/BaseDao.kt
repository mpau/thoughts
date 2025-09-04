package com.mpauli.base.db

import androidx.room.Delete
import androidx.room.Upsert

interface BaseDao<T : Any> {

    @Upsert
    suspend fun upsert(data: T): Long

    @Delete
    suspend fun delete(data: T)
}
