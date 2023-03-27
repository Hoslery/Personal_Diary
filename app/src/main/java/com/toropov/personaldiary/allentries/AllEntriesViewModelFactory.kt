package com.toropov.personaldiary.allentries

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toropov.personaldiary.database.EntryDatabaseDao

class AllEntriesViewModelFactory(
    private val dao: EntryDatabaseDao,
    private val application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllEntriesViewModel::class.java)) {
            return AllEntriesViewModel(dao,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}