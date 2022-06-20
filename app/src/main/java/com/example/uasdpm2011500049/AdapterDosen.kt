package com.example.uasdpm2011500049

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.view.*
import android.widget.*

class AdapterDosen (
    private val getContext: Context,
    private val customListItem:ArrayList<DataDosen>
        ): ArrayAdapter<DataDosen>(getContext,0, customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val infLateList = (getContext as Activity).layoutInflater
            listLayout = infLateList.inflate(R.layout.layout_item, parent, false)
            holder = ViewHolder()
            with(holder) {
                tvNmDosen = listLayout.findViewById(R.id.tvNmDosen)
                tvKdNidn = listLayout.findViewById(R.id.tvKdNidn)
                tvKdProgStud = listLayout.findViewById(R.id.tvKdProgStud)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        } else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmDosen!!.setText(listItem.nmDosen)
        holder.tvKdNidn!!.setText(listItem.kdNidn)

        holder.btnEdit!!.setOnClickListener {
            val i = Intent(context, EntriData::class.java)
            i.putExtra("NIDN", listItem.kdNidn)
            i.putExtra("nama", listItem.nmDosen)
            i.putExtra("Jabatan", listItem.Jabatan)
            i.putExtra("Golongan Pangkat", listItem.golPangkat)
            i.putExtra("Pendidikan Terakhir", listItem.PendidikanTerakhir)
            i.putExtra("Bidang Keahlian", listItem.Keahlian)
            i.putExtra("Program Studi", listItem.ProgStud)
            context.startActivity(i)
        }
        holder.btnHapus!!.setOnClickListener {
            val db = DbHelper(context)
            val alb = AlertDialog.Builder(context)
            val kode = holder.tvKdNidn!!.text
            val nama = holder.tvNmDosen!!.text
            with(alb) {
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage(
                    """
                        Apakah Anda Yakin Akan Menghapus Data Ini?
                       $nama[kode]
                        """.trimIndent()
                )
                setPositiveButton("Ya") { _, _ ->
                    if (db.hapus("$kode"))
                        Toast.makeText(
                            context,
                            "Data Dosen Berhasil Dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data Dosen Gagal Dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("tidak", null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var
                tvNmDosen: TextView? = null
        internal var
                tvKdNidn: TextView? = null
        internal  var
                tvKdProgStud: TextView? = null
        internal var
                btnEdit: ImageButton? = null
        internal var
                btnHapus: ImageButton? = null
    }

}