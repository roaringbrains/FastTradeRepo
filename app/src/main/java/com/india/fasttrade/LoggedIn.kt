package com.india.fasttrade

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_logged_in.*

class LoggedIn : AppCompatActivity() {

    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)

        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLoggedIn = sharedPref.getString("Email","1")



        //val spEmail = sharedPref.getString("Email","1")

        //var email = intent.getStringExtra("Email")

        Logout.setOnClickListener{
            sharedPref.edit().remove("Email").apply()
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        if(isLoggedIn == "1") {

            var email = intent.getStringExtra("Email")

            if(email != null){

                setText(email)
                with(sharedPref.edit())
                {
                    putString("Email",email)
                    apply()
                }
            }else{
                var intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }

//            with(sharedPref.edit())
//            {
//                putString("email", email)
//                apply()
//            }
        }else{

            setText(isLoggedIn)
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
        }

    }

    private fun setText(email:String?)
    {
        db = FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener { tasks ->
                    Name.text = tasks.get("Name").toString()
                    Phone.text = tasks.get("Phone").toString()
                    EmailLog.text = tasks.get("Email").toString()
                }
        }
    }



}