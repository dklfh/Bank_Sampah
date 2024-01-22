package com.example.banksampah

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class kalkulator : AppCompatActivity() {
    private var currentValue = 0
    private lateinit var number: TextView
    private lateinit var btnPlus : Button
    private lateinit var btnMinus : Button
    private lateinit var tvDatePicker: TextView
    private lateinit var btnDatePicker: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tvDatePicker = findViewById(R.id.tvDate)
        btnDatePicker = findViewById(R.id.btnDatePicker)
        number = findViewById(R.id.number)
        btnPlus = findViewById(R.id.btnPlus)
        btnMinus = findViewById(R.id.btnMinus)

        updateTextView()

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        btnDatePicker.setOnClickListener {
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnPlus.setOnClickListener {
            currentValue++
            updateTextView()
        }

        btnMinus.setOnClickListener {
            if (currentValue > 0) {
                currentValue--
                updateTextView()
            }
        }

        // Navbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val tempat: DrawerLayout = findViewById(R.id.tempat)

        val navController = Navigation.findNavController(this, R.id.kalkulator)
        val navView: com.google.android.material.navigation.NavigationView = findViewById(R.id.nav_view)
        NavigationUI.setupWithNavController(navView, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, tempat)



        val toggle = ActionBarDrawerToggle(this, tempat, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        tempat.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun updateTextView() {
        number.text = currentValue.toString()
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))
    }

    // Navbar
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val tempat: DrawerLayout = findViewById(R.id.tempat)
        if (tempat.isDrawerOpen(GravityCompat.START)) {
            tempat.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}