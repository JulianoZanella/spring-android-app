package com.julianozanella.springandroidapp.extensions

import android.support.v4.app.Fragment
import com.julianozanella.springandroidapp.view.util.IReplaceFragAndTitle

fun Fragment.hideFloatingButton(hide: Boolean) {
    if (activity is IReplaceFragAndTitle) {
        (activity as IReplaceFragAndTitle).hideFloatingButton(hide)
    }
}

fun Fragment.replaceFragment(fragment: Fragment) {
    if (activity is IReplaceFragAndTitle) {
        (activity as IReplaceFragAndTitle).replaceFragment(fragment)
    }
}

fun Fragment.setTitle(id: Int) {
    setTitle(getString(id))
}

fun Fragment.setTitle(text: String) {
    if (activity is IReplaceFragAndTitle) {
        (activity as IReplaceFragAndTitle)
            .updateToolbarTitleInFragment(text)
    }
}

fun Fragment.clearAllFragments() {
    if (activity is IReplaceFragAndTitle) {
        (activity as IReplaceFragAndTitle).goToHomeFragment()
    }
}