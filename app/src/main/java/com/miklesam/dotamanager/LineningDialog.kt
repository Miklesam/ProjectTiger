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
import kotlinx.android.synthetic.main.layout_dialog.*

class LineningDialog() : AppCompatDialogFragment() {
    constructor(myListener: NoticeDialogListener) : this() {
        mListener = myListener
    }

    var Lock = true
    var side = true
    var mListener: NoticeDialogListener? = null
    lateinit var spiner1: Spinner
    lateinit var spiner2: Spinner
    lateinit var spiner3: Spinner
    lateinit var spiner4: Spinner
    lateinit var spiner5: Spinner

    interface NoticeDialogListener {
        fun onDialogPositiveClick(position: Array<Int>)
        fun onDialogDismissClick(position: Array<Int>)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = activity!!.layoutInflater
        val mycustomview = inflater.inflate(R.layout.layout_dialog, null)
        spiner1 = mycustomview.findViewById<Spinner>(R.id.spiner1)
        spiner2 = mycustomview.findViewById<Spinner>(R.id.spiner2)
        spiner3 = mycustomview.findViewById<Spinner>(R.id.spiner3)
        spiner4 = mycustomview.findViewById<Spinner>(R.id.spiner4)
        spiner5 = mycustomview.findViewById<Spinner>(R.id.spiner5)

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

        }


        val imaopl = mycustomview.findViewById<ImageView>(R.id.ima1)
        val imaop2 = mycustomview.findViewById<ImageView>(R.id.ima2)
        val imaop3 = mycustomview.findViewById<ImageView>(R.id.ima3)
        val imaop4 = mycustomview.findViewById<ImageView>(R.id.ima4)
        val imaop5 = mycustomview.findViewById<ImageView>(R.id.ima5)
        imaopl.setImageResource(R.drawable.monkeyking_mipmap)
        imaop2.setImageResource(R.drawable.zeus_mipmap)
        imaop3.setImageResource(R.drawable.abadon_mipmap)
        imaop4.setImageResource(R.drawable.kunnka_mipmap)
        imaop5.setImageResource(R.drawable.dazzle_mipmap)
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
            mListener?.onDialogPositiveClick(
                arrayOf(
                    spiner1.selectedItemPosition, spiner2.selectedItemPosition,
                    spiner3.selectedItemPosition, spiner4.selectedItemPosition,
                    spiner5.selectedItemPosition
                )
            )

        }

    }

}