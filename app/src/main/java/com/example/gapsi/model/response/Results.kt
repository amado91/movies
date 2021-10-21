package com.example.gapsi.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Results (

    @Expose
    @SerializedName("backdrop_path")
    var backdrop_path: String? = "",
    @Expose
    @SerializedName("genre_ids")
    var genre_ids: ArrayList<String> = ArrayList(),
    @Expose
    @SerializedName("original_language")
    var original_language: String? = null,
    @Expose
    @SerializedName("original_title")
    var original_title: String? = null,
    @Expose
    @SerializedName("title")
    var title: String? = null,
    @Expose
    @SerializedName("original_name")
    var original_name: String? = null,
    @Expose
    @SerializedName("poster_path")
    var poster_path: String? = null,
    @Expose
    @SerializedName("vote_count")
    var vote_count: String? = null,
    @Expose
    @SerializedName("vote_average")
    var vote_average: String? = null,
    @Expose
    @SerializedName("origin_country")
    var origin_country: ArrayList<String> = ArrayList(),
    @Expose
    @SerializedName("overview")
    var overview: String? = null,
    @Expose
    @SerializedName("name")
    var name: String? = null,
    @Expose
    @SerializedName("popularity")
    var popularity: Double = 0.0,
    @Expose
    @SerializedName("media_type")
    var media_type: String? = null

)