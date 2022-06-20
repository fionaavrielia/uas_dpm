package com.example.uasdpm2011500049

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class EntriData: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entri_data)

        val modeEdit = intent.hasExtra("NIDN") && intent.hasExtra("nama dosen") &&
                intent.hasExtra("jabatan") && intent.hasExtra("Golongan Pangkat")
                && intent.hasExtra("Pendidikan Terakhir") && intent.hasExtra("Bidang Keahlian")
                && intent.hasExtra("Program studi")
        title = if (modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etKdNidn = findViewById<EditText>(R.id.etKdNidn)
        val etNmDosen = findViewById<EditText>(R.id.etNmDosen)
        val spnJabatan = findViewById<Spinner>(R.id.spnJabatan)
        val spnGolPangkat = findViewById<Spinner>(R.id.spnGolPangkat)
        val rdS2 = findViewById<RadioButton>(R.id.rdS2)
        val rdS3 = findViewById<RadioButton>(R.id.rdS3)
        val etKdKeahlian = findViewById<EditText>(R.id.etKdKeahlian)
        val etKdProgStud = findViewById<EditText>(R.id.etKdProgStud)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val Jabatan = arrayOf("Tenaga Pengajar","Asisten Ahli","Lektor","Lektor Kepala","Guru Besar")
        val adpJabatan = ArrayAdapter(
            this@EntriData,
            android.R.layout.simple_spinner_dropdown_item,
            Jabatan
        )
        spnJabatan.adapter = adpJabatan

        val golPangkat = arrayOf("III/a - Penata Muda","III/b - Penata Muda Tingkat I","III/c - Penata",
            "III/d - Penata Tingkat I","IV/a - Pembina","IV/b - Pembina Tingkat I","IV/c - Pembina Utama Muda",
            "IV/d - Pembina Utama Madya","IV/e - Pembina Utama")
        val adpGolPangkat = ArrayAdapter(
            this@EntriData,
            android.R.layout.simple_spinner_dropdown_item,
            Jabatan
        )
        spnGolPangkat.adapter = adpGolPangkat

        if (modeEdit) {
            val kdNidn = intent.getStringExtra("NIDN")
            val nmDosen = intent.getStringExtra("nama dosen")
            val jabatan = intent.getStringExtra("Jabatan")
            val golpangkat = intent.getStringExtra("Golongan Pangkat")
            val pendidikan = intent.getStringExtra("Pendidikan Terakhir")
            val keahlian = intent.getStringExtra("Bidang Keahlian")
            val progstud = intent.getStringExtra("Program Studi")

            etKdNidn.setText(kdNidn)
            etNmDosen.setText(nmDosen)
            spnJabatan.setSelection(Jabatan.indexOf(jabatan))
            spnGolPangkat.setSelection(golPangkat.indexOf(golpangkat))
            if (pendidikan == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etKdKeahlian.setText(keahlian)
            etKdProgStud.setText(progstud)
        }
        etKdNidn.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if ("${etKdNidn.text}".isNotEmpty() && "${etNmDosen.text}".isNotEmpty() &&
                (rdS2.isChecked || rdS3.isChecked)
            ) {
                val db = DbHelper(this@EntriData)
                db.kdNidn = "${etKdNidn.text}"
                db.nmDosen = "${etNmDosen.text}"
                db.Jabatan = spnJabatan.selectedItem as Int
                db.GolPangkat = spnGolPangkat.selectedItem as Int
                db.PendidikanTerakhir = if (rdS2.isChecked) "S2" else "S3"
                db.Keahlian = "${etKdKeahlian.text}"
                db.ProgStud = "${etKdProgStud.text}"
                if (if (!modeEdit) db.simpan() else db.ubah("${etKdNidn.text}")) {

                    Toast.makeText(
                        this@EntriData,
                        "Data Dosen Berhasil Disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else
                    Toast.makeText(
                        this@EntriData,
                        "Data Dosen Gagal Disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            } else
                Toast.makeText(
                    this@EntriData,
                    "Data Dosen Belum Lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}