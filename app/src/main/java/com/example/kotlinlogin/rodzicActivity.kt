package com.example.kotlinlogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class rodzicActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rodzic)
    }

    fun onClickOceny(v: View){

        val intent = Intent(this,ZobaczOcenyRodzic::class.java)
        startActivity(intent)
    }
    fun onClickPlan(v: View){

        val intent = Intent(this,ZobaczPlanRodzic::class.java)
        startActivity(intent)
    }

    fun onClickUsprawiedliwienia(v: View){

        val intent = Intent(this,UsprawiedliwieniaActivity::class.java)
        startActivity(intent)
    }

    fun onClickAktualnosci(v: View){

        val intent = Intent(this,WiadomosciRodzic::class.java)
        startActivity(intent)
    }

}