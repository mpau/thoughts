package com.mpauli.core.app.thoughts.domain.usecase

import com.mpauli.core.app.thoughts.domain.repository.ThoughtsRepository
import com.mpauli.core.models.Thought
import kotlinx.coroutines.flow.Flow

class GetAllThoughtsUseCase internal constructor(private val thoughtsRepository: ThoughtsRepository) {

    fun run(): Flow<List<Thought>> = thoughtsRepository.getAllThoughts()
}
