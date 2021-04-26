package com.shadhin.android_jetpack.view.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CutomerModel(

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "dob")
    val dob: String?,

    @ColumnInfo(name = "number")
    val number: String?,

    @ColumnInfo(name = "postalCode")
    val postalCode: String?,

    @ColumnInfo(name = "postOffice")
    val postOffice: String?,


    @ColumnInfo(name = "thana")
    val thana: String?,

    @ColumnInfo(name = "district")
    val district: String?

) {

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
