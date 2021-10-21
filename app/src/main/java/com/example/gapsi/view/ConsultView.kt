package com.example.gapsi.view

import com.example.gapsi.model.response.Languages
import com.example.gapsi.model.response.ResponseMoviesPopular

interface ConsultView {

    fun result(result: ResponseMoviesPopular)
    fun operationError()

}