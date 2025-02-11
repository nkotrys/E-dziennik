package com.example.kotlinlogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class wpisywanie_ocen : AppCompatActivity() {

    var IdWybranegoUcznia = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wpiszocene)
    }

    fun onClickWroc(v:View){
        val intent = Intent(this, nauczycielActivity::class.java)
        startActivity(intent)
        finish()
    }


    fun onClickWstaw(v:View){
        val imie = findViewById<EditText>(R.id.editTextText7).text.toString()
        val ocena = findViewById<EditText>(R.id.editTextText9).text.toString().toIntOrNull()
        val waga = findViewById<EditText>(R.id.editTextText10).text.toString().toIntOrNull()
        //var idUcznia = znajdzUcznia(imie, nazwisko)
        //var przedmiot = znajdzPrzedmiot("A","A")


        val query2 ="INSERT INTO dziennik_ocen (id_ucznia,ocena,przedmiot,waga) VALUES ((SELECT id FROM users WHERE username=\"$imie\"),$ocena,(SELECT przedmiot FROM nauczyciel_przedmiot WHERE id_nauczyciela = (select id from users where username = \"${MainActivity.username}\")),$waga)"
        //INSERT INTO dziennik_ocen (id_ucznia,ocena,przedmiot,waga) VALUES ((SELECT id_ucznia FROM uczniowie WHERE firstname="Bolek" AND lastname="cwel"),"1",(SELECT przedmiot FROM nauczyciele WHERE firstname="A" AND lastname="A"),"1");
        //val  query = "INSERT INTO dziennik_ocen (id_ucznia,ocena,przedmiot,waga) VALUES (\"$IdWybranegoUcznia\",\"$ocena\",\"$przedmiot\",\"$waga\")"
        Log.d("query","$query2")

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
                    Toast.makeText(this, "dodano ocenę", Toast.LENGTH_LONG).show()
                    Log.d("fun onClickWstaw:","Response: $response")
                    findViewById<EditText>(R.id.editTextText7).setText("  imię")
                    findViewById<EditText>(R.id.editTextText9).setText("  ocena")
                    findViewById<EditText>(R.id.editTextText10).setText("  waga")


                }catch (e:Exception){
                    Toast.makeText(this, "incorrect details", Toast.LENGTH_LONG).show()
                    Log.d("fun onClickWstaw:","Exception: $e")

                }

            }, Response.ErrorListener{
                // Error in request
                Toast.makeText(this, "incorrect details", Toast.LENGTH_LONG).show()
                Log.d("fun onClickWstaw:","Volley error: $it")

            })
        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
    }

    //////////////////////////////////////////TO SIĘ PRZYDA PÓŹNIEJ////////////////////////////////////////////////////////////////////////////////////
    /*
    fun znajdzUcznia(imie:String, nazwisko:String) :String{

        val query = "SELECT id_ucznia FROM uczniowie WHERE firstname=\"$imie\" AND lastname=\"$nazwisko\";"
        val jsonObject = JSONObject()

        jsonObject.put("username",MainActivity.username)
        jsonObject.put("password",MainActivity.password)
        jsonObject.put("email","")
        jsonObject.put("query",query)

        val url = getString(R.string.db_url)
        var odp="0"

        // Volley post request with parameters
        val requestPOST = JsonObjectRequest(Request.Method.POST,url,jsonObject,
            Response.Listener { response ->
                // Process the json
                try {

                    Log.d("fun znajdzUcznia:","Response: $response")
                    odp = znajdzIdWJSON(response)
                    IdWybranegoUcznia=odp
                    Log.d("fun znajdzUcznia:","odp w Post: $odp")
                }catch (e:Exception){
                    Log.d("fun znajdzUcznia:","Exception: $e")
                    odp="0"
                    IdWybranegoUcznia=odp
                }

            }, Response.ErrorListener{
                // Error in request
                Log.d("fun znajdzUcznia:","Volley error: $it")
                odp="0"
                IdWybranegoUcznia=odp
            })
        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)

        Log.d("fun znajdzUcznia:","odp po post: $odp")
        return IdWybranegoUcznia
    }

    fun znajdzPrzedmiot(imie:String, nazwisko:String) :String{

        val query = "SELECT przedmiot FROM nauczyciele WHERE firstname=\"A\" AND lastname=\"A\";"
        val jsonObject = JSONObject()

        jsonObject.put("username","A")
        jsonObject.put("password","A")
        jsonObject.put("email","")
        jsonObject.put("query",query)

        val url = getString(R.string.db_url)
        var odp="0"

        // Volley post request with parameters
        val requestPOST = JsonObjectRequest(Request.Method.POST,url,jsonObject,
            Response.Listener { response ->
                // Process the json
                try {

                    Log.d("fun znajdzPrzedmiot:","Response: $response")
                    odp = znajdzPrzedmiotWJSON(response)
                }catch (e:Exception){
                    Log.d("fun znajdzPrzedmiot:","Exception: $e")
                    odp="0"
                }

            }, Response.ErrorListener{
                // Error in request
                Log.d("fun znajdzPrzedmiot:","Volley error: $it")
                odp="0"
            })
        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
        return odp
    }


    fun znajdzPrzedmiotWJSON(response: JSONObject): String {
        var odp = response.get("message").toString()
        //np: odp = [{"przedmiot":'fizyka'}]
        odp = odp.split(":")[1]
        //odp = 'fizyka'}]
        odp = odp.dropLast(3)
        odp = odp.drop(1)
        Log.d("fun znajdzPrzedmiot:","$odp")
        return odp

    }

    fun znajdzIdWJSON (response: JSONObject) : String{
        var odp = response.get("message").toString()
        //np: odp = [{"id_ucznia":'1'}]
        odp = odp.split(":")[1]
        //odp = '1'}]
        odp = odp.dropLast(3)
        odp = odp.drop(1)
        Log.d("fun znajdzIDWJSON:","$odp")
        IdWybranegoUcznia=odp
        Log.d("fun znajdzIDWJSON:","$IdWybranegoUcznia")
        return odp
    }*/
}

