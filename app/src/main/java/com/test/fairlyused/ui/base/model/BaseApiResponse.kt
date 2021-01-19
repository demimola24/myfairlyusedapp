package com.test.fairlyused.ui.base.model

import androidx.annotation.Keep

/**
 * @author by Lawrence on 2020-02-18.
 * for MyMintApp
 */
@Keep
data class BaseApiResponse<out T>(val status: String?, val message: String?, val data: T?)