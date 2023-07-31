package com.sambaxfinance.sambax.models

import android.os.Parcel
import android.os.Parcelable

data class GeneralGroupLandingResponseModel(
    val id :Int,
    val group_name: String?

): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt(),
                                       parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(group_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GeneralGroupLandingResponseModel> {
        override fun createFromParcel(parcel: Parcel): GeneralGroupLandingResponseModel {
            return GeneralGroupLandingResponseModel(parcel)
        }

        override fun newArray(size: Int): Array<GeneralGroupLandingResponseModel?> {
            return arrayOfNulls(size)
        }
    }
}
