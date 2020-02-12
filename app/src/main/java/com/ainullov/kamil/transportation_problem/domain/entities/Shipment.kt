package com.ainullov.kamil.transportation_problem.domain.entities

import android.os.Parcel
import android.os.Parcelable

data class Shipment(
    var quantity: Double,
    val costPerUnit: Double,
    val row: Int,
    val column: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(quantity)
        parcel.writeDouble(costPerUnit)
        parcel.writeInt(row)
        parcel.writeInt(column)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Shipment> {
        override fun createFromParcel(parcel: Parcel): Shipment {
            return Shipment(parcel)
        }

        override fun newArray(size: Int): Array<Shipment?> {
            return arrayOfNulls(size)
        }
    }
}