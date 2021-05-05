package com.example.desenrolaai.model

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import com.example.desenrolaai.model.enums.BorrowStatus
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

@VersionedParcelize
data class Borrow(
    val id: Int,
    val product: Product,
    val requesterEmail: String,
    val startDate: String,
    val endDate: String,
    var status: BorrowStatus
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(Product::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        TODO("status")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
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

    fun getPrice(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val numDays = sdf.parse(endDate).time - sdf.parse(startDate).time
        return (product.pricePerDay!! * (TimeUnit.DAYS.convert(
            numDays,
            TimeUnit.MILLISECONDS
        ))).toString()
    }
}