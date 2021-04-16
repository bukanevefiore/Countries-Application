package com.examp.countries.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.examp.countries.R
import com.examp.countries.databinding.ItemCountryBinding
import com.examp.countries.model.Country
import com.examp.countries.util.downloadUrl
import com.examp.countries.util.placeholderProgressBar
import com.examp.countries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*
import org.w3c.dom.Text

class CountryAdapter(val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryClickListener {


    class CountryViewHolder(var view: ItemCountryBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        //val view=inflater.inflate(R.layout.item_country,parent,false)
        val view = DataBindingUtil.inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        // databinding ile verileri getiriyoruz
        holder.view.country = countryList[position]
        holder.view.listener = this

        /*
        holder.view.name.text = countryList[position].countryName
        holder.view.region.text = countryList[position].countryRegion
        holder.view.imageView.downloadUrl(countryList[position].flag, placeholderProgressBar(holder.view.context))

        Log.d("sa",countryList[position].countryCurrency.toString())


        holder.view.setOnClickListener {
            val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

         */
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    // feetfragment deki swipe refresh sayfa yenileme özelliğini bildirmek için method
    // listemizi güncelleme işlemi
    fun updateCountryList(newCountryList: List<Country>) {

        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View) {
        val uuid=v.countryUuidText.text.toString().toInt()
        val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}