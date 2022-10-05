package cz.cvut.popovma1.bottomsheetdemo2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageInfo(
    val id: Int,
    val url: String,
    val detail: String
): Parcelable
