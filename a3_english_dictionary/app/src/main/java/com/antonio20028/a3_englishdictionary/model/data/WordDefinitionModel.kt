package com.antonio20028.a3_englishdictionary.model.data
import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class WordDefinitionModel(
    @Contextual val antonyms: List<String?>? = null,
    val definition: String? = null,
    val example: String? = null,
    val synonyms: List<String?>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(antonyms)
        parcel.writeString(definition)
        parcel.writeString(example)
        parcel.writeStringList(synonyms)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WordDefinitionModel> {
        override fun createFromParcel(parcel: Parcel): WordDefinitionModel {
            return WordDefinitionModel(parcel)
        }

        override fun newArray(size: Int): Array<WordDefinitionModel?> {
            return arrayOfNulls(size)
        }
    }
}