package com.antonio20028.a3_englishdictionary.model.data
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class WordDefinitionModel(
    @Contextual val antonyms: List<String?>? = null,
    val definition: String? = null,
    val example: String? = null,
    val synonyms: List<String?>? = null
)