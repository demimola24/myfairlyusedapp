package com.test.fairlyused.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.test.fairlyused.BuildConfig
import com.test.fairlyused.FairlyUsedApp
import com.test.fairlyused.di.viewmodel.ViewModelModule
import com.test.fairlyused.ui.userfulldetails.FetchUserDetailDataSource
import com.test.fairlyused.ui.userfulldetails.FetchUserDetailRepository
import com.test.fairlyused.ui.userfulldetails.FetchUserDetailService
import com.test.fairlyused.ui.userlist.FetchUsersDataSource
import com.test.fairlyused.ui.userlist.FetchUsersRepository
import com.test.fairlyused.ui.userlist.FetchUsersService
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule(val app: FairlyUsedApp) {
    @Singleton
    @Provides
    internal fun provideContext(): Context {
        return app.applicationContext
    }

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =  HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()


    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        client: Lazy<OkHttpClient>, gson: Gson
    ): Retrofit {

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .callFactory(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))

        //get the okhttp3 client for retrofit so that we can inject the
        //client key into every request

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okhttp3clientBuilder = OkHttpClient.Builder()
            .addInterceptor {

             val request = it.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("app-id", BuildConfig.CLIENT_KEY)
                .build()

            it.proceed(request)
        }
            .addInterceptor(logging)
            .readTimeout(63, TimeUnit.SECONDS)

        return  retrofitBuilder.client(okhttp3clientBuilder.build()).build()

    }

    @Provides
    fun provideFetchUsersDataSource(retrofit: Retrofit): FetchUsersDataSource {
        val service = retrofit.create(FetchUsersService::class.java)
        return FetchUsersRepository(service)
    }

    @Provides
    fun provideFetchUserFullDetailsDataSource(retrofit: Retrofit): FetchUserDetailDataSource {
        val service = retrofit.create(FetchUserDetailService::class.java)
        return FetchUserDetailRepository(service)
    }

}