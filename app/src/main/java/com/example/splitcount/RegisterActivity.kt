package com.example.splitcount

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var confirmPassword: EditText
    lateinit var btnRegister: Button
    lateinit var txtError: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        confirmPassword = findViewById(R.id.repeatPassword)
        btnRegister = findViewById(R.id.register)
        txtError = findViewById(R.id.textError)

        btnRegister.setOnClickListener {
            if(validateForm(username.text.toString(), password.text.toString(), confirmPassword.text.toString())){
                createAccount(username.text.toString(), password.text.toString())
            }

        }
    }

    private fun validateForm(email:String, password:String, confirmPassword:String) :Boolean {
        if(password != confirmPassword){
            setError("Le password devono coincidere!")
            return false
        }

        if(password.length < 6){
                setError("La password deve essere lunga almeno 6 caratteri!")
            return false
            }

        if(email.trim() == ""){
            setError("Email non valida!")
            return false
        }

            txtError.setVisibility(View.INVISIBLE);
        return true

    }


    private fun setError(error: String) {
        txtError.text = error
        txtError.setVisibility(View.VISIBLE);
    }

    private fun createAccount(username:String, password:String){
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    setError(task.exception?.message.toString());
                }
            }
    }


}