package com.example.desenrolaai.model

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import com.example.desenrolaai.model.enums.BorrowStatus
import kotlinx.serialization.Serializable
import java.util.*

@VersionedParcelize
data class Borrow (
    val product: Product,
    val requesterEmail: String,
    val startDate: String,
    val endDate: String,
    var status: BorrowStatus
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Product::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        TODO("status")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(product, flags)
        parcel.writeString(requesterEmail)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Borrow> {
        override fun createFromParcel(parcel: Parcel): Borrow {
            return Borrow(parcel)
        }

        override fun newArray(size: Int): Array<Borrow?> {
            return arrayOfNulls(size)
        }
    }
}