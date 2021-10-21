package com.example.gapsi.presenter

import com.example.gapsi.interactor.CatalogTrendingInteractor
import com.example.gapsi.interactor.CatalogTrendingInteractorImpl
import com.example.gapsi.model.response.ResponseMoviesPopular
import com.example.gapsi.view.ConsultView

class ConsultTrendingPresenterImpl(view: ConsultView?) : ConsultTrendingPresenter,
    ConsultProductPresenter {

    private var view: ConsultView? = view
    private var interactor: CatalogTrendingInteractor? = null

    override fun consult(page: Int) {
        interactor = CatalogTrendingInteractorImpl(this)
        if (view != null){
            interactor!!.getCatalogInteractor("e4eaf2ee1bd38ba8dfdd151028c6a1bd", page)
        }
    }

    override fun showResult(result: ResponseMoviesPopular) {
        if (view != null){
            view!!.result(result)
        }
    }

    override fun invalidOperation() {
        if (view != null){
            view!!.operationError()
        }
    }
}