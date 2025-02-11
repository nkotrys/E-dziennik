package com.example.kotlinlogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject

//val url = "http://10.0.2.2/androiddb/"


class ZadaniaActivity : AppCompatActivity(),
    ZadaniaAdapter.UsunZadanieListener {

    private val url : String by lazy { getString(R.string.db_url) }
    private val zadania = mutableListOf<Zadanie>()
    private lateinit var adapter : ZadaniaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zadania)

        // Tworzymy nasz ZadaniaAdapter i wiążemy go z listView
        adapter = ZadaniaAdapter(this, zadania, this)
        var listView = findViewById<ListView>(R.id.listaOcen)
        listView.adapter = adapter

        // Ustawiamy akcję obsługi kliknięcia w dany wiersz
        // naszego listView
        listView.setOnItemClickListener { parent, view, position, id ->
            // Zmieniamy stan zadania: zrobione <-> niezrobione
            // w bazie danych
            zmienStanZadania(position)
        }

        // Wczytujemy zadania z bazy danych
        odswiezListeZadan()
    }

    fun dodajZadanie(view: View) {
        Log.d("dodajZadanie","ENTER")
        // Pobieramy opis zadania
        val textEdit = findViewById<EditText>(R.id.editTextTextPersonName)
        val opis = textEdit.text.toString()

        // Konstruujemy zapytanie do bazy danych
        val jsonObject = JSONObject()
        jsonObject.put("username", MainActivity.username)
        jsonObject.put("password", MainActivity.password)
        jsonObject.put("query",
            "INSERT INTO `zadania` (opis, zrobione) VALUES ('$opis', 0)")

        val requestPOST =
            JsonObjectRequest(Request.Method.POST, url, jsonObject,
                Response.Listener { response ->
                    try {
                        // Obsługa odpowiedzi z bazy danych
                        Log.d("dodajZadanie","Response: $response")
                        textEdit.setText("")
                        // Odświeżamy całą listę zadań
                        odswiezListeZadan()
                    } catch (e:Exception){
                        Log.d("dodajZadanie","Exception: $e")
                    }
                }, Response.ErrorListener{
                    // Error in request
                    Log.d("dodajZadanie","Volley error: $it")
                })
        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
    }

    fun zmienStanZadania(position: Int) {
        Log.d("zmienStanZadania","ENTER")
        // Pobieramy kliknięte zadanie z listy
        val zadanie = adapter.getItem(position) as Zadanie

        // Konstruujemy zapytanie do bazy danych
        val jsonObject = JSONObject()
        jsonObject.put("username", MainActivity.username)
        jsonObject.put("password", MainActivity.password)
        jsonObject.put("query",
            "UPDATE `zadania` SET zrobione=${if (zadanie.zrobione) 0 else 1} WHERE id=${zadanie.id}")

        val requestPOST =
            JsonObjectRequest(Request.Method.POST, url, jsonObject,
                Response.Listener { response ->
                    try {
                        // Obsługa odpowiedzi z bazy danych
                        Log.d("zmienStanZadania","Response: $response")
                        // Odświeżamy całą listę zadań
                        odswiezListeZadan()
                    } catch (e:Exception){
                        Log.d("zmienStanZadania","Exception: $e")
                    }

                }, Response.ErrorListener{
                    // Error in request
                    Log.d("zmienStanZadania","Volley error: $it")
                })
        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
    }

    fun odswiezListeZadan() {
        Log.d("odswiezListeZadan","ENTER")
        // Konstruujemy zapytanie do bazy danych
        val jsonObject = JSONObject()
        jsonObject.put("username", MainActivity.username)
        jsonObject.put("password", MainActivity.password)
        jsonObject.put("query","SELECT * from `zadania`")

        val requestPOST =
            JsonObjectRequest(Request.Method.POST,url,jsonObject,
            Response.Listener { response ->
                try {
                    // Obsługa odpowiedzi z bazy danych
                    Log.d("odswiezListeZadan","Response: $response")
                    // Usuwamy wszystkie zadania z naszej listy lokalnej
                    zadania.clear()
                    // Pobieramy listę zadań zwróconą z bazy danych
                    val jsonZadania : JSONArray = response.getJSONArray("message")
                    // Przechodzimy po otrzymanej liście
                    for (i in 0 until jsonZadania.length()) {
                        // Dla każdego elementu tworzymy obiekt
                        // klasy Zadanie i wstawiamy go do listy lokalnej
                        val id = jsonZadania.getJSONObject(i).getInt("id")
                        val opis = jsonZadania.getJSONObject(i).getString("opis")
                        val zrobione = jsonZadania.getJSONObject(i).getInt("zrobione")
                        zadania.add(Zadanie(id, opis, zrobione != 0))
                    }
                    // Informujemy adapter o konieczności
                    // odświeżenia widoku
                    adapter.notifyDataSetChanged()
                } catch (e:Exception){
                    Log.d("odswiezListeZadan","Exception: $e")
                }

            }, Response.ErrorListener{
                // Error in request
                Log.d("odswiezListeZadan","Volley error: $it")
            })
        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
    }

    override fun usunZadanie(position: Int) {
        Log.d("usunZadanie","ENTER")
        // Pobieramy kliknięte zadanie z listy
        val zadanie = adapter.getItem(position) as Zadanie

        // Konstruujemy zapytanie do bazy danych
        val jsonObject = JSONObject()
        jsonObject.put("username", MainActivity.username)
        jsonObject.put("password", MainActivity.password)
        jsonObject.put("query",
            "DELETE from `zadania` WHERE id=${zadanie.id}")

        val requestPOST =
            JsonObjectRequest(Request.Method.POST,url,jsonObject,
                Response.Listener { response ->
                    try {
                        // Obsługa odpowiedzi z bazy danych
                        Log.d("usunZadanie","Response: $response")
                        // Odświeżamy całą listę zadań
                        odswiezListeZadan()
                    } catch (e:Exception){
                        Log.d("usunZadanie","Exception: $e")
                    }
                }, Response.ErrorListener{
                    // Error in request
                    Log.d("usunZadanie","Volley error: $it")
                })
        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
    }
}