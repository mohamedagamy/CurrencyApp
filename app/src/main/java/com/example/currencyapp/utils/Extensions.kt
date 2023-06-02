package com.example.currencyapp

import android.content.Context
import android.widget.Toast
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Context.showToast(msg:String){
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}