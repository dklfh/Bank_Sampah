package com.example.banksampah

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class nota : AppCompatActivity() {

    private val REQUEST_CODE_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota)

        val cancelButton: Button = findViewById(R.id.cancelbtn)
        cancelButton.setOnClickListener {
            val intent = Intent(this, cobanavbar::class.java)
            startActivity(intent)
        }
        val submitButton: Button = findViewById(R.id.submitbtn)
        submitButton.setOnClickListener{
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                convertXMLtoPDF()
            } else {
                requestPermission()
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun convertXMLtoPDF() {
        val rootLayout = this.findViewById<ViewGroup>(R.id.struk)
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)

        val layout = LayoutInflater.from(this).inflate(R.layout.activity_nota, rootLayout, false)
        val view = rootLayout.findViewById<View>(R.id.struk)

        view.measure(
            View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY)
        )

        Log.d("PDF Generation", "View dimensions: ${view.measuredWidth} x ${view.measuredHeight}")

        val document = PdfDocument()

        val viewWidth = view.measuredWidth
        val viewHeight = view.measuredHeight

        val linearLayout = rootLayout.findViewById<LinearLayout>(R.id.struk)
        linearLayout.gravity = Gravity.CENTER

        Log.d("PDF Generation", "Isi view setelah menggambar: $view")

        val pageInfo = PdfDocument.PageInfo.Builder(viewWidth, viewHeight, 1).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val xOffset = (pageInfo.pageWidth - viewWidth) / 2f
        val yOffset = (pageInfo.pageHeight - viewHeight) / 2f
        canvas.save()
        canvas.translate(xOffset, yOffset)
        val bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888)
        view.draw(Canvas(bitmap))
        val matrix = Matrix()
        canvas.drawBitmap(bitmap, matrix, Paint())
        canvas.restore()
        layout.draw(canvas)
        document.finishPage(page)
        val downloadsDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "contoh_XML_${System.currentTimeMillis()}.pdf"
        val file = File(downloadsDir, fileName)
        try {
            val fos = FileOutputStream(file)
            document.writeTo(fos)
            document.close()
            fos.close()
            Log.d("PDF Generation", "PDF berhasil dibuat dan disimpan di: ${file.absolutePath}")
            Toast.makeText(this, "PDF sudah dibuat!", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            Log.d("mylog", "Error while writing $e")
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        Log.d("PDF Generation", "Isi view setelah menyimpan PDF: $view")
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AlertDialog.Builder(this)
                .setTitle("Izin Diperlukan")
                .setMessage("Aplikasi ini membutuhkan izin untuk mengakses file di penyimpanan eksternal. Silakan berikan izin.")
                .setPositiveButton("OK") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_CODE_PERMISSION
                    )
                }
                .setNegativeButton("Batal", null)
                .create()
                .show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                convertXMLtoPDF()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}