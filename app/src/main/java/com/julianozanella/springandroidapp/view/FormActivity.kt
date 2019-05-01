package com.julianozanella.springandroidapp.view

import android.view.KeyEvent
import android.view.View
import android.widget.TextView

abstract class FormActivity : BaseActivity(), TextView.OnEditorActionListener {

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        mainAction()
        return false
    }

    abstract fun mainAction(view: View? = null)
}