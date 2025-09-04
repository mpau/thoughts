package com.mpauli.core.app.thoughts.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mpauli.base.datetime.LocalDateConverter
import com.mpauli.core.app.thoughts.db.model.ThoughtEntity

@Database(
    entities = [ThoughtEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
internal abstract class ThoughtsRoomDatabase : RoomDatabase() {

    abstract fun getThoughtsDao(): ThoughtsDao
}

private const val DATABASE_VERSION = 1
internal const val THOUGHTS_DATABASE_NAME = "thoughts"
