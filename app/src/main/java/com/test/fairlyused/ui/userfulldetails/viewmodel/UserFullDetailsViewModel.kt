package com.test.fairlyused.ui.userfulldetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.fairlyused.ui.userfulldetails.FetchUserDetailDataSource
import com.test.fairlyused.ui.userfulldetails.model.response.UserDetail
import com.test.fairlyused.utils.UIEvent
import com.test.fairlyused.utils.asLiveData
import com.test.fairlyused.utils.toDefaultErrorMessage
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class UserFullDetailsViewModel @Inject internal constructor(private val dataSource: FetchUserDetailDataSource) :
    ViewModel() {

    private val _progress = MutableLiveData<UIEvent<Boolean>>()
    val progressEvent = _progress.asLiveData()

    private val _errorEvent = MutableLiveData<UIEvent<String>>()
    val errorEvent = _errorEvent.asLiveData()

    private val _userFullDetailsEvent = MutableLiveData<UIEvent<UserDetail>>()
    val userFullDetailsEvent = _userFullDetailsEvent.asLiveData()



    fun fetchUserFullDetails(userId : String) {
        viewModelScope.launch {
            Timber.d("Getting User Full Details..")
            _progress.postValue(UIEvent(true))

            runCatching { dataSource.fetchUserDetail(userId) }
                .onSuccess { response ->
                    response.let {
                        _userFullDetailsEvent.postValue(UIEvent(it))
                    }
                }
                .onFailure { exception: Throwable ->
                    exception.printStackTrace()
                    _errorEvent.value = UIEvent(exception.toDefaultErrorMessage())
                }.also {
                    _progress.value = UIEvent(false)
                }
        }
    }
}