package models

import java.util.*

class ReminderModel(var title: String,var description: String, var date :Date, var location:String, var priority:String, var occurrence:String) {
    override fun toString(): String {
        if (title.isNotBlank()) {
            return title
        }
        return description
    }
}