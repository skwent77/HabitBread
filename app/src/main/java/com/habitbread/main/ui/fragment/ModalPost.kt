package com.habitbread.main.ui.fragment

class ModalPost(title: String, category: String, description: String?, dayOfWeek: String, alarmTime: String?){
    var title: String = ""
    var category: String = ""
    var description: String? = ""
    var dayOfWeek: String = ""
    var alarmTime: String? = null

    init{
        this.title = title
        this.category = category
        this.description = description
        this.dayOfWeek = dayOfWeek
        this.alarmTime = alarmTime
    }
}