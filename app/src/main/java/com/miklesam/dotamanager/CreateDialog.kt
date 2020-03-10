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
        fun onDialogPositiveClick(position: Array<Int>)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = activity!!.layoutInflater
        val mycustomview = inflater.inflate(R.layout.layout_dialog, null)
        val spiner1 = mycustomview.findViewById<Spinner>(R.id.spiner1)
        val spiner2 = mycustomview.findViewById<Spinner>(R.id.spiner2)
        val spiner3 = mycustomview.findViewById<Spinner>(R.id.spiner3)
        val spiner4 = mycustomview.findViewById<Spinner>(R.id.spiner4)
        val spiner5 = mycustomview.findViewById<Spinner>(R.id.spiner5)

        //AllHeroes.clear()
        //HeroInit()

        builder.setView(mycustomview)
        builder.setTitle("Раставьте героев по линиям ")
        builder.setPositiveButton("Расставить") { _, _ ->
            mListener?.onDialogPositiveClick(
                arrayOf(
                    spiner1.selectedItemPosition, spiner2.selectedItemPosition,
                    spiner3.selectedItemPosition, spiner4.selectedItemPosition,
                    spiner5.selectedItemPosition
                )
            )
            Lock = false
            val what = spiner3.selectedItemPosition

        }


        val imaopl = mycustomview.findViewById<ImageView>(R.id.ima1)
        val imaop2 = mycustomview.findViewById<ImageView>(R.id.ima2)
        val imaop3 = mycustomview.findViewById<ImageView>(R.id.ima3)
        val imaop4 = mycustomview.findViewById<ImageView>(R.id.ima4)
        val imaop5 = mycustomview.findViewById<ImageView>(R.id.ima5)
        imaopl.setImageResource(R.drawable.dazzle_mipmap)
        imaop2.setImageResource(R.drawable.abadon_mipmap)
        imaop3.setImageResource(R.drawable.kunnka_mipmap)
        imaop4.setImageResource(R.drawable.monkeyking_mipmap)
        imaop5.setImageResource(R.drawable.zeus_mipmap)
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
        spiner1.adapter = mad
        spiner2.adapter = mad
        spiner3.adapter = mad
        spiner4.adapter = mad
        spiner5.adapter = mad


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