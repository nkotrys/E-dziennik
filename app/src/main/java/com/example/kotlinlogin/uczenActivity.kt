package com.example.kotlinlogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class  uczenActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uczen)
    }

    fun onClickOceny(v:View){

        val intent = Intent(this,OcenyActivity::class.java)
        startActivity(intent)
    }
    fun onClickPlan(v:View){

        val intent = Intent(this,PlanActivity::class.java)
        startActivity(intent)
    }

    fun onClickWiadomosci(v:View){

        val intent = Intent(this,WiadomosciUczen::class.java)
        startActivity(intent)
    }

}