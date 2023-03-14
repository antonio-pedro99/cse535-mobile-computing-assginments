package com.antonio20028.a3_englishdictionary.model.data

data class WordDefinitionModel(
    val antonyms: List<Any>,
    val definition: String,
    val example: String,
    val synonyms: List<String>
)