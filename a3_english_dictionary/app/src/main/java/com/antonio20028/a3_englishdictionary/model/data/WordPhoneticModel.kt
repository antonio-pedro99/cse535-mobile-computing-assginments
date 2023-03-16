package com.antonio20028.a3_englishdictionary.model.data
import kotlinx.serialization.Serializable

@Serializable
data class WordPhoneticModel(
    val audio: String? = null,
    val text: String? = null
)