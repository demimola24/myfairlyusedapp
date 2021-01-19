package com.test.fairlyused.ui.userlist

import com.test.fairlyused.di.scope.AppScope
import com.test.fairlyused.ui.base.model.BaseApiResponse
import com.test.fairlyused.ui.userlist.model.response.UserSummary
import retrofit2.http.GET
import javax.inject.Inject

/**
 * Created by Demimola on 2021-01-18.
 */

interface FetchUsersService {
    @GET("user?limit=100")
    suspend fun fetchUsers(): BaseApiResponse<List<UserSummary>>

}

interface FetchUsersDataSource {
    suspend fun fetchUsers() : BaseApiResponse<List<UserSummary>>
}

@AppScope
class FetchUsersRepository @Inject constructor(private val apiService: FetchUsersService) : FetchUsersDataSource {
    override suspend fun fetchUsers(): BaseApiResponse<List<UserSummary>> {
        return apiService.fetchUsers()
    }
}