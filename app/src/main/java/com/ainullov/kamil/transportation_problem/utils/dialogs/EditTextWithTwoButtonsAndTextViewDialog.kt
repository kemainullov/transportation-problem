package com.ainullov.kamil.transportation_problem.utils.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.utils.hideKeyboard
import com.ainullov.kamil.transportation_problem.utils.showKeyboard
import kotlinx.android.synthetic.main.dialog_edittext_with_two_buttons_and_textview.*

class EditTextWithTwoButtonsAndTextViewDialog : DialogFragment() {

    companion object {
        fun newInstance(
            title: String,
            positiveBtnTitle: String,
            negativeBtnTitle: String,
            editTextHint: String,
            onClickListener: (Int) -> Unit
        ): EditTextWithTwoButtonsAndTextViewDialog {
            val dialog = EditTextWithTwoButtonsAndTextViewDialog()
            val args = Bundle()
            args.putString("title", title)
            args.putString("positiveBtnTitle", positiveBtnTitle)
            args.putString("negativeBtnTitle", negativeBtnTitle)
            args.putString("editTextHint", editTextHint)
            dialog.arguments = args
            dialog.onClickListener = onClickListener
            return dialog
        }

        const val ACTION_NEGATIVE = 0
        const val ACTION_POSITIVE = 1
    }

    private lateinit var onClickListener: (Int) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_background_with_corner)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let {
            val dialog = Dialog(it)
            dialog.setContentView(R.layout.dialog_edittext_with_two_buttons_and_textview)
            setOnClickListeners(dialog)
//            dialog.window?.clearFlags(FLAG_DIM_BEHIND) // убрать фон

            dialog.et_enter.requestFocus()
            showKeyboard(dialog.context)

            dialog.tv_title.text =
                arguments?.getString("title") ?: resources.getString(R.string.adding)
            dialog.et_enter.hint =
                arguments?.getString("editTextHint") ?: resources.getString(R.string.enter)
            dialog.btn_negative.text =
                arguments?.getString("negativeBtnTitle") ?: resources.getString(R.string.cancel)
            dialog.btn_positive.text =
                arguments?.getString("positiveBtnTitle") ?: resources.getString(R.string.add)
            return dialog
        }
        return super.onCreateDialog(savedInstanceState)
    }

    private fun setOnClickListeners(dialog: Dialog) {
        dialog.btn_positive.setOnClickListener {
            if (dialog.et_enter.text.isNotEmpty()) {
                (parentFragment as OnDialogResultListener).dialogResultEvent(dialog.et_enter.text.toString().toInt())
                onClickListener(ACTION_POSITIVE)
                hideKeyboard(dialog.context, dialog.btn_positive)
                dismiss()
            } else Toast.makeText(
                activity,
                getString(R.string.field_must_not_be_empty),
                Toast.LENGTH_SHORT
            ).show()

        }
        dialog.btn_negative.setOnClickListener {
            onClickListener(ACTION_NEGATIVE)
            hideKeyboard(dialog.context, dialog.btn_negative)
            dismiss()
        }
    }
}
