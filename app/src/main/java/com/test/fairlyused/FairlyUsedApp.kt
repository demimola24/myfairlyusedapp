package com.test.fairlyused

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.MutableLiveData
import com.test.fairlyused.di.AppComponent
import com.test.fairlyused.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject



class FairlyUsedApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        AppInjector.setup(this)
    }



    companion object {
        fun coreComponent(context: Context) =
            (context.applicationContext as FairlyUsedApp).appComponent

        @JvmStatic
        fun hasNetwork(context: Context): Boolean {
            var isConnected: Boolean = false // Initial Value
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}