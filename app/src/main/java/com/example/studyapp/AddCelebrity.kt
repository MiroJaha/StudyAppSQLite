package com.example.studyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import io.github.muddz.styleabletoast.StyleableToast

class AddCelebrity : AppCompatActivity() {

    private val dbHelper by lazy { DBHelper(applicationContext,tableName = tableName) }

    private lateinit var titleEntry: EditText
    private lateinit var materialEntry: EditText
    private lateinit var detailsEntry: EditText
    private lateinit var saveButton: Button
    private lateinit var backButton: Button
    private lateinit var tableName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_celebrity)

        title="Add New Material"

        titleEntry= findViewById(R.id.titleEntry)
        materialEntry= findViewById(R.id.materialEntry)
        detailsEntry= findViewById(R.id.detailsEntry)
        saveButton= findViewById(R.id.saveButton)
        backButton= findViewById(R.id.backButton)

        tableName= intent.extras?.getString("tableName")!!

        backButton.setOnClickListener{
            finish()
        }

        saveButton.setOnClickListener{
            if (checkEntry()){
                val check= dbHelper.saveData(Data(
                    0,
                    titleEntry.text.toString(),
                    materialEntry.text.toString(),
                    detailsEntry.text.toString()
                ))
                val wrongCode: Long= -1
                if (check != wrongCode) {
                    StyleableToast.makeText(this,"Material Saved Successfully!!",R.style.mytoast).show()
                    clearEntry()
                }
                else
                    StyleableToast.makeText(this,"Something Went Wrong!!",R.style.mytoast).show()
            }
            else
                StyleableToast.makeText(this,"Please Enter Valid Values!!",R.style.mytoast).show()
        }

    }

    private fun clearEntry() {
        titleEntry.text.clear()
        materialEntry.text.clear()
        detailsEntry.text.clear()
    }

    private fun checkEntry(): Boolean {
        if (titleEntry.text.isBlank())
            return false
        if (materialEntry.text.isBlank())
            return false
        if (detailsEntry.text.isBlank())
            return false
        return true
    }
}