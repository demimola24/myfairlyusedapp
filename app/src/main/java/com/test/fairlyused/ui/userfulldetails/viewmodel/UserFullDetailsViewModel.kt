package com.test.fairlyused.ui.userfulldetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.fairlyused.ui.userfulldetails.FetchUserDetailDataSource
import com.test.fairlyused.ui.userfulldetails.model.response.UserDetail
import com.test.fairlyused.utils.UIEvent
import com.test.fairlyused.utils.asLiveData
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class UserFullDetailsViewModel @Inject internal constructor(private val dataSource: FetchUserDetailDataSource) :
    ViewModel() {

    private val _progressNonBlocking = MutableLiveData<UIEvent<Boolean>>()
    val progressNonBlocking = _progressNonBlocking.asLiveData()

    private val _errorEvent = MutableLiveData<UIEvent<String>>()
    val errorEvent = _errorEvent.asLiveData()

    private val _userFullDetailsEvent = MutableLiveData<UIEvent<UserDetail>>()
    val userFullDetailsEvent = _userFullDetailsEvent.asLiveData()



    fun fetchUserFullDetails(userId : String) {
        viewModelScope.launch {
            Timber.d("Getting User Full Details..")
            _progressNonBlocking.postValue(UIEvent(true))

            runCatching { dataSource.fetchUserDetail(userId) }
                .onSuccess { response ->
                    response.let {
                        _userFullDetailsEvent.postValue(UIEvent(it))
                    }
                }
                .onFailure { exception: Throwable ->
                    Timber.e(exception)
                    _errorEvent.value = UIEvent("Unable to fetch user details at the moment")
                }.also {
                    _progressNonBlocking.value = UIEvent(false)
                }
        }
    }
}