package com.miklesam.dotamanager.simplefragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.R
import kotlinx.android.synthetic.main.fragment_morf_news.*

class MorfNews :Fragment(R.layout.fragment_morf_news){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        morf_view.initStartPosition()
        morf_view.start()

    }
}