package com.habitbread.main.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.habitbread.main.R
import com.habitbread.main.api.FirebaseAPI
import com.habitbread.main.api.ServerImpl
import com.habitbread.main.base.BaseApplication
import com.habitbread.main.data.GoogleOAuthRequest
import com.habitbread.main.data.GoogleOAuthResponse
import com.habitbread.main.util.AccountUtils
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : Fragment() {
    private val loginTag = "HabitBread"
    private lateinit var client: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button_google_sign_in.setOnClickListener {
//            Toast.makeText(requireContext(), "Hello, World", Toast.LENGTH_SHORT).show()
            signIn()
        }
        client = AccountUtils(requireContext()).googleSignInClient
        if (AccountUtils(requireContext()).isAlreadyLoggedIn()) {
            client.silentSignIn().addOnCompleteListener {
                if (it.isSuccessful) {
                    handleSignInResult(it)
                } else {
                    button_google_sign_in.visibility = View.VISIBLE
                }
            }
        } else {
            button_google_sign_in.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 9001) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                handleSignInResult(task)
            } else {
                Log.d(loginTag, task.exception.toString())
                Log.d(loginTag, task.result.toString())
            }
        }
    }

    private fun signIn() {
        val signInIntent = client.signInIntent
        startActivityForResult(signInIntent, 9001)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                Log.d(loginTag, "handleSignInResult: " + account.idToken)
                sendGoogleOauth(account.idToken)
            } else {
                Log.d(loginTag, "handleSignInResult: " + " no Account")
            }

            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("HabitBread", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun sendGoogleOauth(idToken: String?) {
        val tempRequest = GoogleOAuthRequest(idToken!!)
        val call = ServerImpl.APIService.serverLoginWithGoogle(tempRequest)
        call.enqueue(object : Callback<GoogleOAuthResponse?> {
            override fun onResponse(
                call: Call<GoogleOAuthResponse?>,
                response: Response<GoogleOAuthResponse?>
            ) {
                Log.d(loginTag, "onResponse: ${response.body()}")
                val googleOauthResponse = response.body()
                if (googleOauthResponse?.idToken != null) {
                    Log.d(loginTag, googleOauthResponse.idToken)
                    BaseApplication.preferences.googleIdToken = googleOauthResponse.idToken

                    // Patch FCM New token
                    FirebaseAPI().sendRegistrationToServer(BaseApplication.preferences.FCMToken.toString())

                    BaseApplication.firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
                        param(FirebaseAnalytics.Param.ITEM_ID, BaseApplication.preferences.googleIdToken!!)
                    }
                    findNavController().navigate(R.id.action_login_to_viewPager)
                } else {
                    Log.d(loginTag, googleOauthResponse.toString())
                    Toast.makeText(requireContext(), "Google Oauth Failed", Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun onFailure(
                call: Call<GoogleOAuthResponse?>,
                t: Throwable
            ) {
                Log.d("HabitBread", "Error in Server google oauth")
                t.printStackTrace()
            }
        })
    }
}