package com.example.desenrolaai.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize
import com.example.desenrolaai.model.enums.BorrowStatus
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

@Serializable
@VersionedParcelize
data class Borrow(
    @PrimaryKey(autoGenerate = true)
    var id: String = "",
    var product: Product = Product(),
    var requesterEmail: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var status: BorrowStatus = BorrowStatus.PENDING
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readParcelable(Product::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        TODO("status")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
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
        return "R$ %.2f".format(product.pricePerDay!! * (TimeUnit.DAYS.convert(
            numDays,
            TimeUnit.MILLISECONDS
        )))
    }
}