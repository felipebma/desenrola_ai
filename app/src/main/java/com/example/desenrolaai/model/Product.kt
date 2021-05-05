package com.example.desenrolaai.model

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
data class Product(
    var id: Int,
    var name: String,
    var description: String,
    val categories: List<String>? = ArrayList(),
    val images: List<String>? = ArrayList(),
    val pricePerDay: Double? = 0.0,
    val ownerEmail: String? = "",
    val ownerName: String? = "",
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList(),
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
        parcel.writeStringList(images)
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
}