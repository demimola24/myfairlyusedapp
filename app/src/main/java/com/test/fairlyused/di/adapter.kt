package com.test.fairlyused.di

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Created by Demimola on 2021-01-18.
 */


open class ActivityLifecycleCallbackAdapter : Application.ActivityLifecycleCallbacks {

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

}