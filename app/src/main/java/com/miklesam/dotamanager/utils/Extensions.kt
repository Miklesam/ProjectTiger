package com.miklesam.dotamanager.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    customToastText.text = message
    toast.duration = long
    toast.show()
}

fun AppCompatActivity.replaceFragmentFromRightToLeft(fragment: Fragment, addToBackStack: Boolean) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.setCustomAnimations(
        R.anim.enter_right_to_left,
        R.anim.exit_right_to_left,
        R.anim.enter_left_to_right,
        R.anim.exit_left_to_right
    )
    transaction.replace(R.id.fragment_holder, fragment)
    if (addToBackStack) transaction.addToBackStack(null)
    transaction.commit()
}

fun AppCompatActivity.replaceFragmentFromLeftToRight(fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.setCustomAnimations(
        R.anim.enter_left_to_right,
        R.anim.exit_left_to_right,
        R.anim.enter_right_to_left,
        R.anim.exit_right_to_left
    )
    transaction.replace(R.id.fragment_holder, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}

fun AppCompatActivity.replaceFragmentFromBottomToTop(fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()

    transaction.setCustomAnimations(
        R.anim.enter_top_to_bottom,
        R.anim.exit_top_to_bottom,
        R.anim.enter_bottom_to_top,
        R.anim.exit_bottom_to_top
    )
    transaction.replace(R.id.fragment_holder, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}

fun AppCompatActivity.replaceFragmentFromTopToBottom(fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.setCustomAnimations(
        R.anim.enter_bottom_to_top,
        R.anim.exit_bottom_to_top,
        R.anim.enter_top_to_bottom,
        R.anim.exit_top_to_bottom
    )
    transaction.replace(R.id.fragment_holder, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.fragment_holder, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}

fun Fragment.hideKeyboard() {
    val view = this.activity?.currentFocus
    if (view != null) {
        val imm =
            this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Fragment.plusDay() {
    var days = PrefsHelper.read(PrefsHelper.CAREER_DAY, "0")?.toInt()
    var months = PrefsHelper.read(PrefsHelper.CAREER_MONTH, "0")?.toInt()
    var years = PrefsHelper.read(PrefsHelper.CAREER_YEAR, "0")?.toInt()
    days = days?.plus(1)
    if (days == 31) {
        days = 0
        months = months?.plus(1)
        if (months == 12) {
            months = 0
            years = years?.plus(1)
        }
    }
    PrefsHelper.write(PrefsHelper.CAREER_DAY, days.toString())
    PrefsHelper.write(PrefsHelper.CAREER_MONTH, months.toString())
    PrefsHelper.write(PrefsHelper.CAREER_YEAR, years.toString())
}

