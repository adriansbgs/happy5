package com.ftw.happy5test.viewmodel

import androidx.multidex.BuildConfig
import com.ftw.happy5test.api.ApiServices
import com.ftw.happy5test.model.ResponseMovies
import com.ftw.happy5test.utils.API_KEY
import com.ftw.happy5test.utils.BASE_URL
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InstallIn(ApplicationComponent::class)
@Module
class MainRepository @Inject constructor() {
    private val TAG = MainRepository::class.java.simpleName
    fun serviceMainMovie(): ApiServices {
        val client = OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(ApiServices::class.java)
    }

    fun getAllMovies(): Single<ResponseMovies> {
        return serviceMainMovie().getAllMovies(apiKey = API_KEY, limit = 3)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}