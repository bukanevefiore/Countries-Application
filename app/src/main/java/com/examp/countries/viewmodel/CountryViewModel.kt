package com.examp.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examp.countries.model.Country

class CountryViewModel : ViewModel() {

    val countryLiveData=MutableLiveData<Country>()


    // internetten çektiğimiz verileri room database e kaydetmek için method
    fun getDataFromData(){
        val country=Country("Turkey","Asia","Ankara","TRY","Turkish","www.ii.com")
        countryLiveData.value=country
    }
}