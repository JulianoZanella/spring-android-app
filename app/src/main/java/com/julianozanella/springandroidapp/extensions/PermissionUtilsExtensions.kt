package com.julianozanella.springandroidapp.extensions

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.julianozanella.springandroidapp.R

/**
 * Checks whether the permission is granted,
 * if it is not,
 * asks the user for permission and performs the function.
 */
fun Fragment.callPermissionRequestIfNeeded(
    requestCode: Int,
    permission: String,
    permissionMessage: String,
    toDo: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(
            activity!!,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        if (this.shouldShowRequestPermissionRationale(permission)) {
            callDialog(
                this,
                requestCode,
                permissionMessage,
                arrayOf(permission)
            )
        } else {
            this.requestPermissions(arrayOf(permission), requestCode)
        }
    } else {
        toDo()
    }
}

fun Fragment.isPermissionGranted(
    permission: String,
    permissions: Array<out String>,
    grantResults: IntArray
): Boolean {
    for (i in permissions.indices) {
        if (permissions[i] == permission && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
            return true
        }
    }
    return false
}

private fun callDialog(
    fragment: Fragment,
    requestCode: Int,
    message: String,
    permissions: Array<String>
) {
    val activity = fragment.activity
    activity ?: return
    val dialog = AlertDialog.Builder(activity).create()
    dialog.setTitle(activity.getString(R.string.permission))
    dialog.setMessage(message)
    dialog.setButton(AlertDialog.BUTTON_POSITIVE, activity.getString(R.string.ok)) { _, _ ->
        fragment.requestPermissions(permissions, requestCode)
        dialog.dismiss()
    }
    dialog.setButton(AlertDialog.BUTTON_NEGATIVE, activity.getString(R.string.cancel)) { _, _ ->
        dialog.dismiss()
    }
    dialog.show()
}