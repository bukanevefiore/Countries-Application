package com.examp.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.examp.countries.view.CountryFragmentArgs
import com.examp.countries.R
import com.examp.countries.viewmodel.CountryViewModel


class CountryFragment : Fragment() {

    private lateinit var viewModel : CountryViewModel
    private var countryUuid=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromData()

        // feed fragmentten gönderilen countryUuid değişkenini bundle ile alma
        arguments?.let {
            countryUuid= CountryFragmentArgs.fromBundle(it).countryUuid
        }

        observeLiveData()    // method çağırma

    }

    // countryviewmodel sınıfımızdaki livedata nın işlmelerini aktarma için method
    private fun observeLiveData(){

        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                val countryName=view?.findViewById<TextView>(R.id.countryName)
                val countryCapital=view?.findViewById<TextView>(R.id.countryCapital)
                val countryRegion=view?.findViewById<TextView>(R.id.countryRegon)
                val countryCurrency=view?.findViewById<TextView>(R.id.countryCurrency)
                val countryLangugage=view?.findViewById<TextView>(R.id.countryLangugage)

                countryName?.text=country.countryName
                countryCapital?.text=country.countryCapital
                countryRegion?.text=country.countryRegion
                countryCurrency?.text=country.countryCurrency
                countryLangugage?.text=country.countryLanguage
            }
        })
    }


}