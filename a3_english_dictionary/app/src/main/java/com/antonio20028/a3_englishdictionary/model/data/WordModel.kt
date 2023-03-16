package com.antonio20028.a3_englishdictionary.model.data
import kotlinx.serialization.Serializable

@Serializable
data class WordModel(
    val meanings: List<WordMeaningModel>?,
    val phonetic: String? = null,
    val phonetics: List<WordPhoneticModel>?,
    val word: String? = null
)