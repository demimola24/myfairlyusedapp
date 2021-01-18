package com.test.fairlyused

import android.app.Application
import android.content.Context
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
        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as FairlyUsedApp).appComponent
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}