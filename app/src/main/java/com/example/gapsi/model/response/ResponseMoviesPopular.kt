package com.example.gapsi.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseMoviesPopular (

    @SerializedName("page")
    val page: Int,
    @Expose
    @SerializedName("results")
    var results: ArrayList<Results>,
    @Expose
    @SerializedName("total_pages")
    var total_pages: String,
    @Expose
    @SerializedName("total_results")
    var total_results: String
)