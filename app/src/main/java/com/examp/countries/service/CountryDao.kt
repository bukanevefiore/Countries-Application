package com.examp.countries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.examp.countries.model.Country

// data access object verileri(Dao) room databse de saklamak
// cororoutines kullanarak arka plandaki bir therad de çalıştırmak
// verileri room database de saklam işlemleri
// rooom databse için arayüzümüz
@Dao
interface CountryDao {

    // data access object
    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>

    //insert -> Insert into db ye veri ekleme
    // suspend -> courroutine içersinde çağrılır, durdurma(pause) devam ettirme()resume gibi işlemlere olanak sağlar
    // vararg ->  bir obje sayısını bilmediğimiz zaman farklı sayılarla verebilmemizi sağlayan keyword(multiple country object)(coklu ülke)
    // List<Long>  -> primarykey

  // recylerview de tüm ülkeleri sıralamak için üşkeleri getirir
    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    // ülkelerden herhnagi birine tıklandığında tıklanan ülkenin ayrıntılarını getirir
    @Query("SELECT * FROM country WHERE uuid= :countryId")
    suspend fun getCountry(countryId : Int) : Country


    @Query("DELETE FROM country")
    suspend fun deleteCountries()



}