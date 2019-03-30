package com.julianozanella.springandroidapp.view


import android.Manifest
import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.dto.ClienteDTO
import com.julianozanella.springandroidapp.extensions.*
import com.julianozanella.springandroidapp.service.ImageService
import com.julianozanella.springandroidapp.viewModel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[ProfileViewModel::class.java]
        val clienteDTO = activity!!.getSharedPreference(KEY.CLIENT, ClienteDTO::class.java) as ClienteDTO?
        clienteDTO?.let {
            tv_client_name.text = it.nome
            tv_client_email.text = it.email
            ImageService().setClientImage(riv_client, it.id)
        }
        bt_camera.setOnClickListener { callCamera() }
        bt_gallery.setOnClickListener { callGallery() }
        bt_upload.setOnClickListener { upload() }
        bt_cancel.setOnClickListener { cancelUpload() }
    }

    private fun callCamera() {
        callPermissionRequestIfNeeded(
            REQUEST_CAMERA,
            Manifest.permission.CAMERA,
            getString(R.string.permission_message_camera),
            openCamera
        )
    }

    private fun callGallery() {
        callPermissionRequestIfNeeded(
            REQUEST_GALLERY,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            getString(R.string.permission_message_gallery),
            openGallery
        )
    }

    private val openCamera = {
        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(i, REQUEST_CAMERA)
    }

    private val openGallery = {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    private fun upload() {
        val photo = iv_preview.drawable
        val bitmap: Bitmap = (photo as BitmapDrawable).bitmap
        viewModel.uploadProfilePicture(bitmap).observe(this, Observer {
            if (it != null) {
                Log.d("Upload", it)
                //TODO("Atualizar imagem")
                cancelUpload()
            }
        })
    }

    private fun cancelUpload() {
        iv_preview.visibility = View.GONE
        bt_upload.visibility = View.GONE
        bt_cancel.visibility = View.GONE
    }

    private fun setImage(bitmap: Bitmap) {
        iv_preview.setImageBitmap(bitmap)
        iv_preview.visibility = View.VISIBLE
        bt_upload.visibility = View.VISIBLE
        bt_cancel.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.tittle_profile)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA -> {
                if (isPermissionGranted(Manifest.permission.CAMERA, permissions, grantResults)) openCamera()
            }
            REQUEST_GALLERY -> {
                if (isPermissionGranted(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        permissions,
                        grantResults
                    )
                ) openGallery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val service = ImageService()
            when (requestCode) {
                REQUEST_CAMERA -> {
                    if (data?.extras != null) {
                        setImage(service.getBitmap(data.extras!!))
                    }
                }
                REQUEST_GALLERY -> {
                    if (data?.data != null) {
                        setImage(service.getBitmap(context!!.contentResolver, data.data!!))
                    }
                }
            }
        }
    }

    companion object {
        const val REQUEST_CAMERA = 875
        const val REQUEST_GALLERY = 876
    }
}
