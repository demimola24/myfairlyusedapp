package com.test.fairlyused.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.fairlyused.ui.userfulldetails.viewmodel.UserFullDetailsViewModel
import com.test.fairlyused.ui.userlist.viewmodel.UserListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(UserFullDetailsViewModel::class)
    abstract fun bindUserFullDetailsViewModelViewModel(viewModel: UserFullDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    abstract fun bindUserListViewModel(viewModel: UserListViewModel): ViewModel


    /**
     * Provides the ViewModelFactory
     * */
    @Binds
    abstract fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


}