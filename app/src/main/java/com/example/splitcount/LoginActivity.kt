package com.example.splitcount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var txtUsername: EditText
    lateinit var txtPassword: EditText
    lateinit var btnRegister: Button
    lateinit var btnLogin: Button
    lateinit var progressLoading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        txtUsername = findViewById(R.id.username)
        txtPassword = findViewById(R.id.password)

        btnLogin   = findViewById(R.id.login)
        btnRegister = findViewById(R.id.register)
        progressLoading  = findViewById(R.id.loading)
        btnRegister.setOnClickListener {
            val regIntent = Intent(this, RegisterActivity::class.java)
            startActivity(regIntent)
        }

        btnLogin.setOnClickListener{
            login(txtUsername.text.toString(), txtPassword.text.toString());
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if(auth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }



    private fun login(email:String, password:String){

        enableFields(false)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if(auth.currentUser != null){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(baseContext, "Si è verificato un errore.",
                            Toast.LENGTH_LONG).show()
                        enableFields(true)
                    }
                } else {

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_LONG).show()
                    enableFields(true)
                }


            }

    }

    private fun enableFields(enable:Boolean){
        btnLogin.isEnabled = enable
        txtUsername.isEnabled = enable
        txtPassword.isEnabled = enable
        btnRegister.isEnabled = enable

        if(enable){
            progressLoading.setVisibility(View.GONE)
        } else {
            progressLoading.setVisibility(View.VISIBLE)
        }

    }
}
