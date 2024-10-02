package com.neverland.thinkerbell.view.home

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.fragment.app.DialogFragment
import com.neverland.domain.model.univ.DeptContact
import com.neverland.thinkerbell.BuildConfig
import com.neverland.thinkerbell.custom.CustomToast
import com.neverland.thinkerbell.databinding.DialogContactBinding
import com.neverland.thinkerbell.databinding.DialogForceUpdateBinding
import com.neverland.thinkerbell.utils.DisplayUtils

class ForceUpdateDialog : DialogFragment() {

    companion object {
        fun newInstance(): ForceUpdateDialog {
            return ForceUpdateDialog()
        }
    }

    private var mBinding: DialogForceUpdateBinding? = null
    private val binding get() = mBinding!!

    override fun onStart() {
        super.onStart()

        dialog?.let {
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val widthInDp = 304f

            val widthInPx = DisplayUtils.dpToPx(requireContext(), widthInDp).toInt()

            it.window?.setLayout(widthInPx, WRAP_CONTENT)
        }

        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogForceUpdateBinding.inflate(requireActivity().layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDialogUpdate.setOnClickListener {
            moveToPlayStore()
        }
    }

    private fun moveToPlayStore() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
            setPackage("com.android.vending")
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val webIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
            }
            startActivity(webIntent)
        }
    }
}
