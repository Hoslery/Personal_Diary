package com.toropov.personaldiary.updateentry

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toropov.personaldiary.createentry.CreateEntryViewModel
import com.toropov.personaldiary.database.EntryDatabaseDao

class UpdateEntryViewModelFactory(
    private val entryKey: Int,
    private val dao: EntryDatabaseDao,
    private val application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateEntryViewModel::class.java)) {
            return UpdateEntryViewModel(entryKey,dao,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}