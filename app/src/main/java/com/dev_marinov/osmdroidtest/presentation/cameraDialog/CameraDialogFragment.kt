package com.dev_marinov.osmdroidtest.presentation.cameraDialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.dev_marinov.osmdroidtest.R
import com.dev_marinov.osmdroidtest.databinding.FragmentCameraDialogBinding
import com.dev_marinov.osmdroidtest.domain.model.MarkerCityCam
import com.dev_marinov.osmdroidtest.domain.model.MarkerDomofonCam
import com.dev_marinov.osmdroidtest.domain.model.MarkerOffice
import com.dev_marinov.osmdroidtest.domain.model.MarkerOutdoorCam
import com.squareup.picasso.Picasso

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraDialogFragment : DialogFragment() {

    lateinit var binding: FragmentCameraDialogBinding
    lateinit var viewModel: CameraDialogViewModel
    private val args: CameraDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_camera_dialog, container, false)
        setBackgroundDialogFragment()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("4444 ", " CameraDialogFragment loaded")

        initViewModel()
        receiveNavigationArgs()
        initLayoutDialog()

    }

    private fun setBackgroundDialogFragment() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[CameraDialogViewModel::class.java]
    }

    private fun receiveNavigationArgs() {
        viewModel.getNavigationArgs(args.navigationArgs)
    }

    private fun initLayoutDialog() {
        lifecycleScope.launchWhenStarted {
            binding.btRetry.visibility = View.GONE
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.checkEmpty.collect {
                    showButton(it)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            binding.btRetry.visibility = View.GONE
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cameraDialog.collect { cameraDialog ->

                    cameraDialog.titleType?.let {
                        when (it) {
                            is MarkerCityCam -> {
                                //Log.d("4444", " it 3333 MarkerCityCam check 1=" + it)

                                binding.layoutOutDoor.root.visibility = View.GONE
                                binding.layoutOffice.root.visibility = View.GONE
                                binding.layoutDomofon.root.visibility = View.GONE
                                binding.layoutCity.root.visibility = View.VISIBLE
                                binding.layoutCity.tvTitle.text = cameraDialog.titleAddress
                                Picasso.get().load(cameraDialog.previewUrl)
                                    .placeholder(R.drawable.ic_placeholder)
                                    .into(binding.layoutCity.imgPreview)
                                binding.layoutCity.imgCloseWindow.setOnClickListener {
                                    dismissDialogFragment()
                                }
                            }
                            is MarkerOutdoorCam -> {
                                // Log.d("4444", " it 3333 OutdoorCams check 2=" + it)
                                binding.layoutCity.root.visibility = View.GONE
                                binding.layoutOffice.root.visibility = View.GONE
                                binding.layoutDomofon.root.visibility = View.GONE
                                binding.layoutOutDoor.root.visibility = View.VISIBLE
                                binding.layoutOutDoor.tvTitle.text = cameraDialog.titleAddress
                                Picasso.get().load(cameraDialog.previewUrl)
                                    .placeholder(R.drawable.ic_placeholder)
                                    .into(binding.layoutOutDoor.imgPreview)
                                binding.layoutOutDoor.imgCloseWindow.setOnClickListener {
                                    dismissDialogFragment()
                                }
                            }
                            is MarkerDomofonCam -> {
                                //Log.d("4444", " it 3333 MarkerDomofonCam check 3=" + it)
                                binding.layoutOffice.root.visibility = View.GONE
                                binding.layoutCity.root.visibility = View.GONE
                                binding.layoutOutDoor.root.visibility = View.GONE
                                binding.layoutDomofon.root.visibility = View.VISIBLE
                                binding.layoutDomofon.tvTitle.text = cameraDialog.titleAddress
                                binding.layoutDomofon.imgCloseWindow.setOnClickListener {
                                    dismissDialogFragment()
                                }
                            }
                            is MarkerOffice -> {
                                //Log.d("4444", " it 3333 MarkerOffice check 4=" + it)
                                binding.layoutDomofon.root.visibility = View.GONE
                                binding.layoutOutDoor.root.visibility = View.GONE
                                binding.layoutCity.root.visibility = View.GONE
                                binding.layoutOffice.root.visibility = View.VISIBLE
                                binding.layoutOffice.tvTitle.text = cameraDialog.titleAddress
                                binding.layoutOffice.tvWorkTime.text =
                                    "Часы работы: \n" + cameraDialog.worktime
                                binding.layoutOffice.tvVisible.text =
                                    "Телефон: \n" + cameraDialog.visible
                                binding.layoutOffice.imgCloseWindow.setOnClickListener {
                                    dismissDialogFragment()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun dismissDialogFragment() {
        dismiss()
    }

    // метод который сработает когда данные по маркеру не зугрузилимсь
    private fun showButton(isVisible: Boolean) {
        when (isVisible) {
            true -> {
                binding.btRetry.visibility = View.VISIBLE
                binding.btRetry.setOnClickListener {
                    binding.btRetry.visibility = View.GONE
                    viewModel.getData()
                    receiveNavigationArgs()
                }
            }
            false -> {
                binding.btRetry.visibility = View.GONE
            }
        }
    }
}