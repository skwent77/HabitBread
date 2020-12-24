package com.habitbread.main.ui.fragment

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.habitbread.main.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_registraion.*
import org.greenrobot.eventbus.EventBus

class RegistrationBottomSheet : BottomSheetDialogFragment() {

    private var getHabitTitle: String = ""
    private var getHabitCategory: String = "기타"
    private var getHabitDescription: String? = ""
    private var getHabitAlarmDay: String = ""
    private var getHabitAlarmTime: String = ""
    private var days: MutableList<String> = mutableListOf("0", "0", "0", "0", "0", "0", "0")

    override fun getTheme(): Int {
        return R.style.bottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registraion, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onClickDayOfWeekChips()
        onCheckAlarmSwith()
        onRegisterCancel()
        onRegisterDone()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {dialog ->
            val bottomDialog = dialog as BottomSheetDialog
            val bottomSheet = bottomDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
        }
        return bottomSheetDialog
    }

    private fun onClickDayOfWeekChips(){
        chip_mon.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.setTextColor(Color.parseColor("#FFFFFF"))
                days[1] = "1"
            }else {
                buttonView.setTextColor(Color.parseColor("#787877"))
                days[1] = "0"
            }
        }
        chip_tue.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.setTextColor(Color.parseColor("#FFFFFF"))
                days[2] = "1"
            }else {
                buttonView.setTextColor(Color.parseColor("#787877"))
                days[2] = "0"
            }
        }
        chip_wed.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.setTextColor(Color.parseColor("#FFFFFF"))
                days[3] = "1"
            }else {
                buttonView.setTextColor(Color.parseColor("#787877"))
                days[3] = "0"
            }
        }
        chip_thu.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.setTextColor(Color.parseColor("#FFFFFF"))
                days[4] = "1"
            }else {
                buttonView.setTextColor(Color.parseColor("#787877"))
                days[4] = "0"
            }
        }
        chip_fri.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.setTextColor(Color.parseColor("#FFFFFF"))
                days[5] = "1"
            }else {
                buttonView.setTextColor(Color.parseColor("#787877"))
                days[5] = "0"
            }
        }
        chip_sat.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.setTextColor(Color.parseColor("#FFFFFF"))
                days[6] = "1"
            }else {
                buttonView.setTextColor(Color.parseColor("#787877"))
                days[6] = "0"
            }
        }
        chip_sun.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.setTextColor(Color.parseColor("#FFFFFF"))
                days[0] = "1"
            }else {
                buttonView.setTextColor(Color.parseColor("#787877"))
                days[0] = "0"
            }
        }
    }

    private fun onCheckAlarmSwith() {
        switch_alarm.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                timepicker_alarm_time.visibility = View.VISIBLE
                textView_isAlarmChecked.visibility = View.INVISIBLE
            }else {
                timepicker_alarm_time.visibility = View.INVISIBLE
                textView_isAlarmChecked.visibility = View.VISIBLE
            }
        }
    }

    private fun onRegisterDone(){
        imageView_done.setOnClickListener {
            val getNewTitle = editText_title.text.toString()
            getHabitDescription = editText_description.text.toString()
            getHabitAlarmDay = ""
            for(i in 0..6) {
                getHabitAlarmDay += days[i]
            }
            getHabitAlarmTime = if(switch_alarm.isChecked) {
                timepicker_alarm_time.hour.toString() + ":" + timepicker_alarm_time.minute.toString()
            }else {
                ""
            }

            // check all data is entered
            var isDoneReady = true
            if(getNewTitle.isEmpty()) {
                isDoneReady = false
            }else {
                getHabitTitle = getNewTitle
            }
            if(getHabitAlarmDay == "0000000") {
                isDoneReady = false
            }

            // call create habit api
            if(isDoneReady) {
                EventBus.getDefault().post(ModalPost(getHabitTitle, getHabitCategory, getHabitDescription, getHabitAlarmDay, getHabitAlarmTime))
                findNavController().navigateUp()
            } else {
                Toast.makeText(context, "습관빵 이름과 빵 굽는 날은 반드시 입력해주세요!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onRegisterCancel(){
        imageView_close.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}