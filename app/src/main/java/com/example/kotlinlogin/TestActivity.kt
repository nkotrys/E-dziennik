package com.example.kotlinlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject

class TestActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    fun onClickSwitchView(v: View) {
        var intent = Intent(this, MainActivity::class.java)
        when(v.id){
            R.id.button3 -> intent = Intent(this, MainActivity::class.java)
            R.id.button4 -> intent = Intent(this, MainActivity2::class.java)
            R.id.button5 -> intent = Intent(this, MainActivity3::class.java)
            R.id.button8 -> intent = Intent(this, uczenActivity::class.java)
            //R.id.button8 -> intent = Intent(this, rodzicActivity::class.java)
            R.id.button10 -> intent = Intent(this, nauczycielActivity::class.java)
            R.id.button17 -> intent = Intent(this, OcenyActivity::class.java)
            R.id.button11 -> intent = Intent(this, rodzicActivity::class.java)
        }

        startActivity(intent)
    }
}