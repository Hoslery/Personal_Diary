package com.toropov.personaldiary.createentry

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.toropov.personaldiary.database.Entry
import com.toropov.personaldiary.database.EntryDatabaseDao
import kotlinx.coroutines.*

class CreateEntryViewModel(
        val dao: EntryDatabaseDao,
        application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToAllEntries = MutableLiveData<Entry?>()
    val navigateToAllEntries: LiveData<Entry?>
        get() = _navigateToAllEntries

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun addEntry(editText: String, editTags: String) {
        uiScope.launch {
            val newEntry: Entry = if(editTags == ""){
                Entry(entryText = editText, entryTags = null)
            } else {
                Entry(entryText = editText, entryTags = editTags)
            }
            insert(newEntry)
            _navigateToAllEntries.value = newEntry
        }
    }

    private suspend fun insert(newEntry: Entry) {
        withContext(Dispatchers.IO) {
            dao.insert(newEntry)
        }
    }

    fun doneNavigating() {
        _navigateToAllEntries.value = null
    }
}