package com.toropov.personaldiary.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface EntryDatabaseDao {

    @Insert
    fun insert(entry: Entry)

    @Update
    fun update(entry: Entry)

    @Query("SELECT * FROM entry_table WHERE id = :key")
    fun get(key: Int): Entry?

    @Query("SELECT * FROM entry_table WHERE (tags LIKE '%' || :tag || '%')" +
                " OR( NULLIF(tags, '') IS NULL AND ('%' = :tag) ) ORDER BY date DESC")
    fun getAllEntriesWithFilter(tag: String): LiveData<List<Entry>>

    @Query("SELECT * FROM entry_table ORDER BY date DESC")
    fun getAllEntries(): LiveData<List<Entry>>

    @Delete
    fun delete(entry: Entry)

}