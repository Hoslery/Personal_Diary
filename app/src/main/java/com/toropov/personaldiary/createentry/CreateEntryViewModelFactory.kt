package com.toropov.personaldiary.createentry

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toropov.personaldiary.database.EntryDatabaseDao

class CreateEntryViewModelFactory(
        private val dao: EntryDatabaseDao,
        private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateEntryViewModel::class.java)) {
            return CreateEntryViewModel(dao,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}