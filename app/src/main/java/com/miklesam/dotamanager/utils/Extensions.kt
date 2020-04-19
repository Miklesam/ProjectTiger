package com.miklesam.dotamanager.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.R

fun Fragment.showCustomToast(message: String, long: Int) {
    val toast = Toast(this.context)
    toast.duration = Toast.LENGTH_LONG
    val inflater =
        context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view: View = inflater.inflate(R.layout.your_custom_layout, null)
    val customToastText = view.findViewById<TextView>(R.id.customToastText)
    toast.view = view
    customToastText.text=message
    toast.duration=long
    toast.show()
}