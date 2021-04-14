package com.examp.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examp.countries.model.Country
import com.examp.countries.service.CountryAPIService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel : ViewModel() {

    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable() // (aynı anda birden fazla call yaparken)api den veri çekerken kullan at matığıyla hafızada fazla yer kaplamasına engel oluyor



    val countries=MutableLiveData<List<Country>>()  // değiştirilebilir canlı ülke veri listesi
    val countryError=MutableLiveData<Boolean>()  // hata olması duurmundadki livedata( canlı veri )
    val countryLoading=MutableLiveData<Boolean>()  // ülkelerin yüklenme durumunun evet ya da hayır şeklinde livedata da tutulması

    // swipe refresh widgetı için sayfa yenileme işlemi fonksiyonu
    fun refreshData(){

        getDataFromAPI()
    }

    // servisten veri çekme işlemi
    private fun getDataFromAPI(){
        countryLoading.value=true

        disposable.add(
                countryApiService.getData()
                        .subscribeOn(Schedulers.newThread())  // arkaplanda yapılacak
                        .observeOn(AndroidSchedulers.mainThread())  // arayüzde yapılacak
                        .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                            override fun onSuccess(t: List<Country>) {

                                countries.value=t
                                countryError.value=false
                                countryLoading.value=false

                            }

                            override fun onError(e: Throwable) {

                                countryLoading.value=false
                                countryError.value=true
                                e.printStackTrace()
                            }


                        })
        )

    }
}