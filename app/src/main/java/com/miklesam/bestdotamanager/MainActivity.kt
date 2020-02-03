package com.miklesam.bestdotamanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        if (savedInstanceState == null) {
            showFragmentMain()
        }

    }


    private fun showFragmentMain() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentMenu()
        //transaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_right)
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }



}
