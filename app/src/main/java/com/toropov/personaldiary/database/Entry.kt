package com.toropov.personaldiary.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.toropov.personaldiary.getCurrentDateTime
import com.toropov.personaldiary.toString

@Entity(tableName = "entry_table")
data class Entry(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var entryId: Int = 0,

    @ColumnInfo(name = "date")
    var entryDate: String = getCurrentDateTime().toString("yyyy/MM/dd HH:mm:ss"),

    @ColumnInfo(name = "text")
    var entryText: String = "123",

    @ColumnInfo(name = "tags")
    var entryTags: String? = ""
)