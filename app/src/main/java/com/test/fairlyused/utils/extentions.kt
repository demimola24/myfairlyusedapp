package com.test.fairlyused.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonParser
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by Demimola on 2021-01-18.
 */


fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun Throwable.toDefaultErrorMessage(): String {
    return when (this) {
        is HttpException -> {
            return when (code()) {
                 504 -> {
                    //server error
                    "Data doesn't exit locally, kindly turn on your network to fetch data"
                }
                in 500..511 -> {
                    //server error
                    "Server error, kindly try again later"
                }
                else -> {
                    //client error
                    return try {
                        val errorJsonString = response()?.errorBody()?.string()
                        Log.i("toDefaultErrorMessage","$errorJsonString")
                        JsonParser().parse(errorJsonString)
                            .asJsonObject["message"]
                            .asString
                    } catch (e: Exception) {
                        return "An unknown error occurred"
                    }
                }
            }
        }
        is SocketTimeoutException -> {
            Timber.d(this)
            return "Request timed out, kindly try again"

        }
        is UnknownHostException ->{
            return "Connection error, please check your connection and try again"
        }
        is ConnectException -> {
            return "Connection error, please check your connection and try again"
        }
        is IOException -> {
            Timber.d(this)
            return "An unknown error occurred"

        }
        else -> {
            Timber.d(this)
            "Unable to process this request, ${this.localizedMessage}"
        }
    }
}

