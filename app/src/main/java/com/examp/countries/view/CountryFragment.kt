package com.examp.countries.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.examp.countries.view.CountryFragmentArgs
import com.examp.countries.R
import com.examp.countries.databinding.FragmentCountryBinding
import com.examp.countries.util.downloadUrl
import com.examp.countries.util.placeholderProgressBar
import com.examp.countries.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*


class CountryFragment : Fragment() {

    private lateinit var viewModel : CountryViewModel
    private var countryUuid=0
    private lateinit var dataBinding : FragmentCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_country,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // feed fragmentten gönderilen countryUuid değişkenini bundle ile alma
        arguments?.let {
            countryUuid= CountryFragmentArgs.fromBundle(it).countryUuid

            viewModel=ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromData(countryUuid)

        }

        observeLiveData()    // method çağırma

    }

    // countryviewmodel sınıfımızdaki livedata nın işlmelerini aktarma için method
    private fun observeLiveData(){

        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {


                dataBinding.selectedCountry = country

                /*
                countryName.text=country.countryName
                countryCapital.text=country.countryCapital
                countryRegon.text=country.countryRegion
                countryCurrency.text=country.countryCurrency
                countryLangugage.text=country.countryLanguage
                context?.let {
                    countryImage.downloadUrl(country.flag, placeholderProgressBar(it))
                }
                */
            }
        })


    }


}