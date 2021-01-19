package com.test.fairlyused.di

import android.app.Application
import com.test.fairlyused.FairlyUsedApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Demimola on 2021-01-18.
 */

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class, AppModule::class, ActivityModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }

    fun inject(application: FairlyUsedApp)

}