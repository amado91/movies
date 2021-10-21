package com.example.gapsi.service.`interface`


import com.example.gapsi.model.response.Languages
import com.example.gapsi.model.response.ResponseMoviesPopular
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface WebService {

    @GET("configuration/languages?")
    fun getCatalog(@Query("api_key") token: String): Observable<ArrayList<Languages>>


    @GET("movie/now_playing?")
    fun getTendencia(@Query( "api_key") token: String,
                     @Query("page") page: Int): Observable<ResponseMoviesPopular>


    @GET("movie/popular?")
    fun getPopular(@Query( "api_key") token: String,
                   @Query("language") lenguage: String,
                   @Query("page") page: Int): Observable<ResponseMoviesPopular>

    @GET("discover/movie?")
    fun getPopular(@Query( "api_key") token: String,
                   @Query("language") lenguage: String,
                   @Query("page") page: Int,
                   @Query("sort_by") popular: String): Observable<ResponseMoviesPopular>
}
