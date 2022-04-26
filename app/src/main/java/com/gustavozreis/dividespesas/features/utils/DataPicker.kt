package com.gustavozreis.dividespesas.features.utils

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class DataPicker(val context: Context, val view: EditText) {

    val myCalendar = Calendar.getInstance()

    val chosenYear = myCalendar.get(Calendar.YEAR)
    val chosenMonth = myCalendar.get(Calendar.MONTH)
    val chosenDay = myCalendar.get(Calendar.DAY_OF_MONTH)

    @RequiresApi(Build.VERSION_CODES.N)
    fun datePicker(): String {
        var selectedDate: String = ""
        DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                selectedDate = "$dayOfMonth/${month + 1}/$year"

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            },
            chosenYear,
            chosenMonth,
            chosenDay
        )
        return selectedDate
    }

}