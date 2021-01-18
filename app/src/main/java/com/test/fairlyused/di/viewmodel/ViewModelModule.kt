package com.test.fairlyused.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {


//    @Binds
//    @IntoMap
//    @ViewModelKey(SavingGoalDurationViewModel::class)
//    abstract fun bindSavingGoalDurationViewModel(viewModel: SavingGoalDurationViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(FundSavingsAmountViewModel::class)
//    abstract fun bindFundSavingsAmountViewModel(viewModel: FundSavingsAmountViewModel): ViewModel


    /**
     * Provides the MintViewModelFactory
     * */
    @Binds
    abstract fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


}