package com.example.banksampah

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.banksampah.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class laporansubkategorii : Fragment() {

    private lateinit var tglAwalTextView: TextView
    private lateinit var tglAkhirTextView: TextView
    private lateinit var btnhapusfilter : Button
    private val calendar = Calendar.getInstance()
    private lateinit var simpanButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_laporansubkategorii, container, false)
        val fileName = "Nota-Sampah-lapsub.pdf"
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

        tglAwalTextView = rootView.findViewById(R.id.tgl_awal)
        tglAkhirTextView = rootView.findViewById(R.id.tgl_akhir)
        btnhapusfilter =  rootView.findViewById(R.id.hapus)
        simpanButton = rootView.findViewById(R.id.simpan_lapsub)

        simpanButton.setOnClickListener {
            convertXMLtoPDF(file)
        }

        tglAwalTextView.setOnClickListener {
            showDatePickerDialog(tglAwalTextView)
        }

        tglAkhirTextView.setOnClickListener {
            showDatePickerDialog(tglAkhirTextView)
        }

        btnhapusfilter.setOnClickListener{
            tglAwalTextView.text=null
            tglAkhirTextView.text=null
        }

        return rootView
    }

    @Suppress("DEPRECATION")
    private fun convertXMLtoPDF(file: File) {
        val rootLayout = requireView().findViewById<ViewGroup>(R.id.laporan)
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

        val layout = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_laporan_kategori, rootLayout, false)
        val view = rootLayout.findViewById<View>(R.id.laporan)


        view.measure(
            View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY)
        )

        Log.d("PDF Generation", "View dimensions: ${view.measuredWidth} x ${view.measuredHeight}")

        val document = PdfDocument()

        val viewWidth = view.measuredWidth
        val viewHeight = view.measuredHeight


        Log.d("PDF Generation", "Isi view setelah menggambar: $view")

        val marginLeft = 80
        val marginTop = 20
        val marginBottom = 20

        val pageInfo = PdfDocument.PageInfo.Builder(viewWidth + marginLeft , viewHeight + marginTop + marginBottom, 1).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val xOffset = (pageInfo.pageWidth - viewWidth) / 2f + marginLeft
        val yOffset = (pageInfo.pageHeight - viewHeight) / 2f + marginTop
        canvas.save()
        canvas.translate(xOffset, yOffset)
        val bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888)
        view.draw(Canvas(bitmap))
        val matrix = Matrix()
        canvas.drawBitmap(bitmap, matrix, Paint())
        canvas.restore()
        layout.draw(canvas)
        document.finishPage(page)
        try {
            val fos = FileOutputStream(file)
            document.writeTo(fos)
            document.close()
            fos.close()
            Log.d("PDF Generation", "PDF berhasil dibuat dan disimpan di: ${file.absolutePath}")
            Toast.makeText(requireContext(), "PDF sudah dibuat! disimpan di DOWNLOADS", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            Log.d("mylog", "Error while writing $e")
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        Log.d("PDF Generation", "Isi view setelah menyimpan PDF: $view")
    }

    private fun showDatePickerDialog(textView: TextView) {
        val datePicker = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Update the calendar with the selected date
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Update the TextView with the selected date
                updateLabel(textView)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Show the DatePickerDialog
        datePicker.show()
    }

    private fun updateLabel(textView: TextView) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        textView.text = dateFormat.format(calendar.time)
    }
}