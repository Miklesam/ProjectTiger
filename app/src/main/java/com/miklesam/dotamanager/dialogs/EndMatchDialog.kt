package com.miklesam.dotamanager.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.miklesam.dotamanager.R

class EndMatchDialog() : AppCompatDialogFragment() {
    constructor(myListener: toLobbyInterface, side: Int) : this() {
        mListener = myListener
        sude=side
    }


    var Lock = true
    var sude=0
    var mListener: toLobbyInterface? = null
    interface toLobbyInterface {
        fun goToLobbyClick()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = activity!!.layoutInflater
        val mycustomview = inflater.inflate(R.layout.layout_end_match_dialog, null)
        val match_result_text=mycustomview.findViewById<TextView>(R.id.match_result_text)
        when(sude){
            1->match_result_text.text="Вы выиграли"
            2->match_result_text.text="Вы проиграли"
            3->match_result_text.text="Ничья"
        }




        builder.setView(mycustomview)
        builder.setTitle("Матч закончен")
        builder.setPositiveButton("Закончить") { _, _ ->
            mListener?.goToLobbyClick()
            Lock = false
        }

        return builder.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if(Lock){
            mListener?.goToLobbyClick()
        }

    }

}