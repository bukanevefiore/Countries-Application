package com.examp.countries.viewmodel

import android.app.Application
import android.os.ParcelUuid
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examp.countries.model.Country
import com.examp.countries.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData=MutableLiveData<Country>()


    // internetten çektiğimiz verileri room database e kaydetmek için method
    fun getDataFromData(uuid: Int){
      //  val country=Country("Turkey","Asia","Ankara","TRY","Turkish","www.ii.com")
      //  countryLiveData.value=country

        launch {

            val dao=CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value=country
        }
    }
}