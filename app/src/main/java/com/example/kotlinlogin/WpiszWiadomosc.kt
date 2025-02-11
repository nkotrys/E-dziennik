package com.example.kotlinlogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class WpiszWiadomosc : AppCompatActivity() {

    var IdWybranegoUcznia = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dodaj_wiadomosx)
        val spinner: Spinner = findViewById(R.id.spinner3)
// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            this,
            R.array.typy_aktualnosci,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            spinner.adapter = adapter
        }
    }


    fun onClickWstaw(v: View){
        val klasa = findViewById<EditText>(R.id.editTextText12).text.toString()

        //val data = findViewById<EditText>(R.id.editTextText6).text.toString()
        val wiadomosc = findViewById<EditText>(R.id.editTextTextMultiLine).text.toString()

        val spinner = findViewById<Spinner>(R.id.spinner3)
        val typ = spinner.getSelectedItem().toString()

        //var idUcznia = znajdzUcznia(imie, nazwisko)
        //var przedmiot = znajdzPrzedmiot("A","A")


        var query2 ="INSERT INTO wiadomosci (klasa,typ_wiadomosci,data) VALUES (\"$klasa\",\"$typ\",\'$wiadomosc\')"
        //INSERT INTO dziennik_ocen (id_ucznia,ocena,przedmiot,waga) VALUES ((SELECT id_ucznia FROM uczniowie WHERE firstname="Bolek" AND lastname="cwel"),"1",(SELECT przedmiot FROM nauczyciele WHERE firstname="A" AND lastname="A"),"1");
        //val  query = "INSERT INTO dziennik_ocen (id_ucznia,ocena,przedmiot,waga) VALUES (\"$IdWybranegoUcznia\",\"$ocena\",\"$przedmiot\",\"$waga\")


        Log.d("query","$query2")
        //findViewById<EditText>(R.id.editTextTextMultiLine).setText(query2)

        val jsonObject = JSONObject()

        jsonObject.put("username",MainActivity.username)
        jsonObject.put("password",MainActivity.password)
        jsonObject.put("email","")
        jsonObject.put("roleid","")
        jsonObject.put("query",query2)

        val url = getString(R.string.db_url)


        // Volley post request with parameters
        val requestPOST = JsonObjectRequest(Request.Method.POST,url,jsonObject,
            Response.Listener { response ->
                // Process the json
                try {
                    Toast.makeText(this, "dodano wiadomość", Toast.LENGTH_LONG).show()
                    Log.d("fun onClickWstaw:","Response: $response")

                }catch (e:Exception){
                    Toast.makeText(this, "incorrect details", Toast.LENGTH_LONG).show()
                    Log.d("fun onClickWstaw:","Exception: $e")
                    findViewById<EditText>(R.id.editTextText12).setText("  klasa")
                    findViewById<EditText>(R.id.editTextTextMultiLine).setText("  wiadomość")
                }

            }, Response.ErrorListener{
                // Error in request
                Toast.makeText(this, "incorrect details", Toast.LENGTH_LONG).show()
                Log.d("fun onClickWstaw:","Volley error: $it")

            })
        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
    }
}