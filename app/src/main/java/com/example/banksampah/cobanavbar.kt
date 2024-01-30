package com.example.banksampah

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import android.view.ViewGroup


class cobanavbar : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var tempat:DrawerLayout
    private lateinit var toolbarTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cobanavbar)

        tempat = findViewById(R.id.tempat)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toolbarTitle = findViewById(R.id.toolbar_title)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, tempat, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        tempat.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null){
            replaceFragment(calculator())
            toolbarTitle.text = "Kalkulator"
            navigationView.setCheckedItem(R.id.nav_kalkulator)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_kalkulator -> {
                replaceFragment(calculator())
                toolbarTitle.text = "Kalkulator"
            }
            // Data
            R.id.nav_DatSatuan -> {
                replaceFragment(datasatuann())
                toolbarTitle.text = "Data Satuan"
            }
            R.id.nav_DatKategori -> {
                replaceFragment(datakategorii())
                toolbarTitle.text = "Data Kategori"
            }
            R.id.nav_DatSub -> {
                replaceFragment(datasubkategorii())
                toolbarTitle.text = "Data Sub-Kategori"
            }

            // Laporan
            R.id.nav_LapTransaksi -> {
                replaceFragment(laporanTransaksii())
                toolbarTitle.text = "Laporan Transaksi"
            }
            R.id.nav_LapKategori -> {
                replaceFragment(laporanKategori())
                toolbarTitle.text = "Laporan Kategori"
            }
            R.id.nav_LapSub -> {
                replaceFragment(laporansubkategorii())
                toolbarTitle.text = "Laporan Sub-Kategori"
            }

            R.id.nav_hapus -> {
                showOverlay()
                toolbarTitle.text = "judul"
            }

            R.id.nav_setting -> {
                replaceFragment(coba3())
                toolbarTitle.text = "judul"
            }
        }
        tempat.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showOverlay() {
        val parentView = window.decorView.findViewById<ViewGroup>(android.R.id.content)
        val overlayView = layoutInflater.inflate(R.layout.perdatasampah, null)
        parentView.addView(overlayView)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (tempat.isDrawerOpen(GravityCompat.START)) {
            tempat.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}

