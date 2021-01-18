package com.test.fairlyused.ui.userlist.model.response

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class UserSummary(
    val id: String,
    val title: String,
    val firstName: String,
    val lastName: String
)
