package com.example.kotlinlogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class nauczycielActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nauczyciel)
    }

    fun onClickWpiszOcene(v:View){
        val intent = Intent(this, wpisywanie_ocen::class.java)
        startActivity(intent)
    }

    fun onClickWpiszNieobecnosc(v:View){
        val intent = Intent(this, nieobecnosciActivity::class.java)
        startActivity(intent)
    }
    fun onClickWpiszWiadomosci(v:View){
        val intent = Intent(this, WpiszWiadomosc::class.java)
        startActivity(intent)
    }

}