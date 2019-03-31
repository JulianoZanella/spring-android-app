package com.julianozanella.springandroidapp.view.util

import android.support.v4.app.Fragment

interface IReplaceFragAndTitle {

    fun replaceFragment(fragment: Fragment)
    fun updateToolbarTitleInFragment(titleStringId: Int)
    fun updateToolbarTitleInFragment(title: String)
    fun hideFloatingButton(hide: Boolean = false)
    fun goToHomeFragment()
    fun updateProfileImage()
}