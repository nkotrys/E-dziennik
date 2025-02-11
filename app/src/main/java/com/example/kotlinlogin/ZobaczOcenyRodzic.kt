package com.example.kotlinlogin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class ZobaczOcenyRodzic: AppCompatActivity() {

    var lista = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ocenypokaz)
        val spinner: Spinner = findViewById(R.id.spinner)
// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            this,
            R.array.przedmioty,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            spinner.adapter = adapter
        }


    }

    fun onClickOceny(v: View) {
        //val  user = findViewById<EditText>(R.id.loginUserEditText).text.toString()
        //val  password = findViewById<EditText>(R.id.loginPasswordEditText).text.toString()

        val spinner = findViewById<Spinner>(R.id.spinner)
        val przedmiot = spinner.getSelectedItem().toString()

        val queue = Volley.newRequestQueue(this)

        //val url = "http://10.0.2.2/androiddb/"
        val url = getString(R.string.db_url)

        Log.d("fun onClickLogin:","url: $url")

        // Post parameters
        val zapytanie = JSONObject()
        zapytanie.put("username","${MainActivity.username}")
        zapytanie.put("password","${MainActivity.password}")
        zapytanie.put("email","")
        zapytanie.put("roleid","")
        zapytanie.put("query","SELECT * FROM dziennik_ocen WHERE przedmiot = '$przedmiot' AND id_ucznia = (SELECT id_ucznia FROM uczen_rodzic WHERE id_rodzica = (select id from users where username = \"${MainActivity.username}\"));")

        // Volley post request with parameters
        val requestPOST = JsonObjectRequest(
            Request.Method.POST,url,zapytanie,
            Response.Listener { response ->
                // Process the json
                try {
                    processResponse(response)
                    Log.d("fun onClickLogin:","Response: $response")
                }catch (e:Exception){
                    Log.d("fun onClickLogin:","Exception: $e")
                }

            }, Response.ErrorListener{
                // Error in request
                Log.d("fun onClickLogin:","Volley error: $it")
            })


        // zapamietajmy uzytegko usera i haslo - na wypadek gdyby udalo sie zalogowac
        //MainActivity.username = user
        //MainActivity.password = password

        //VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
        queue.add(requestPOST)
    }

    fun processResponse(response: JSONObject) {
        var ListView = findViewById<ListView>(R.id.listaOcen)
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,lista)
        ListView.adapter = adapter

        //val textEdit = findViewById<TextView>(R.id.textView)
        //val textEdit = findViewById<EditText>(R.id.editTextTextMultiLine)
        //textEdit.setText(response.getJSONArray("message").toString())
        //textEdit.setText("jestem tu")

        lista.clear()
        val jsonLista : JSONArray = response.getJSONArray("message")

        for(i in 0 until jsonLista.length()){

            lista.add("ocena= "+jsonLista.getJSONObject(i).get("ocena").toString()+", waga= "+jsonLista.getJSONObject(i).get("waga").toString())
            //lista.add(jsonLista.get(i).toString())


        }

        adapter.notifyDataSetChanged()




    }
}