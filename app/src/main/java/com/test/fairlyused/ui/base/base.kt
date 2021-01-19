package com.test.fairlyused.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.fairlyused.R
import com.test.fairlyused.di.Injectable
import kotlinx.android.synthetic.main.fragment_error_dialog.view.*
import javax.inject.Inject

/**
 * Created by Demimola on 2021-01-18.
 */


abstract class BaseFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory


    fun showErrorDialog(
        message: String,
        cancelAction: (() -> Unit)? = null,
        secondaryButton: (() -> Unit)? = null
    ) {
        val dialog = ErrorDialog(
            message,
            cancelButtonAction = cancelAction,
            secondaryButtonAction = secondaryButton
        )
        dialog.show(parentFragmentManager, ErrorDialog.FRAGMENT_TAG)
    }
}


class ErrorDialog(
    var message: String = "An error occurred, please try again later",
    private var secondaryButtonText: String = "Retry",
    private var cancelButtonAction: (() -> Unit)?,
    private var secondaryButtonAction: (() -> Unit)?
) : BaseDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_error_dialog, container, false)
        view.tv_error_msg.text = message
        view.secondary_button.visibility =
            if (secondaryButtonAction != null) View.VISIBLE else View.GONE
        view.secondary_button.text = secondaryButtonText
        view.secondary_button.setOnClickListener {
            secondaryButtonAction?.invoke()
            dialog?.dismiss()
        }

        view.btn_cancel.setOnClickListener {
            cancelButtonAction?.invoke()
            dialog?.dismiss()
        }
        return view
    }

    companion object {
        const val FRAGMENT_TAG = "ErrorDialog"
    }
}

abstract class BaseDialogFragment : DialogFragment() {


    override fun setCancelable(cancelable: Boolean) {
        super.setCancelable(false)
    }

    override fun isCancelable(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }


}

