package com.mpauli.core.app

import androidx.room.Room
import com.mpauli.core.app.apod.domain.repository.ApodRepository
import com.mpauli.core.app.apod.domain.usecase.GetApodUseCase
import com.mpauli.core.app.thoughts.db.THOUGHTS_DATABASE_NAME
import com.mpauli.core.app.thoughts.db.ThoughtsDatabase
import com.mpauli.core.app.thoughts.db.ThoughtsRoomDatabase
import com.mpauli.core.app.thoughts.domain.repository.ThoughtsRepository
import com.mpauli.core.app.thoughts.domain.usecase.DeleteThoughtUseCase
import com.mpauli.core.app.thoughts.domain.usecase.GetAllThoughtsUseCase
import com.mpauli.core.app.thoughts.domain.usecase.GetThoughtUseCase
import com.mpauli.core.app.thoughts.domain.usecase.GetThoughtsOfDayUseCase
import com.mpauli.core.app.thoughts.domain.usecase.UpsertThoughtUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder<ThoughtsRoomDatabase>(
            context = androidContext(),
            name = THOUGHTS_DATABASE_NAME
        ).build()
    }
    single {
        ThoughtsDatabase(
            db = get(),
            defaultDispatcher = Dispatchers.Default
        )
    }
    single { ThoughtsRepository(get()) }
    single { ApodRepository(get()) }

    factory { GetAllThoughtsUseCase(get()) }
    factory { GetThoughtsOfDayUseCase(get()) }
    factory { GetThoughtUseCase(get()) }
    factory { UpsertThoughtUseCase(get()) }
    factory { DeleteThoughtUseCase(get()) }

    factory { GetApodUseCase(get()) }
}
