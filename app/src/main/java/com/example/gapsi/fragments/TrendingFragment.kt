package com.example.gapsi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gapsi.R
import com.example.gapsi.adapter.LanguagesAdapter
import com.example.gapsi.adapter.PaginationScrollListener
import com.example.gapsi.model.response.ResponseMoviesPopular
import com.example.gapsi.model.response.Results
import com.example.gapsi.presenter.ConsultTrendingPresenter
import com.example.gapsi.presenter.ConsultTrendingPresenterImpl
import com.example.gapsi.view.ConsultView
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.lang.reflect.Type


class TrendingFragment: Fragment(), ConsultView {

    private var consultProductPresenter: ConsultTrendingPresenter? = null
    var page = 1
    private lateinit var progressBar: ProgressBar
    private lateinit var text1: TextView
    lateinit var mRecyclerView : RecyclerView
    private val mAdapter : LanguagesAdapter = LanguagesAdapter()
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    private var dataResult : ArrayList<Results> = ArrayList()

    companion object {
        fun newInstance(): TrendingFragment = TrendingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultProductPresenter = ConsultTrendingPresenterImpl(this)
        consultProductPresenter!!.consult(page)

        progressBar = view.findViewById(R.id.progress_circular)
        text1 = view.findViewById(R.id.txtinit)

        mRecyclerView = view.findViewById(R.id.catalogRecycler)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun result(result: ResponseMoviesPopular) {
        dataResult = result.results
        progressBar.visibility = View.INVISIBLE
        text1.visibility = View.INVISIBLE
        saveData()
        loadData()
    }
    fun getMoreItems() {
        progressBar.visibility = View.VISIBLE
        if (page > 1){
            consultProductPresenter!!.consult(page)
        }
        isLoading = false

        mAdapter.addData(dataResult)
    }

    override fun operationError() {
        Toast.makeText(context,"Offline", Toast.LENGTH_LONG).show()
        progressBar.visibility = View.INVISIBLE
        loadData()
    }

    private fun saveData() {
        val sharedPreferences = context!!.getSharedPreferences("shared preferences",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(dataResult)
        editor.putString("courses", json)
        editor.apply()
        Toast.makeText(context, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show()
    }

    private fun loadData() {
        val sharedPreferences = context!!.getSharedPreferences("shared preferences",
            AppCompatActivity.MODE_PRIVATE
        )
        val gson = Gson()
        val json = sharedPreferences.getString("courses", null)
        val type: Type = object : TypeToken<ArrayList<Results>>() {}.type

        dataResult = gson.fromJson<Any>(json, type) as ArrayList<Results>
        viewMoviesAdapter(dataResult)
    }

    private fun viewMoviesAdapter(result: ArrayList<Results> ){
        mAdapter.catalogAdapter(result, context!!)
        mRecyclerView.adapter = mAdapter

        mRecyclerView.addOnScrollListener(object : PaginationScrollListener(mRecyclerView.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                page++

                getMoreItems()
            }
        })
    }
}