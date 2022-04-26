package com.gustavozreis.dividespesas.features.utils

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class DataPicker() {

    @RequiresApi(Build.VERSION_CODES.N)
    fun datePicker(context: Context): String {
        var selectedDate: String = ""
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val selectedDateRaw = "$dayOfMonth/${month + 1}/$year"

            val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.US)

            val selectedDateParsed = dateFormat.parse(selectedDateRaw) as Date
            selectedDate = dateFormat.format(selectedDateParsed)

        }

        DatePickerDialog(context, datePicker,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONDAY),
            myCalendar.get(Calendar.DAY_OF_MONTH)).show()

        return selectedDate
    }
}
