package com.mpauli.core.app.thoughts.db

import androidx.room.Dao
import androidx.room.Query
import com.mpauli.base.db.BaseDao
import com.mpauli.core.app.thoughts.db.model.ThoughtEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
internal interface ThoughtsDao : BaseDao<ThoughtEntity> {

    @Query("SELECT * FROM thoughts ORDER BY id DESC")
    fun getAllThoughts(): Flow<List<ThoughtEntity>>

    @Query("SELECT * FROM thoughts WHERE date = :date ORDER BY id DESC")
    fun getThoughtsOfDay(date: LocalDate): Flow<List<ThoughtEntity>>

    @Query("SELECT * FROM thoughts WHERE id = :id LIMIT 1")
    fun getThought(id: Long): Flow<ThoughtEntity?>
}
