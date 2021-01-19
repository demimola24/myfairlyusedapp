package com.test.fairlyused

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.test.fairlyused.ui.base.model.BaseApiResponse
import com.test.fairlyused.ui.userlist.FetchUsersDataSource
import com.test.fairlyused.ui.userlist.model.response.UserSummary
import com.test.fairlyused.ui.userlist.viewmodel.UserListViewModel
import com.test.fairlyused.utils.UIEvent
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Demimola on 2021-01-19.
 */


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()


    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private  lateinit var  viewModel : UserListViewModel

    @Mock private  lateinit var dataSource: FetchUsersDataSource
    @Mock private lateinit var userListObserver: Observer<UIEvent<List<UserSummary>>>


    @Test
    fun whenFetchUser_shouldReturnNewUIEvent() {
        testCoroutineRule.runBlockingTest {
            doReturn(BaseApiResponse(data = emptyList<UserSummary>(),message = "",status = ""))
                .`when`(dataSource)
                .fetchUsers()

            viewModel = UserListViewModel(dataSource)
            viewModel.userListEvent.observeForever(userListObserver)
            viewModel.fetchUserList()
            verify(userListObserver,never()).onChanged(UIEvent(emptyList()))
            viewModel.userListEvent.removeObserver(userListObserver)
        }
    }



    @Test
    fun whenFetchUser_shouldReturnSameData() {
        testCoroutineRule.runBlockingTest {
            doReturn(BaseApiResponse(data = emptyList<UserSummary>(),message = "",status = ""))
                .`when`(dataSource)
                .fetchUsers()

            viewModel = UserListViewModel(dataSource)
            viewModel.userListEvent.observeForever(userListObserver)
            viewModel.fetchUserList()
            assertEquals(viewModel.userListEvent.value?.getContentIfNotHandled(),emptyList<UserSummary>() )
            viewModel.userListEvent.removeObserver(userListObserver)
        }
    }


}