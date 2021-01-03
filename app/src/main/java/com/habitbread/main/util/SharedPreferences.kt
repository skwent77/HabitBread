package com.habitbread.main.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {

    private val preferenceFileName = "habitBread"
    private val preferencesGoogleIdToken = "googleIdToken"
    private val preferencesFCMToken = "FCMToken"
    private val preferences: SharedPreferences? =
        context.getSharedPreferences(preferenceFileName, 0)
    /* 파일 이름과 EditText를 저장할 Key 값을 만들고 prefs 인스턴스 초기화 */

    var googleIdToken: String?
        get() = preferences!!.getString(preferencesGoogleIdToken, "")
        set(value) = preferences!!.edit().putString(preferencesGoogleIdToken, value).apply()
    /* get/set 함수 임의 설정. get 실행 시 저장된 값을 반환하며 default 값은 ""
     * set(value) 실행 시 value로 값을 대체한 후 저장 */

    var isTokenRegistered: Boolean
        get() = preferences!!.getBoolean(preferencesFCMToken, true)
        set(value) = preferences!!.edit().putBoolean(preferencesFCMToken, value).apply()

    var FCMToken: String?
        get() = preferences!!.getString(preferencesFCMToken, "")
        set(value) = preferences!!.edit().putString(preferencesFCMToken, value).apply()

    fun clearPreferences() {
        preferences!!.edit().clear().apply();
    }
}