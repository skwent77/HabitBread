package com.habitbread.main.base

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.habitbread.main.util.SharedPreferences

class BaseApplication : Application() {
    companion object {
        lateinit var preferences : SharedPreferences
        lateinit var firebaseAnalytics: FirebaseAnalytics
    }

    override fun onCreate() {
        super.onCreate()
        preferences = SharedPreferences(applicationContext)
        firebaseAnalytics = Firebase.analytics
    }
}
