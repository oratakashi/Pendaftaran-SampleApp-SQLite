package com.oratakashi.pendaftaran.ui.register

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.oratakashi.pendaftaran.R
import com.oratakashi.pendaftaran.data.db.DatabaseBuilder
import com.google.android.material.snackbar.Snackbar
import com.oratakashi.pendaftaran.data.db.DatabaseBuilder.Callback
import com.oratakashi.pendaftaran.data.db.DatabaseCallback
import com.oratakashi.pendaftaran.data.model.Students
import com.oratakashi.pendaftaran.utils.Converter
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class RegisterActivity : AppCompatActivity(), Callback, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val builder : DatabaseBuilder by lazy {
        DatabaseBuilder(applicationContext)
    }

    private var ganre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.title = "Tambah Baru"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etTglLahir.setOnClickListener {
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                etTglLahir.setText("$dayOfMonth-"+ Converter.decimalFormat(month+1)+"-$year")
            },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show()
        }

        rgGanre.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rbMan      -> ganre = "Laki-Laki"
                R.id.rbWoman    -> ganre = "Perempuan"
            }
        }

        fab.setOnClickListener {
            if(
                etName.text!!.isEmpty() ||
                ganre.isEmpty() ||
                etTglLahir.text!!.isEmpty() ||
                etTmpLahir.text!!.isEmpty()
            ){
                Snackbar.make(it, "Harap isi semua field!", Snackbar.LENGTH_LONG)
                    .setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
            }else{
                launch {
                    builder.insert(
                        Students(
                            null,
                            etName.text.toString(),
                            ganre,
                            Converter.toSQLFormat(etTglLahir.text.toString()),
                            etTmpLahir.text.toString()
                        ), this@RegisterActivity
                    )
                }
            }
        }
    }

    override fun callback(callback: DatabaseCallback) {
        if(callback == DatabaseCallback.Insert){
            Toast.makeText(applicationContext, "Berhasil menambahkan data!",
                Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}