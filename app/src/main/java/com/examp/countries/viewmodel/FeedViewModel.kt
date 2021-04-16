package com.examp.countries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examp.countries.model.Country
import com.examp.countries.service.CountryAPIService
import com.examp.countries.service.CountryDatabase
import com.examp.countries.util.CustomSharedPreferences
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable() // (aynı anda birden fazla call yaparken)api den veri çekerken kullan at matığıyla hafızada fazla yer kaplamasına engel oluyor
    private var customPreferences = CustomSharedPreferences(getApplication())
    // güncellem zamanını belirleme(nonosaniye cinsinden 10 dakikaya refreshtime değişkenine atıyoruz )
    private var refreshTime = 10*60*1000*1000*1000L


    val countries=MutableLiveData<List<Country>>()  // değiştirilebilir canlı ülke veri listesi
    val countryError=MutableLiveData<Boolean>()  // hata olması duurmundadki livedata( canlı veri )
    val countryLoading=MutableLiveData<Boolean>()  // ülkelerin yüklenme durumunun evet ya da hayır şeklinde livedata da tutulması


    // swipe refresh widgetı için sayfa yenileme işlemi fonksiyonu
    fun refreshData(){

        // preferences ile oluşturulan güncelleem zamanını alma
        val updateTime = customPreferences.getTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){

            getDataFromSQLite()

        }
        else{
            getDataFromAPI()
        }

    }


    fun refreshFromAPI(){
        getDataFromAPI()
    }



    // sqlite dan veri çekme işlemi(verilerin çekilme süresi 10 dakikadan az ise veriler sqlite dan çekilecek)
    // sqlite dan veri çekerken dao kullanır ve bunu launch içinde yaparız
    private fun getDataFromSQLite(){
        countryLoading.value=true

        launch {

            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries from sqlite",Toast.LENGTH_LONG).show()
        }
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

                                storeInSQLite(t)
                                Toast.makeText(getApplication(),"Countries from api",Toast.LENGTH_LONG).show()

                            }

                            override fun onError(e: Throwable) {

                                countryLoading.value=false
                                countryError.value=true
                                e.printStackTrace()
                            }


                        })
        )

    }

    // progressbar vs gösterilmesi vs
    private fun showCountries(countryList: List<Country>){

        countries.value=countryList
        countryError.value=false
        countryLoading.value=false
    }





    // aldığımız verileri sqlite a kaydetme
    private fun storeInSQLite(list: List<Country>){

        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteCountries()
            val listLong = dao.insertAll(*list.toTypedArray())  // list -> individual (tek tek)
            var i =0
            while (i < list.size){
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }

            showCountries(list)

        }

        customPreferences.saveTime(System.nanoTime())

    }


    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }

}