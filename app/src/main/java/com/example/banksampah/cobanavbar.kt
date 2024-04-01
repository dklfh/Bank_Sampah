package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import androidx.appcompat.app.ActionBarDrawerToggle

class cobanavbar : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var tempat: DrawerLayout
    private lateinit var toolbarTitle: TextView
    private var overlayAlertDialog: AlertDialog? = null

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

        if (savedInstanceState == null) {
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
            R.id.nav_HisTransaksi -> {
                replaceFragment(HistoriTransaksi())
                toolbarTitle.text = "Histori Transaksi"
            }
            R.id.nav_hapus -> {
                showDeleteDataDialog()
            }
            R.id.nav_aboutus -> {
                replaceFragment(About_us())
                toolbarTitle.text = "App Maker"
            }
        }
        tempat.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun showDeleteDataDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this, R.style.AppTheme_Dialog)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.perdatasampah, null)
        val tanggalDataTextView = dialogView.findViewById<TextView>(R.id.tanggalData)
        val currentDate = SimpleDateFormat("dd MMMM yyyy", Locale("id")).format(Date())
        val spinner: Spinner = dialogView.findViewById(R.id.periode)
        val periodeArray = arrayOf("1 Menit", "1 Jam", "1 Hari") // Mengubah opsi periode
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, periodeArray)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancel)
        val simpanButton = dialogView.findViewById<Button>(R.id.simpan)

        alertDialogBuilder.setView(dialogView)

        tanggalDataTextView.text = currentDate

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        cancelButton.setOnClickListener {
            overlayAlertDialog?.dismiss()
        }

        simpanButton.setOnClickListener {
            val selectedPeriode = spinner.selectedItem.toString()

            // Mendapatkan durasi berdasarkan pilihan pengguna
            val duration = when (selectedPeriode) {
                "1 Menit" -> 60L // 1 menit = 60 detik
                "1 Jam" -> 3600L // 1 jam = 3600 detik
                "1 Hari" -> 86400L // 1 hari = 86400 detik
                else -> throw IllegalArgumentException("Invalid duration")
            }

            // Jadwalkan penghapusan data setelah durasi tertentu
            Handler().postDelayed({
                // Panggil method untuk menghapus data histori transaksi
                deleteTransactionHistory()
                // Tampilkan pesan bahwa data sudah berhasil dihapus
                Toast.makeText(this, "Data histori transaksi sudah berhasil dihapus", Toast.LENGTH_SHORT).show()
            }, duration * 1000) // konversi durasi dari detik ke milidetik

            Toast.makeText(this, "Penghapusan data dijadwalkan setelah $selectedPeriode", Toast.LENGTH_SHORT).show()
            overlayAlertDialog?.dismiss()
        }

        overlayAlertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        overlayAlertDialog = alertDialogBuilder.create()
        overlayAlertDialog?.show()
    }


    private fun deleteTransactionHistory() {
        // Dapatkan referensi ke Firebase database
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        uid?.let { userId ->
            val dbref = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Data transaksi")

            // Hapus data dari database
            dbref.removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Data histori transaksi berhasil dihapus", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Gagal menghapus data histori transaksi: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onBackPressed() {
        if (tempat.isDrawerOpen(GravityCompat.START)) {
            tempat.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
