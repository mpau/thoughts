package com.mpauli.core.app.apod.domain.usecase

import com.mpauli.core.app.apod.domain.repository.ApodRepository
import com.mpauli.core.models.Apod
import java.time.LocalDate

class GetApodUseCase internal constructor(private val apodRepository: ApodRepository) {

    suspend fun run(date: LocalDate = LocalDate.now()): Result<Apod> = apodRepository.get(date)
}
