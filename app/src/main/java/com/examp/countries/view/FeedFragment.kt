package com.examp.countries.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.examp.countries.R
import com.examp.countries.adapter.CountryAdapter
import com.examp.countries.viewmodel.FeedViewModel

class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private val countryAdapter =CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // hangi class larla çalışmak istediğimizi belirtiyoruz
        viewModel=ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        val list= view.findViewById<RecyclerView>(R.id.countryList)
        list.layoutManager=LinearLayoutManager(context)
        list.adapter=countryAdapter


        observeLiveData()

        /*
      view.findViewById<Button>(R.id.fragment_buton).setOnClickListener(View.OnClickListener {

          val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment()
          Navigation.findNavController(it).navigate(action)
      })

         */
    }

    // feedviewmodel sınıfındaki livedata ları gözlemleme
    fun observeLiveData(){
        val bar=view?.findViewById<ProgressBar>(R.id.countryLoading)

        // contries listeleme
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            // countries in boş dolu kontrolü
            countries?.let {
                view?.findViewById<RecyclerView>(R.id.countryList)?.visibility=View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        // hata mesajı verme
        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            // hata mesajı var (true) ise
            error?.let {
                if(it){
                    view?.findViewById<TextView>(R.id.countryError)?.visibility=View.VISIBLE

                }else{
                    view?.findViewById<TextView>(R.id.countryError)?.visibility=View.GONE
                }
            }
        })

        // progress bar
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if(it){
                    bar?.visibility=View.VISIBLE
                    view?.findViewById<RecyclerView>(R.id.countryList)?.visibility=View.GONE
                    view?.findViewById<TextView>(R.id.countryError)?.visibility=View.GONE
                }else{
                    bar?.visibility=View.GONE
                }
            }
        })

    }

}