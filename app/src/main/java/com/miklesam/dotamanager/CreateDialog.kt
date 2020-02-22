package com.miklesam.dotamanager

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatDialogFragment
import com.miklesam.dotamanager.R

class CreateDialog() : AppCompatDialogFragment() {
    constructor(myListener: NoticeDialogListener) : this() {
        mListener = myListener
    }

    var Lock = true
    var side = true
    var mListener: NoticeDialogListener? = null

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = activity!!.layoutInflater
        val mycustomview = inflater.inflate(R.layout.layout_dialog, null)
        val spiner1 = mycustomview.findViewById<Spinner>(R.id.spiner1)

        //AllHeroes.clear()
        //HeroInit()

        builder.setView(mycustomview)
        builder.setTitle("Раставьте героев по линиям ")
        builder.setPositiveButton("Расставить") { _, _ ->
            var lining = ""
            lining += spiner1.selectedItemPosition.toString()
            mListener?.onDialogPositiveClick(lining)
            Lock = false

        }


        val imaopl = mycustomview.findViewById<ImageView>(R.id.ima1)
        imaopl.setImageResource(R.drawable.ogremagi_mipmap)
         //imaopl.setImageResource(AllHeroes.get(HeroList.get(0)).picked)
        //imaop2.setImageResource(AllHeroes.get(HeroList.get(1)).picked)
        //imaop3.setImageResource(AllHeroes.get(HeroList.get(2)).picked)
        //imaop4.setImageResource(AllHeroes.get(HeroList.get(3)).picked)
        //imaop5.setImageResource(AllHeroes.get(HeroList.get(4)).picked)
        val mad: ArrayAdapter<String>
        if (side) mad = context?.let {
            ArrayAdapter(
                it,
                R.layout.spinner_item,
                resources.getStringArray(R.array.lineList)
            )
        }!!
        else mad = context?.let {
            ArrayAdapter(
                it,
                R.layout.spinner_item,
                resources.getStringArray(R.array.modernlineList)
            )
        }!!
        spiner1!!.adapter = mad


        return builder.create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        if (Lock) {
            Log.w("onDismiss", "inDialog")
            val activity = activity
            if (activity is DialogInterface.OnDismissListener) {
                (activity as DialogInterface.OnDismissListener).onDismiss(dialog)
            }

        }

    }

}