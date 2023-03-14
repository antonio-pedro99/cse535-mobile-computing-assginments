package com.antonio20028.a3_englishdictionary.model.data

data class WordMeaningModel(
    val antonyms: List<Any>,
    val definitions: List<WordDefinitionModel>,
    val partOfSpeech: String,
    val synonyms: List<String>
)