package com.miklesam.dotamanager.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.FragmentManager
import com.miklesam.dotamanager.R
import kotlinx.android.synthetic.main.message_dialog_layout.view.*

class MessageDialog : androidx.fragment.app.DialogFragment() {

    companion object {

        private const val PARAM_TITLE = "param_title"
        private const val PARAM_MESSAGE = "param_message"
        private const val PARAM_BUTTON_POSITIVE = "param_button_positive"
        private const val PARAM_CANCEL_ON_TOUCH_OUTSIDE = "param_cancel_on_touch_outside"

        @JvmStatic
        fun newInstance(
            title: String?,
            message: String?,
            positiveButton: String?,
            cancelOnTouchOutside: Boolean
        ): MessageDialog {
            val confirmDialog = MessageDialog()
            val bundle = Bundle()
            bundle.putString(PARAM_TITLE, title)
            bundle.putString(PARAM_MESSAGE, message)
            bundle.putString(PARAM_BUTTON_POSITIVE, positiveButton)
            bundle.putBoolean(PARAM_CANCEL_ON_TOUCH_OUTSIDE, cancelOnTouchOutside)
            confirmDialog.arguments = bundle
            return confirmDialog
        }
    }

    private var shown = false
    var overlapEnabled: Boolean = false
    var onDialogClickListener: OnDialogClickListener? = null

    interface OnDialogClickListener {
        fun onPositiveButtonClicked()
    }

    private val okListener = View.OnClickListener {
        onDialogClickListener = activity as OnDialogClickListener
        onDialogClickListener?.onPositiveButtonClicked()
        dismiss()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (shown && !overlapEnabled) return
        try {
            super.show(manager, tag)
            shown = true
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(activity, R.layout.message_dialog_layout, null)
        if (!TextUtils.isEmpty(arguments?.getString(PARAM_BUTTON_POSITIVE))) {
            view.commonsOkButton.text = arguments?.getString(PARAM_BUTTON_POSITIVE)
            view.commonsOkButton.visibility = View.VISIBLE
            view.commonsOkButton.setOnClickListener(okListener)
        }
        if (!TextUtils.isEmpty(arguments?.getString(PARAM_TITLE))) {
            view.commonsTitle.text = arguments?.getString(PARAM_TITLE)
            view.commonsTitle.visibility = View.VISIBLE
        }
        if (!TextUtils.isEmpty(arguments?.getString(PARAM_MESSAGE))) {
            view.commonsMessage.text = arguments?.getString(PARAM_MESSAGE)
            view.commonsMessage.visibility = View.VISIBLE
        }
        // Use the Builder class for convenient dialog construction
        val dialog = AlertDialog.Builder(activity)
            .setView(view)
            .create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(
            arguments?.getBoolean(PARAM_CANCEL_ON_TOUCH_OUTSIDE) ?: true
        )
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        shown = false
    }

    override fun onDismiss(dialog: DialogInterface) {
        shown = false
        super.onDismiss(dialog)
    }
}
