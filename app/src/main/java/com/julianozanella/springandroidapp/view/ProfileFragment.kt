package com.julianozanella.springandroidapp.view


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.dto.ClienteDTO
import com.julianozanella.springandroidapp.extensions.*
import com.julianozanella.springandroidapp.service.ImageService
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

    }

    private fun cancelUpload() {
        iv_preview.visibility = View.GONE
        bt_upload.visibility = View.GONE
        bt_cancel.visibility = View.GONE
        bt_camera.isEnabled = true
        bt_gallery.isEnabled = true
    }

    private fun setImage(bitmap: Bitmap) {
        bt_camera.isEnabled = false
        bt_gallery.isEnabled = false
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
