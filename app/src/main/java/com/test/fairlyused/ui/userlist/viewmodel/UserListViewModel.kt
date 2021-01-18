package com.test.fairlyused.ui.userlist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.fairlyused.ui.userlist.FetchUsersDataSource
import com.test.fairlyused.ui.userlist.model.response.UserSummary
import com.test.fairlyused.utils.UIEvent
import com.test.fairlyused.utils.asLiveData
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class UserListViewModel @Inject internal constructor(private val dataSource: FetchUsersDataSource) :
    ViewModel() {

    private val _progressNonBlocking = MutableLiveData<UIEvent<Boolean>>()
    val progressNonBlocking = _progressNonBlocking.asLiveData()

    private val _errorEvent = MutableLiveData<UIEvent<String>>()
    val errorEvent = _errorEvent.asLiveData()

    private val _userListEvent = MutableLiveData<UIEvent<List<UserSummary>>>()
    val userListEvent = _userListEvent.asLiveData()


    init {
        fetchUserList()
    }

    fun fetchUserList() {
        viewModelScope.launch {
            Timber.d("Getting User List..")
            _progressNonBlocking.postValue(UIEvent(true))

            runCatching { dataSource.fetchUsers() }
                .onSuccess { response ->
                    response.let {
                        _userListEvent.postValue(UIEvent(it))
                    }
                }
                .onFailure { exception: Throwable ->
                    Timber.e(exception)
                    _errorEvent.value = UIEvent("Unable to fetch users at the moment")
                }.also {
                    _progressNonBlocking.value = UIEvent(false)
                }
        }
    }
}