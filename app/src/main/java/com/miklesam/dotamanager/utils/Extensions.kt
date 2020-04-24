package com.miklesam.dotamanager.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
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

