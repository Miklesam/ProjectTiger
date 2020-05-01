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
import com.miklesam.dotamanager.datamodels.Heroes
import kotlinx.android.synthetic.main.layout_dialog.*

class LineningDialog() : AppCompatDialogFragment() {
    constructor(myListener: NoticeDialogListener, heroes: ArrayList<Int>?) : this() {
        mListener = myListener
        heroesList = heroes
    }

    var Lock = true
    var side = true
    var mListener: NoticeDialogListener? = null
    var heroesList: ArrayList<Int>? = null
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
        imaopl.setImageResource(Heroes.values().find { it.id == heroesList?.get(0) ?: 0 }!!.mipmap)
        imaop2.setImageResource(Heroes.values().find { it.id == heroesList?.get(1) ?: 0 }!!.mipmap)
        imaop3.setImageResource(Heroes.values().find { it.id == heroesList?.get(2) ?: 0 }!!.mipmap)
        imaop4.setImageResource(Heroes.values().find { it.id == heroesList?.get(3) ?: 0 }!!.mipmap)
        imaop5.setImageResource(Heroes.values().find { it.id == heroesList?.get(4) ?: 0 }!!.mipmap)
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