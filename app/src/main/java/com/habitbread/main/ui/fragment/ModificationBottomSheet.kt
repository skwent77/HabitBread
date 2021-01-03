package com.habitbread.main.ui.fragment

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.habitbread.main.R
import com.habitbread.main.ui.viewModel.DetailViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.fragment_modification.*
import ru.ifr0z.timepickercompact.TimePickerCompact

class ModificationBottomSheet : BottomSheetDialogFragment() {

    private val modificationTag: String = "ModificationBottomSheet"
    private var getHabitId: Int = -1
    private var getHabitTitle: String = ""
    private var getHabitCategory: String = "기타"
    private var getHabitDescription: String? = ""
    private var getHabitAlarmDay: String = ""
    private var getHabitAlarmTime: String? = ""
    private lateinit var timePickerAlarmTime: TimePickerCompact
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var textViewIsAlarmChecked: TextView
    private lateinit var switchAlarm: SwitchMaterial
    private lateinit var setNewDataOnHabitListener: SetNewDataOnHabitListener
    private val detailViewModel: DetailViewModel by viewModels()

    override fun getTheme(): Int {
        return R.style.bottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modification, container, false)
        timePickerAlarmTime = view.findViewById(R.id.timepicker_alarm_time)
        editTextTitle = view.findViewById(R.id.editText_title)
        editTextDescription = view.findViewById(R.id.editText_description)
        textViewIsAlarmChecked = view.findViewById(R.id.textView_isAlarmChecked)
        switchAlarm = view.findViewById(R.id.switch_alarm)
        setPreviousDetailData(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onClickDayOfWeekChips()
        onCheckAlarmSwith()
        onModifyCancel()
        onModifyDone()
        onClickDelete()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            setNewDataOnHabitListener = context as SetNewDataOnHabitListener
        }catch (e: ClassCastException) {
            Log.e(modificationTag, "onAttach Error")
        }
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

    private fun setPreviousDetailData(view: View) {
        // get previous detail datas from activity
        getHabitId = this.requireArguments().getInt("habitId")
        getHabitTitle = this.requireArguments().getString("title").toString()
        getHabitDescription = this.arguments?.getString("description")
        getHabitAlarmDay = this.requireArguments().getString("dayOfWeek").toString()
        getHabitAlarmTime = this.requireArguments().getString("alarmTime")

        // set previous detail data
        editTextTitle.hint = getHabitTitle
        editTextTitle.setHintTextColor(Color.parseColor("#000000"))
        if(getHabitDescription == null) {
            editTextDescription.hint = "내용을 적어주세요"
        }else {
            editTextDescription.hint = getHabitDescription
            editTextDescription.setHintTextColor(Color.parseColor("#000000"))
        }
        for(i in 0..6) {
            if(getHabitAlarmDay[i] == '1') {
                view.findViewWithTag<Chip>(i.toString()).isCheckable = true
                view.findViewWithTag<Chip>(i.toString()).isChecked = true
                view.findViewWithTag<Chip>(i.toString()).setTextColor(Color.parseColor("#FFFFFF"))
                view.findViewWithTag<Chip>(i.toString()).isCheckable = false
            }
        }
        if(getHabitAlarmTime != null) {
            val previousAlarmHour: Int = getHabitAlarmTime!!.substring(0, 2).toInt()
            val previousAlarmMinute: Int = getHabitAlarmTime!!.substring(3, 5).toInt()
            timePickerAlarmTime.hour = previousAlarmHour
            timePickerAlarmTime.minute = previousAlarmMinute
        }else {
            timePickerAlarmTime.visibility = View.INVISIBLE
            textViewIsAlarmChecked.visibility = View.VISIBLE
            switchAlarm.isChecked = false
        }
    }

    private fun onClickDayOfWeekChips(){
        chip_mon.setOnClickListener {
            Toast.makeText(context, "죄송합니다. 요일은 수정할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
        chip_tue.setOnClickListener {
            Toast.makeText(context, "죄송합니다. 요일은 수정할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
        chip_wed.setOnClickListener {
            Toast.makeText(context, "죄송합니다. 요일은 수정할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
        chip_thu.setOnClickListener {
            Toast.makeText(context, "죄송합니다. 요일은 수정할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
        chip_fri.setOnClickListener {
            Toast.makeText(context, "죄송합니다. 요일은 수정할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
        chip_sat.setOnClickListener {
            Toast.makeText(context, "죄송합니다. 요일은 수정할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
        chip_sun.setOnClickListener {
            Toast.makeText(context, "죄송합니다. 요일은 수정할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCheckAlarmSwith() {
        switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                timePickerAlarmTime.visibility = View.VISIBLE
                textViewIsAlarmChecked.visibility = View.INVISIBLE
            }else {
                timePickerAlarmTime.visibility = View.INVISIBLE
                textViewIsAlarmChecked.visibility = View.VISIBLE
            }
        }
    }

    private fun onModifyDone(){
        imageView_done.setOnClickListener {
            val getChangedTitle = editTextTitle.text.toString()
            val getChangedDescription = editTextDescription.text.toString()

            // check all is entered
            if(getChangedTitle.isNotEmpty()) {
                getHabitTitle = getChangedTitle
            }
            if(getChangedDescription.isNotEmpty()) {
                getHabitDescription = getChangedDescription
            }
            getHabitAlarmTime = if(switchAlarm.isChecked) {
                timePickerAlarmTime.hour.toString() + ":" + timePickerAlarmTime.minute.toString()
            } else {
                ""
            }

            setNewDataOnHabitListener.setNewDataOnHabit(getHabitTitle, getHabitCategory, getHabitDescription, getHabitAlarmTime)
            findNavController().navigateUp()
        }
    }

    interface SetNewDataOnHabitListener {
        fun setNewDataOnHabit(newTitle: String, newCategory: String, newDescription: String?, newAlarmTime: String?)
    }

    private fun onModifyCancel(){
        imageView_close.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onClickDelete() {
        textView_modify_delete.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
                .setMessage("정말 삭제 하시겠습니까?")
                .setPositiveButton("예") { _, _->
                    detailViewModel.deleteHabit(getHabitId)
                    detailViewModel.deleteData.observe(this, Observer {
                        if(it.message == "success") {
                            findNavController().navigate(R.id.action_modificationBottomSheet_to_viewPager)
                            Toast.makeText(context, "습관빵이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        }else {
                            Toast.makeText(context, "죄송합니다. 오류로 인해 습관빵이 삭제되지 않았습니다.", Toast.LENGTH_SHORT).show()
                        }
                    })
                }.setNegativeButton("아니요") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
            dialog.create().show()
        }
    }
}