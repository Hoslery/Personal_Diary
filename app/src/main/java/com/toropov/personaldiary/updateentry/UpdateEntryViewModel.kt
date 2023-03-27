package com.toropov.personaldiary.updateentry

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.toropov.personaldiary.database.Entry
import com.toropov.personaldiary.database.EntryDatabaseDao
import com.toropov.personaldiary.getCurrentDateTime
import com.toropov.personaldiary.toString
import kotlinx.coroutines.*

class UpdateEntryViewModel(
    private val entryKey: Int = 0,
    val dao: EntryDatabaseDao,
    application: Application
): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToAllEntries = MutableLiveData<Boolean>()
    val navigateToAllEntries: LiveData<Boolean?>
        get() = _navigateToAllEntries

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun updateEntry(text: String, tags: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val entry = dao.get(entryKey) ?: return@withContext
                entry.entryDate = getCurrentDateTime().toString("yyyy/MM/dd HH:mm:ss")
                entry.entryText = text
                if (tags == ""){
                    entry.entryTags = null
                } else {
                    entry.entryTags = tags
                }
                dao.update(entry)
            }
            _navigateToAllEntries.value = true
        }
    }


    fun doneNavigating() {
        _navigateToAllEntries.value = false
    }
}