package com.gustavozreis.dividespesas.features.utils

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DatePicker() {

    @RequiresApi(Build.VERSION_CODES.N)
    fun datePicker(context: Context, textView: TextView) {

        val myCalendar = Calendar.getInstance()

        val chosenYear = myCalendar.get(Calendar.YEAR)
        val chosenMonth = myCalendar.get(Calendar.MONTH)
        val chosenDay = myCalendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            context,
            { view, year, month, dayOfMonth ->
                val selectedDateRaw = "$dayOfMonth/${month + 1}/$year"

                val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.US)

                val selectedDateParsed = dateFormat.parse(selectedDateRaw) as Date
                val selectedDateFinal = dateFormat.format(selectedDateParsed)

                textView.text = selectedDateFinal
            },
            chosenYear,
            chosenMonth,
            chosenDay
        )

        datePicker.datePicker.maxDate = System.currentTimeMillis()
        datePicker.show()
    }
}
