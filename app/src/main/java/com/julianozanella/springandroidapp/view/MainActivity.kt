package com.julianozanella.springandroidapp.view

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.dto.ClienteDTO
import com.julianozanella.springandroidapp.extensions.KEY
import com.julianozanella.springandroidapp.extensions.getSharedPreference
import com.julianozanella.springandroidapp.extensions.saveSharedPreferences
import com.julianozanella.springandroidapp.service.AuthService
import com.julianozanella.springandroidapp.service.ImageService
import com.julianozanella.springandroidapp.view.util.IReplaceFragAndTitle
import com.julianozanella.springandroidapp.viewModel.OrderViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, IReplaceFragAndTitle {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            replaceFragment(CartFragment())
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        replaceFragment(CategoriesFragment())
        fillNavHeader()
    }

    private fun fillNavHeader() {
        val headerView = nav_view.getHeaderView(0)
        headerView.nav_header.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            replaceFragment(ProfileFragment())
        }
        val viewModel = ViewModelProviders.of(this)[OrderViewModel::class.java]
        val email: String? = getSharedPreference(KEY.EMAIL, String::class.java) as String?
        if (email != null) {
            viewModel.findByEmail(email).observe(this, Observer {
                if (it != null) {
                    saveSharedPreferences(KEY.CLIENT, it)
                    headerView.nav_header_title.text = it.nome
                    headerView.nav_header_subtitle.text = it.email
                    ImageService().setClientImage(headerView.riv_header, it.id)
                }
            })
        }
    }

    override fun updateProfileImage() {
        val id = (getSharedPreference(KEY.CLIENT, ClienteDTO::class.java) as ClienteDTO).id
        val headerView = nav_view.getHeaderView(0)
        ImageService().setClientImage(headerView.riv_header, id)
    }

    override fun replaceFragment(fragment: Fragment) {
        val tag = fragment.javaClass.simpleName
        supportFragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fl_fragment_container,
                fragment,
                tag
            )
            .addToBackStack(tag)
            .commit()
    }

    override fun goToHomeFragment() {
        supportFragmentManager
            .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        replaceFragment(CategoriesFragment())
    }

    override fun updateToolbarTitleInFragment(titleStringId: Int) {
        updateToolbarTitleInFragment(getString(titleStringId))
    }

    override fun updateToolbarTitleInFragment(title: String) {
        toolbar.title = title
    }

    @SuppressLint("RestrictedApi")
    override fun hideFloatingButton(hide: Boolean) {
        fab.visibility = if (hide) View.GONE else View.VISIBLE
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (supportFragmentManager.backStackEntryCount == 1) {
                finish()
            }
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                replaceFragment(ProfileFragment())
            }
            R.id.nav_categories -> {
                replaceFragment(CategoriesFragment())
            }
            R.id.nav_cart -> {
                replaceFragment(CartFragment())
            }
            R.id.nav_logout -> {
                logout()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
