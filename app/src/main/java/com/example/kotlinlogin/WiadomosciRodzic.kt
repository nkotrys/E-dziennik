package com.example.kotlinlogin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class WiadomosciRodzic : AppCompatActivity() {

    var lista = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sprawdz_wiadomosci)
    }

    fun onClickOdswiez(v: View){
        val queue = Volley.newRequestQueue(this)

        //val url = "http://10.0.2.2/androiddb/"
        val url = getString(R.string.db_url)

        Log.d("fun onClickLogin:", "url: $url")

        // Post parameters
        val zapytanie = JSONObject()
        zapytanie.put("username", "${MainActivity.username}")
        zapytanie.put("password", "${MainActivity.password}")
        zapytanie.put("roleid", "")
        zapytanie.put("email", "")
        zapytanie.put(
            "query",
            "SELECT * FROM wiadomosci WHERE klasa = (SELECT klasa FROM uczen_klasa WHERE id_ucznia = (SELECT id_ucznia FROM uczen_rodzic WHERE id_rodzica = (select id from users where username = \"${MainActivity.username}\")));"
        )

        // Volley post request with parameters
        val requestPOST = JsonObjectRequest(
            Request.Method.POST, url, zapytanie,
            Response.Listener { response ->
                // Process the json
                try {
                    processResponse(response)
                    Log.d("fun onClickLogin:", "Response: $response")
                } catch (e: Exception) {
                    Log.d("fun onClickLogin:", "Exception: $e")
                }

            }, Response.ErrorListener {
                // Error in request
                Log.d("fun onClickLogin:", "Volley error: $it")
            })


        // zapamietajmy uzytegko usera i haslo - na wypadek gdyby udalo sie zalogowac
        //MainActivity.username = user
        //MainActivity.password = password

        //VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
        queue.add(requestPOST)


    }
    fun processResponse(response: JSONObject) {
        var ListView = findViewById<ListView>(R.id.aktualnosci)
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,lista)
        ListView.adapter = adapter

        //val textEdit = findViewById<TextView>(R.id.textView)
        //val textEdit = findViewById<EditText>(R.id.editTextTextMultiLine)
        //textEdit.setText(response.getJSONArray("message").toString())
        //textEdit.setText("jestem tu")

        lista.clear()
        val jsonLista : JSONArray = response.getJSONArray("message")

        for(i in 0 until jsonLista.length()){

            lista.add(jsonLista.getJSONObject(i).get("typ_wiadomosci").toString()+": "+jsonLista.getJSONObject(i).get("data").toString())
            //lista.add(jsonLista.get(i).toString())


        }
    }
}