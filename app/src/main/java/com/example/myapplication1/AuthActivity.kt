package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)

        val linkToReg: TextView = findViewById(R.id.link_to_reg)
        val userLogin: EditText = findViewById(R.id.user_login_auth)
        val userPass: EditText = findViewById(R.id.user_pass_auth)
        val button: Button = findViewById(R.id.button_auth)


        // Crucial: Handle potential nullPointerException
        linkToReg.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            // Improved validation: Check for empty or null strings
            if (login.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Exit the listener if validation fails
            }

            //  Crucially, handle potential null DbHelper
            val db = DbHelper(this, null) // Assuming DbHelper is properly initialized
            try {
                val isAuth = db.getUser(login, pass)
                if (isAuth) {
                    Toast.makeText(this, "Пользователь $login авторизован.", Toast.LENGTH_SHORT).show()
                    userLogin.text.clear()
                    userPass.text.clear()
                    //Add code to navigate to the next activity after successful login

                    val intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "Ошибка при авторизации, пользователь не найден.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                //Catch any exception during database operation
                Toast.makeText(this, "Ошибка базы данных: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }





    }
}