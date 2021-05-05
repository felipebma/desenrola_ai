package com.example.desenrolaai.model

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.serialization.Serializable

@Serializable
@VersionedParcelize
data class Product(
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var categories: MutableList<String>? = ArrayList(),
    var pricePerDay: Double? = 0.0,
    var ownerEmail: String? = "",
    var ownerName: String? = "",
    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeStringList(categories)
        parcel.writeValue(pricePerDay)
        parcel.writeString(ownerEmail)
        parcel.writeString(ownerName)
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }

    fun getCondensedDescription(): String {
        return if (description.length <= 100) description else description.substring(0, 100)
            .trim() + "..."
    }
}