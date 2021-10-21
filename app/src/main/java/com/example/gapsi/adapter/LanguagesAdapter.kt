package com.example.gapsi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gapsi.R
import com.example.gapsi.model.response.Languages
import com.example.gapsi.model.response.ResponseMoviesPopular
import com.example.gapsi.model.response.Results
import com.squareup.picasso.Picasso

class LanguagesAdapter: RecyclerView.Adapter<LanguagesAdapter.ViewHolder>() {

    var result: ArrayList<Results>  = ArrayList()
    lateinit var context: Context

    fun catalogAdapter(productosCatalog : ArrayList<Results> , context: Context){
        this.result = productosCatalog
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(
                R.layout.adapter_languages,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = result[position]
        holder.bind(item, context)
    }

    override fun getItemCount(): Int {
        return result.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById(R.id.tvSuperhero) as TextView
        val realName = view.findViewById(R.id.tvRealName) as TextView
        val publisher = view.findViewById(R.id.tvPublisher) as TextView
        val avatar = view.findViewById(R.id.ivAvatar) as ImageView

        fun bind(items:Results, context: Context){
            name.text = items.original_title
            realName.text = items.overview
            publisher.text = items.original_language
            if (items.poster_path!!.isNotEmpty()){
                avatar.loadUrl("https://image.tmdb.org/t/p/original/"+items.poster_path)
            }


        }
        fun ImageView.loadUrl(url: String) {
            Picasso.with(context).load(url).placeholder(R.drawable.default_movie).into(this)
        }

    }

    fun addData(listItems: ArrayList<Results>) {
        var size = this.result.size
        this.result.addAll(listItems)
        var sizeNew = this.result.size
        notifyItemRangeChanged(size, sizeNew)
    }
}
