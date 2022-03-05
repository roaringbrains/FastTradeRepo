package com.india.fasttrade

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()

        ContinueRegister.setOnClickListener {

            var validCredentials = checking()
            if (validCredentials) {

                var email = EmailRegister.text.toString()
                var password = PasswordRegister.text.toString()
                var name = NameRegister.text.toString()
                var phone = PhoneRegister.text.toString()

                // Add a new document with a generated id.
                val user = hashMapOf(
                    "Name" to name,
                    "Phone" to phone,
                    "Email" to email
                )

                val Users = db.collection("USERS")
                val query = Users.whereEqualTo("Email", email).get()
                    .addOnSuccessListener { tasks ->
                        if (tasks.isEmpty) {

                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {

                                        Users.document(email).set(user)
                                        val intent = Intent(this, LoggedIn::class.java )
                                        intent.putExtra("Email",email)
                                        startActivity(intent)
                                        finish()

                                        // Sign in success, update UI with the signed-in user's information
//                                        Log.d(TAG, "createUserWithEmail:success")
//                                        Toast.makeText(
//                                            baseContext, "User Added.",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
                                        //val user = auth.currentUser
                                        //updateUI(user)
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                        Toast.makeText(
                                            baseContext, "Authentication failed.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        //updateUI(null)
                                    }
                                }


                        } else {

                            Toast.makeText(this, "User Already Registered", Toast.LENGTH_LONG)
                                .show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)

                        }

                    }


//                auth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success")
//                            Toast.makeText(baseContext, "User Added.",
//                                Toast.LENGTH_SHORT).show()
//                            //val user = auth.currentUser
//                            //updateUI(user)
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                            Toast.makeText(baseContext, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show()
//                            //updateUI(null)
//                        }
//                    }

            } else {
                Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
            }


        }

    }


    private fun checking(): Boolean {

        if (NameRegister.text.toString().trim { it <= ' ' }.isNotEmpty()
            && PhoneRegister.text.toString().trim { it <= ' ' }.isNotEmpty()
            && EmailRegister.text.toString().trim { it <= ' ' }.isNotEmpty()
            && PasswordRegister.text.toString().trim { it <= ' ' }.isNotEmpty()
        ) {
            return true
        }

        return false
    }


}