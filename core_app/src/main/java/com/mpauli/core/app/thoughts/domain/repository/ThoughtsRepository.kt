package com.mpauli.core.app.thoughts.domain.repository

import com.mpauli.core.app.thoughts.db.ThoughtsDatabase
import com.mpauli.core.models.Thought
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

internal class ThoughtsRepository(private val thoughtsDatabase: ThoughtsDatabase) {

    fun getAllThoughts(): Flow<List<Thought>> = thoughtsDatabase.getAllThoughts()

    fun getThoughtsOfDay(date: LocalDate): Flow<List<Thought>> = thoughtsDatabase.getThoughtsOfDay(date)

    fun getThought(id: Long): Flow<Thought> = thoughtsDatabase.getThought(id)

    suspend fun upsertThought(thought: Thought) {
        thoughtsDatabase.upsertThought(thought)
    }

    suspend fun deleteThought(thought: Thought) {
        thoughtsDatabase.deleteThought(thought)
    }
}
