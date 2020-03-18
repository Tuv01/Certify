package com.tuv01.certification

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Login : AppCompatActivity() {

    lateinit var btnLogin:Button
    lateinit var inputUsername:EditText
    lateinit var inputPassword:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        btnLogin =findViewById(R.id.btnLogin)
        inputPassword = findViewById(R.id.editTextPassword)
        inputUsername = findViewById(R.id.editTextUsername)

        btnLogin.setOnClickListener() {
            val username: String = inputUsername.getText().toString()
            val password: String = inputPassword.getText().toString()
            if (TextUtils.isEmpty(username) || !username.equals("d23x51")) {
                Toast.makeText(this@Login, "Enter the correct username!", Toast.LENGTH_SHORT).show();
                return@setOnClickListener;
            }

            if (TextUtils.isEmpty(password)|| !password.equals("contract")) {
                Toast.makeText(this@Login, "Enter the correct password!", Toast.LENGTH_SHORT).show();
                return@setOnClickListener;
            }
            else{
                val intent = Intent (this,MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
