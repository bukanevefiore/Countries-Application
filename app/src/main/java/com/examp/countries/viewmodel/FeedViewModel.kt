package com.examp.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examp.countries.model.Country

class FeedViewModel : ViewModel() {

    val countries=MutableLiveData<List<Country>>()  // değiştirilebilir canlı ülke veri listesi
    val countryError=MutableLiveData<Boolean>()  // hata olması duurmundadki livedata( canlı veri )
    val countryLoading=MutableLiveData<Boolean>()  // ülkelerin yüklenme durumunun evet ya da hayır şeklinde livedata da tutulması

    // swipe refresh widgetı için sayfa yenileme işlemi fonksiyonu
    fun refreshData(){

        val country=Country("Turkey","Asia","Ankara","TRY","Turkish","www.ss.com")
        val country2=Country("France","Paris","EUR","Franch","Turkish","www.ss.com")
        val country3=Country("Germany","Berlin","EUR","German","Turkish","www.ss.com")

        val countryList= arrayListOf<Country>(country,country2,country3)

        countries.value=countryList
        countryError.value=false
        countryLoading.value=false
    }
}