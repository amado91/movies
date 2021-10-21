package com.example.gapsi.interactor

import android.util.Log
import com.example.gapsi.model.response.ResponseMoviesPopular
import com.example.gapsi.presenter.ConsultProductPresenter
import com.example.gapsi.service.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CatalogInteractorImpl(consultProductPresenterImpl: ConsultProductPresenter) : CatalogInteractor {

    private val presenter: ConsultProductPresenter? = consultProductPresenterImpl

    private fun onResponse(response: ResponseMoviesPopular) {
        presenter!!.showResult(response)
    }

    private fun onFailure(response: Any) {
        Log.e("Error", response.toString())
        presenter!!.invalidOperation()

    }

    override fun getCatalogInteractor(token: String, page: Int ) {
        val compositeDisposable = CompositeDisposable()

        compositeDisposable.add(
            RetrofitClient.buildService2()
                .getPopular(token,"es-MX", page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response -> onResponse(response) }, { t -> onFailure(t) }))
    }

}



