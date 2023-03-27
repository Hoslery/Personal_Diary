package com.toropov.personaldiary

import com.toropov.personaldiary.database.Entry
import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun validateEntryText(text: String): String? {
    if (text.isEmpty())
        return null
    return text
}

fun validateEntryTags(tags: String): String? {
    if (tags.isEmpty())
        return ""
    val tagsList: List<String> = tags.split(',').map { tag -> tag.trim() }
    val regex = """#([^#]+)""".toRegex()
    for (tag in tagsList) {
        if (!regex.matches(tag)){
            return null
        }
    }
    return tagsList.joinToString(",")
}

fun getAllUniqTags(entries: List<Entry>): List<String> {
    val uniqTags = mutableSetOf<String>()
    for(entry in entries){
        val tagsList: List<String> = entry.entryTags?.split(',') ?: continue
        for(tag in tagsList){
            uniqTags.add(tag)
        }
    }
    if (uniqTags.size == 0){
        return listOf()
    } else {
        return uniqTags.toList()
    }
}