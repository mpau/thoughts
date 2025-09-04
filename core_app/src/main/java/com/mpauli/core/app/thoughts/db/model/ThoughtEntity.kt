package com.mpauli.core.app.thoughts.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mpauli.core.models.Thought
import java.time.LocalDate

@Entity(
    tableName = "thoughts",
    indices = [Index("date")]
)
internal data class ThoughtEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "date")
    val date: LocalDate,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "text")
    val text: String
)

internal fun ThoughtEntity.toDomain(): Thought {
    return Thought(
        id = id,
        date = date,
        title = title,
        text = text
    )
}

internal fun Thought.toEntity(): ThoughtEntity {
    return ThoughtEntity(
        id = id,
        date = date,
        title = title,
        text = text
    )
}
