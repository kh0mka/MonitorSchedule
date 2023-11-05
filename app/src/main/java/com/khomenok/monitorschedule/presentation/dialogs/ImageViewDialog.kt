package com.khomenok.monitorschedule.presentation.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.khomenok.monitorschedule.R
import com.khomenok.monitorschedule.databinding.ImageViewDialogBinding
import com.bumptech.glide.Glide

class ImageViewDialog(
    ctx: Context,
    private val imageView: String,
) : Dialog(ctx) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ImageViewDialogBinding.inflate(LayoutInflater.from(context))
        window?.setBackgroundDrawableResource(R.drawable.dialog_bg_shadow)

        setContentView(binding.root)

        Glide.with(context)
            .load(imageView)
            .into(binding.imagePreview)
    }

}