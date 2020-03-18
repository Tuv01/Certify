package com.tuv01.certification

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SelectActivity : AppCompatActivity() {

    lateinit var btnStudent: Button
    lateinit var btnCompany: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        btnStudent = findViewById(R.id.btnStudent)
        btnCompany = findViewById(R.id.btnCompany)

        btnStudent.setOnClickListener() {
            val intent = Intent (this,Login::class.java)
            startActivity(intent)
        }

        btnCompany.setOnClickListener() {
            val intent = Intent (this,ReadContract::class.java)
            startActivity(intent)
        }
    }
}
