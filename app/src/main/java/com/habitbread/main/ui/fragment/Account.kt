package com.habitbread.main.ui.fragment

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.habitbread.main.R
import com.habitbread.main.base.BaseApplication
import com.habitbread.main.ui.viewModel.AccountViewModel
import com.habitbread.main.util.AccountUtils
import com.habitbread.main.util.PushUtils
import kotlinx.android.synthetic.main.fragment_account.*

class Account : Fragment() {
    private val accountViewModel : AccountViewModel by viewModels()
    private var userNickname: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        accountViewModel.accountData.observe(viewLifecycleOwner, Observer {
            userNickname = it.accountName
            textview_profile_nickname.text = userNickname
            progress_exp.progress = it.percent
            textview_progress_exp.text = getString(R.string.percentage, it.percent);
            if (it.percent <= 55) {
                textview_progress_exp.setTextColor(Color.parseColor("#80553615"))
            } else {
                textview_progress_exp.setTextColor(Color.parseColor("#FFFFFF"))
            }
            textview_account_exp.text = it.userExp.toString()
            textview_bread_num.text = it.totalItemCount.toString()
            textview_current_bread_num.text = getString(R.string.currentBreadNum, it.totalItemCount)
        })
        setOnClickListener()
        setOnToggleListener()
        switch_alarm.isChecked = BaseApplication.preferences.isTokenRegistered
    }

    private fun setOnToggleListener() {
        switch_alarm.setOnCheckedChangeListener { _, isChecked ->
            BaseApplication.preferences.isTokenRegistered = isChecked
            if (isChecked) {
                PushUtils().register()
            } else {
                PushUtils().unregister()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accountViewModel.getUserInfo()
    }
    private fun setNickNameButton() {
        imageButton_change_nickname.setOnClickListener {
            val dialogEditText = getDialogEditText()
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("닉네임을 변경합니다")
                .setView(dialogEditText)
                .setPositiveButton("변경!") { _: DialogInterface, _: Int ->
                    if (dialogEditText.length() < 0) {
                        Toast.makeText(requireContext(), "닉네임 길이는 1글자 이상이여야 합니다~", Toast.LENGTH_SHORT).show()
                    } else {
                        accountViewModel.updateUserNickname(dialogEditText.text.toString())
                        accountViewModel.userInfoData.observe(viewLifecycleOwner, Observer {
                            textview_profile_nickname.text = it.userName
                        })
                    }
                }.setNegativeButton("취소!") {
                        dialogInterface, _ -> dialogInterface.cancel()
                }
            dialog.create().show()
        }
    }


    private fun getDialogEditText() : EditText {
        val et = EditText(requireContext())
        et.setText(userNickname)
        return et
    }

    private fun setOnClickListener() {
        imageButton_change_nickname.setOnClickListener {
            setNickNameButton()
        }
        imageButton_delete_account.setOnClickListener {
            deleteAccount()
        }
        imageButton_logout.setOnClickListener {
            signOut()
        }
        imageButton_change_info.setOnClickListener {
            showNotReadyToast()
        }
    }

    private fun showNotReadyToast() {
        Toast.makeText(this.context, "아직 준비중인 기능입니다.", Toast.LENGTH_SHORT).show()
    }

    private fun deleteAccount() {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("정말 탈퇴 하시겠습니까??")
            .setPositiveButton("네") { _, _->
                PushUtils().unregister()
                accountViewModel.deleteAccount()
                AccountUtils(this.requireContext()).revokeAccess().addOnCompleteListener {
                    backToLogin()
                }
            }.setNegativeButton("아니요") { dialogInterface , _->
                dialogInterface.dismiss()
            }
        dialog.create().show()
    }

    private fun signOut() {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("정말 로그아웃 하시겠습니까??")
            .setPositiveButton("네") { _,_->
                PushUtils().unregister()
                AccountUtils(this.requireContext()).signOut().addOnCompleteListener {
                    backToLogin()
                }
            }.setNegativeButton("아니요") { dialogInterface, _->
                dialogInterface.dismiss()
            }
        dialog.create().show()
    }

    private fun backToLogin() {
        Log.d("HabitBread", "Back To Login")
        findNavController().navigate(R.id.action_viewPager_to_login)
    }
}