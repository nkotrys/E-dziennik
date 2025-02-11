package com.example.kotlinlogin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class UsprawiedliwieniaActivity: AppCompatActivity() {

    var lista = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usprawiedliwienia)
    }

    fun onClickOdswiez(v: View){
        val queue = Volley.newRequestQueue(this)
        val data = findViewById<EditText>(R.id.editTextText3).text.toString()+'%'
        //val url = "http://10.0.2.2/androiddb/"
        val url = getString(R.string.db_url)

        Log.d("fun onClickLogin:", "url: $url")

        // Post parameters
        val zapytanie = JSONObject()
        zapytanie.put("username", "${MainActivity.username}")
        zapytanie.put("password", "${MainActivity.password}")
        zapytanie.put("roleid","")
        zapytanie.put("email", "")
        zapytanie.put(
            "query",
            "SELECT * FROM nieobecnosci WHERE id_ucznia = (SELECT id_ucznia FROM uczen_rodzic WHERE id_rodzica = (select id from users where username = \"${MainActivity.username}\")) and usprawiedliwione = 0 and created_at like \"$data\";"        )

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


        queue.add(requestPOST)


    }

    fun onClickUsprawiedliw(v : View){

        val url = getString(R.string.db_url)
         val data = findViewById<EditText>(R.id.editTextText3).text.toString()+'%'

        val queue = Volley.newRequestQueue(this)
        Log.d("fun onClickLogin:","url: $url")

        // Post parameters
        val zapytanie = JSONObject()
        zapytanie.put("username","${MainActivity.username}")
        zapytanie.put("password","${MainActivity.password}")
        zapytanie.put("roleid","")
        zapytanie.put("email","")
        zapytanie.put("query","UPDATE nieobecnosci SET usprawiedliwione = 1 WHERE data like \'$data\' AND id_ucznia = (SELECT id_ucznia FROM uczen_rodzic WHERE id_rodzica = (select id from users where username = \"${MainActivity.username}\"))")

        //UPDATE nieobecnosci SET czy_usprawiedliwione = 1 WHERE data= '2023-01-01' AND id_ucznia = (SELECT uczen_id FROM rodzice WHERE username = "akowal")
        //Log.d("query: ","UPDATE nieobecnosci SET czy_usprawiedliwione = 1 WHERE data= \'$dzien\' AND id_ucznia = (SELECT uczen_id FROM rodzice WHERE username = \"${MainActivity.username}\")")
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
        var ListView = findViewById<ListView>(R.id.nieusprawiedliwione)
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,lista)
        ListView.adapter = adapter

        //val textEdit = findViewById<TextView>(R.id.textView)
        //val textEdit = findViewById<EditText>(R.id.editTextTextMultiLine)
        //textEdit.setText(response.getJSONArray("message").toString())
        //textEdit.setText("jestem tu")

        lista.clear()
        val jsonLista : JSONArray = response.getJSONArray("message")

        for(i in 0 until jsonLista.length()){

            lista.add(jsonLista.getJSONObject(i).get("przedmiot").toString()+", "+jsonLista.getJSONObject(i).get("data").toString())
            //lista.add(jsonLista.get(i).toString())


        }

        adapter.notifyDataSetChanged()




    }

}