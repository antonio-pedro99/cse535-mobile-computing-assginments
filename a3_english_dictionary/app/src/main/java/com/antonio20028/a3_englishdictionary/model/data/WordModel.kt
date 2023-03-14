package com.antonio20028.a3_englishdictionary.model.data

data class WordModel(
    val meanings: List<WordMeaningModel>?,
    val phonetic: String?,
    val phonetics: List<WordPhoneticModel>?,
    val word: String
)