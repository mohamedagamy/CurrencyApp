package com.example.currencyapp

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

fun Context.showToast(msg:String){
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}

fun get3DaysAgo():List<String>{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val list = mutableListOf<String>()

    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE,0)
    val firstDay: String = dateFormat.format(calendar.time)
    calendar.add(Calendar.DATE,-1)
    val secondDay: String = dateFormat.format(calendar.time)
    calendar.add(Calendar.DATE,-2)
    val thirdDay: String = dateFormat.format(calendar.time)

    list.addAll(listOf(firstDay,secondDay,thirdDay))
    return list
}