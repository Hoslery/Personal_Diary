package com.toropov.personaldiary.allentries

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.toropov.personaldiary.database.Entry
import com.toropov.personaldiary.database.EntryDatabaseDao
import kotlinx.coroutines.*

class AllEntriesViewModel(
    val dao: EntryDatabaseDao,
    application: Application
): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var entries: LiveData<List<Entry>>
    var filter = MutableLiveData<String>("%")
    val dbUpdate = dao.getAllEntries()

    init {
        entries = Transformations.switchMap(filter) { filter ->
            return@switchMap dao.getAllEntriesWithFilter(filter)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onDelete(entry: Entry) {
        uiScope.launch {
            delete(entry)
        }
    }

    private suspend fun delete(entry: Entry) {
        withContext(Dispatchers.IO) {
            dao.delete(entry)
        }
    }

    fun setFilter(tag: String) {
        filter.postValue(tag)
    }

}