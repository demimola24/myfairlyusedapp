package com.test.fairlyused.di

import com.test.fairlyused.di.scope.AppScope
import com.test.fairlyused.ui.userfulldetails.fragment.UserFullDetailsFragment
import com.test.fairlyused.ui.userlist.fragment.UserListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuildersModule {


    /**Features*/
    @ContributesAndroidInjector
    @AppScope
    internal abstract fun contributeUserListFragment(): UserListFragment

    /**Features*/
    @ContributesAndroidInjector
    @AppScope
    internal abstract fun contributeUserFullDetailsFragment(): UserFullDetailsFragment


}