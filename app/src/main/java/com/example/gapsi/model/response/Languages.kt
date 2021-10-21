package com.example.gapsi.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Languages (
    
    @Expose
    @SerializedName("iso_639_1")
    var iso_639_1: String,
    @Expose
    @SerializedName("english_name")
    var english_name: String,
    @Expose
    @SerializedName("name")
    var name: String
)