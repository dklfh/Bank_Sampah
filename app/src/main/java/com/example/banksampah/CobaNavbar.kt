package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class CobaNavbar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cobanavbar)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val tempat: DrawerLayout = findViewById(R.id.tempat)

        val toggle = ActionBarDrawerToggle(this, tempat, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        tempat.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        val tempat: DrawerLayout = findViewById(R.id.tempat)
        if (tempat.isDrawerOpen(GravityCompat.START)){
            tempat.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}