package com.examp.countries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// verilerin sqlite içindeki (room database) i kullnmak için entity yazıyoruz
//  her değişkenin üzerine kolon isimlerini belirtiyoruz
//
@Entity
data class Country(
        @ColumnInfo(name= "name")
        @SerializedName("name")
        val countryName: String?,

        @ColumnInfo(name= "region")
        @SerializedName("region")
        val countryRegion: String?,

        @ColumnInfo(name = "capital")
        @SerializedName("capital")
        val countryCapital: String?,

        @ColumnInfo(name = "currency")
        @SerializedName("currency")
        val countryCurrency: String?,

        @ColumnInfo(name = "language")
        @SerializedName("language")
        val countryLanguage: String?,

        @ColumnInfo(name = "flag")
        @SerializedName("flag")
        val flag: String?
)

// json data mızda id olmadığı için burda yukraıdaki constructor dışında primarykey olarak oluşturuyoruz

{
        @PrimaryKey(autoGenerate = true)
        var uuid: Int = 0
}

