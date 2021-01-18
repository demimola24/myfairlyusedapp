package com.test.fairlyused.ui.userfulldetails.model.response

import androidx.annotation.Keep

@Keep
data class UserDetail(
    val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    var phone: String,
    var email: String,
    val picture: String
)
