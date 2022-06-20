package com.example.uasdpm2011500049

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var adpDosen: AdapterDosen
    private lateinit var dataDosen: ArrayList<DataDosen>
    private lateinit var lvDosen: ListView
    private lateinit var linTidakAda: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvDosen = findViewById(R.id.lvDataDosen)
        linTidakAda = findViewById(R.id.linTidakAda)

        dataDosen = ArrayList()
        adpDosen = AdapterDosen(this@MainActivity, dataDosen)

        lvDosen.adapter = adpDosen

        refresh()

        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, EntriData::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus)refresh()
    }

    private fun refresh() {
        val db = DbHelper(this@MainActivity)
        val data = db.tampil()
        repeat(dataDosen.size) { dataDosen.removeFirst() }
        if (data.count > 0) {
            while (data.moveToNext()) {
                val dataDosen = DataDosen(
                    data.getInt(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getInt(4),
                    data.getString(5),
                    data.getString(6),
                )
                adpDosen.add(dataDosen)
                adpDosen.notifyDataSetChanged()
            }
            lvDosen.visibility = View.VISIBLE
            linTidakAda.visibility = View.GONE
        } else
            lvDosen.visibility = View.GONE
        linTidakAda.visibility = View.VISIBLE
    }
}