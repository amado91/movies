package com.example.gapsi.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gapsi.R
import com.example.gapsi.adapter.CatalogAdapter
import com.example.gapsi.adapter.PaginationScrollListener
import com.example.gapsi.fragments.TrendingFragment
import com.example.gapsi.model.response.RealmCatalog
import com.example.gapsi.model.response.ResponseMoviesPopular
import com.example.gapsi.model.response.Results
import com.example.gapsi.model.response.ResultsRealm
import com.example.gapsi.presenter.ConsultProductPresenter
import com.example.gapsi.presenter.ConsultProductPresenterImpl
import com.example.gapsi.view.ConsultView
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import io.realm.Realm
import io.realm.RealmList
import java.lang.reflect.Type


class MainActivity : AppCompatActivity(), ConsultView {

    private var consultProductPresenter: ConsultProductPresenter? = null
    private lateinit var consult: EditText
    private lateinit var openFragment: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var text1: TextView
    lateinit var mRecyclerView : RecyclerView
    private val mAdapter : CatalogAdapter = CatalogAdapter()
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var page = 1
    private var dataResult : ArrayList<Results> = ArrayList()
    private var addData: RealmList<ResultsRealm> = RealmList()
    private var dataRealm : RealmCatalog = RealmCatalog()
    private lateinit var  realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
        consultProductPresenter = ConsultProductPresenterImpl(this)
        consultProductPresenter!!.consult(page)
        initUI()
        if (dataResult.size > 0){
            loadData()
        }
    }

    private fun initUI(){
        openFragment = findViewById(R.id.button)
        consult = findViewById(R.id.edtWordBrowser)
        progressBar = findViewById(R.id.progress_circular)
        text1 = findViewById(R.id.txtinit)
        mRecyclerView = findViewById(R.id.catalogRecycler)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        openFragment.setOnClickListener {
            openFragment.visibility = View.INVISIBLE
            val fragment = TrendingFragment.newInstance()
            openFragment(fragment)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        openFragment.visibility =View.VISIBLE
    }

    override fun result(result: ResponseMoviesPopular) {
        dataResult = result.results
        dataResult.forEachIndexed { _, element ->
           val info = ResultsRealm()
            info.backdrop_path = element.backdrop_path
            info.original_title = element.original_title
            info.overview = element.overview

            addData.add(info)
        }
        dataRealm.results = addData
        saveMovies(dataRealm)
        saveData()
        loadData()
        progressBar.visibility = View.INVISIBLE
        text1.visibility = View.INVISIBLE
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(dataResult)
        editor.putString("courses", json)
        editor.apply()
        Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("courses", null)
        val type: Type = object : TypeToken<ArrayList<Results>>() {}.type

        dataResult = gson.fromJson<Any>(json, type) as ArrayList<Results>
        viewMoviesAdapter(dataResult)
    }


    private fun viewMoviesAdapter(catalog : ArrayList<Results>) {
        mAdapter.catalogAdapter(catalog, this)
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
    fun getMoreItems() {
        progressBar.visibility = View.VISIBLE
        if (page > 1){
            consultProductPresenter!!.consult(page)
        }
        isLoading = false

        mAdapter.addData(dataResult)
    }

    private fun saveMovies(data:  RealmCatalog) {
        val  info :RealmList<ResultsRealm> = RealmList()
        info.addAll(data.results)

        info.forEachIndexed{ _, resultsRealm ->
            realm.executeTransactionAsync ({
                val student = it.createObject(ResultsRealm::class.java)
                student.original_title = resultsRealm.original_title
                student.overview = resultsRealm.overview
            },{
                Log.d("MainActivity","On Success: Data Written Successfully!")
            },{
                Log.d("MainActivity","On Error: Error in saving Data!")
            })
        }
    }

    override fun operationError() {
        Toast.makeText(this,"Offline", Toast.LENGTH_LONG).show()
        progressBar.visibility = View.INVISIBLE
        loadData()
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}