package com.sambaxfinance.sambax.models

import android.os.Parcel
import android.os.Parcelable

data class GeneralGroupLandingResponseModel(
    val id :Int
): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
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
