package com.example.gapsi.model.response

import io.realm.RealmList
import io.realm.RealmObject


open class ResultsRealm(
    var backdrop_path: String? = "",
    var genre_ids: RealmList<String> = RealmList(),
    var original_language: String? = null,
    var original_title: String? = null,
    var title: String? = null,
    var original_name: String? = null,
    var poster_path: String? = null,
    var vote_count: String? = null,
    var vote_average: String? = null,
    var origin_country: RealmList<String> = RealmList(),
    var overview: String? = null,
    var name: String? = null,
    var popularity: Double = 0.0,
    var media_type: String? = null

): RealmObject()