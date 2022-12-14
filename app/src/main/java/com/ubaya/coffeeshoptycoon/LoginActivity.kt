package com.ubaya.coffeeshoptycoon

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var sharedFile = "com.ubaya.utsnmp"
        var shared: SharedPreferences = getSharedPreferences(sharedFile,
            Context.MODE_PRIVATE )
        var editor:SharedPreferences.Editor = shared.edit()
        setContentView(R.layout.activity_login)
        editor.putString("USERNAME", "roronini")
        editor.putString("PASSWORD", "roni123")
        editor.putString("PLAYERNAME","Rony")
        editor.apply()

        var loginUsername = shared.getString("USERNAME","")
        var loginPassword = shared.getString("PASSWORD","")

        btnLogin.setOnClickListener {
            var inputUsername = txtUsername.text.toString()
            var inputPassword = txtPassword.text.toString()
            if ( inputUsername == loginUsername &&  inputPassword == loginPassword){
                val intent = Intent(this, PreparationActivity::class.java)
                if(!shared.contains("DAY")  || shared.getInt("DAY", 0) == 1){
                    editor.putInt("BALANCE", 350000)
                    editor.putInt("DAY", 1)
                    editor.apply()
                }
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "wrong username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}