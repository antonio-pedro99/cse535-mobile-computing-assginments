package com.antonio20028.a3_englishdictionary.model.data
import kotlinx.serialization.Serializable

@Serializable
data class WordMeaningModel(
    val antonyms: List<String?>? = null,
    val definitions: List<WordDefinitionModel?>? = null,
    val partOfSpeech: String? = null,
    val synonyms: List<String?>? = null
)