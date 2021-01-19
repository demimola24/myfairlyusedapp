package com.test.fairlyused.ui.userfulldetails

import com.test.fairlyused.di.scope.AppScope
import com.test.fairlyused.ui.userfulldetails.model.response.UserDetail
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

/**
 * Created by Demimola on 2021-01-18.
 */

interface FetchUserDetailService {
    @GET("user/{userId}")
    suspend fun fetchUserDetail(@Path("userId") userId: String): UserDetail

}

interface FetchUserDetailDataSource {
    suspend fun fetchUserDetail(userId: String) : UserDetail
}

@AppScope
class FetchUserDetailRepository @Inject constructor(private val apiService: FetchUserDetailService) : FetchUserDetailDataSource {
    override suspend fun fetchUserDetail(userId: String): UserDetail {
        return apiService.fetchUserDetail(userId)
    }
}