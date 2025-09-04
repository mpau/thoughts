package com.mpauli.core.app.thoughts.domain.usecase

import com.mpauli.core.app.thoughts.domain.repository.ThoughtsRepository
import com.mpauli.core.models.Thought
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetThoughtsOfDayUseCase internal constructor(private val thoughtsRepository: ThoughtsRepository) {

    fun run(date: LocalDate): Flow<List<Thought>> = thoughtsRepository.getThoughtsOfDay(date)
}
