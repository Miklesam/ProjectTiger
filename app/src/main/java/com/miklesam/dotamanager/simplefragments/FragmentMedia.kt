package com.miklesam.dotamanager.simplefragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.utils.showCustomToast
import kotlinx.android.synthetic.main.fragment_media.*

class FragmentMedia :Fragment(R.layout.fragment_media){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        twitter.setOnClickListener { showCustomToast("В процессе реализации",Toast.LENGTH_SHORT) }
        youtube.setOnClickListener { showCustomToast("В процессе реализации",Toast.LENGTH_SHORT) }
        social.setOnClickListener { showCustomToast("В процессе реализации",Toast.LENGTH_SHORT) }
    }
}
