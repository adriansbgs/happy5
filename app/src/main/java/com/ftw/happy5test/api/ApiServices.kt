package com.ftw.happy5test.api

import com.ftw.happy5test.model.ResponseMovieDetail
import com.ftw.happy5test.model.ResponseMovies
import com.ftw.happy5test.utils.GET_MOVIES_DETAIL
import com.ftw.happy5test.utils.GET_MOVIES_LIST
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET(GET_MOVIES_LIST)
    fun getAllMovies(
        @Query("limit") limit: Int,
        @Query("api_key") apiKey: String
    ): Single<ResponseMovies>

    @GET(GET_MOVIES_DETAIL.plus("{id}"))
    fun getDetailMovie(
        @Path("id") id: Int,
        @Query("limit") limit: Int,
        @Query("api_key") apiKey: String
    ): Single<ResponseMovieDetail>
}