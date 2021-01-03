package com.habitbread.main.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.habitbread.main.R
import com.habitbread.main.api.FirebaseAPI
import com.habitbread.main.base.BaseApplication
import com.habitbread.main.util.PushUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BaseApplication.preferences.isTokenRegistered) {
            PushUtils().register()
        }
        //FirebaseAPI().sendRegistrationToServer(FirebaseAPI().getCurrentFCMToken().toString())
        FirebaseAPI().getCurrentFCMToken()
    }

    override fun onResume() {
        super.onResume()
        //TODO:(Google Play Service 확인 코드 추가해야 함)
    }
}