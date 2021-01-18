package com.test.fairlyused.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Created by Demimola on 2021-01-18.
 */


fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>