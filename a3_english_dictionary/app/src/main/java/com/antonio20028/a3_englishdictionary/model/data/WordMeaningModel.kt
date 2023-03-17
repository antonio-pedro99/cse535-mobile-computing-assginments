package com.antonio20028.a3_englishdictionary.model.data
import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class WordMeaningModel(
    val antonyms: List<String?>? = null,
    val definitions: List<WordDefinitionModel?>? = null,
    val partOfSpeech: String? = null,
    val synonyms: List<String?>? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createStringArrayList(),
        parcel.createTypedArrayList(WordDefinitionModel),
        parcel.readString(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(antonyms)
        parcel.writeTypedList(definitions)
        parcel.writeString(partOfSpeech)
        parcel.writeStringList(synonyms)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WordMeaningModel> {
        override fun createFromParcel(parcel: Parcel): WordMeaningModel {
            return WordMeaningModel(parcel)
        }

        override fun newArray(size: Int): Array<WordMeaningModel?> {
            return arrayOfNulls(size)
        }
    }
}