package com.mpauli.core.app.thoughts.domain.usecase

import com.mpauli.core.app.thoughts.domain.repository.ThoughtsRepository
import com.mpauli.core.models.Thought

class UpsertThoughtUseCase internal constructor(private val thoughtsRepository: ThoughtsRepository) {

    suspend fun run(thought: Thought) {
        thoughtsRepository.upsertThought(thought)
    }
}
