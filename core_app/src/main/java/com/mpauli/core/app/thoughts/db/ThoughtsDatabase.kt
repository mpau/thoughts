package com.mpauli.core.app.thoughts.db

import com.mpauli.core.app.thoughts.db.model.toDomain
import com.mpauli.core.app.thoughts.db.model.toEntity
import com.mpauli.core.models.Thought
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import kotlin.coroutines.CoroutineContext

internal class ThoughtsDatabase(
    private val db: ThoughtsRoomDatabase,
    private val defaultDispatcher: CoroutineContext
) {

    fun getAllThoughts(): Flow<List<Thought>> =
        db.getThoughtsDao()
            .getAllThoughts()
            .map { it.map { item -> item.toDomain() } }
            .flowOn(defaultDispatcher)

    fun getThoughtsOfDay(date: LocalDate): Flow<List<Thought>> =
        db.getThoughtsDao()
            .getThoughtsOfDay(date)
            .filterNotNull()
            .map { it.map { item -> item.toDomain() } }
            .flowOn(defaultDispatcher)

    fun getThought(id: Long): Flow<Thought> =
        db.getThoughtsDao()
            .getThought(id)
            .filterNotNull()
            .map { it.toDomain() }
            .flowOn(defaultDispatcher)

    suspend fun upsertThought(thought: Thought) {
        db.getThoughtsDao().upsert(thought.toEntity())
    }

    suspend fun deleteThought(thought: Thought) {
        db.getThoughtsDao().delete(thought.toEntity())
    }
}
