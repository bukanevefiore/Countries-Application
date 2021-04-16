package com.examp.countries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.examp.countries.model.Country

// room veri tabanımız
@Database(entities = arrayOf(Country::class),version = 1)
abstract class CountryDatabase : RoomDatabase(){


    // abstrack bir method oluşturup CountryDao interface ini döndürüyoruz
    abstract fun countryDao() : CountryDao

    // Singleton ile verilerin bir defa çekilmesi(veri tabanında bir defa obje oluşması) sağlanarak çakışma yaşanması önlenir
    companion object {  // diğer sınıflardan ulaşabilmek için

        @Volatile private var instance : CountryDatabase? = null  // @Volatile : farklı thread lerde görünür olması için :
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){

            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context : Context) = Room.databaseBuilder(
                context.applicationContext, CountryDatabase::class.java,"countrydatabase"
        ).build()


    }




}