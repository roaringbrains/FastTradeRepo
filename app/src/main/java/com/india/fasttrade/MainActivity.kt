package com.india.fasttrade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = Firebase.auth


        Register.setOnClickListener{
            var intent  = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        Login.setOnClickListener{

            if(checking()){
                val email = Email.text.toString()
                val password = Password.text.toString()
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){
                        task->
                        if (task.isSuccessful){

                            var intent  = Intent(this, LoggedIn::class.java)
                            startActivity(intent)

                            Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this,"Wrong Details",Toast.LENGTH_LONG).show()
                        }
                    }
                    //.addOnFailureListener() //not necessary now

            }else{
                Toast.makeText(this,"Enter the Details",Toast.LENGTH_LONG).show()
            }


        }

    }

    private fun checking():Boolean{

        if(Email.text.toString().trim{it<= ' '}.isNullOrEmpty()
            && Password.text.toString().trim{it<= ' '}.isNullOrEmpty())
        {
            return false
        }
        return true
    }


}