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

class nieobecnosciActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nieobecnosci)
    }



    fun onClickWstaw(v: View){
        val imie = findViewById<EditText>(R.id.editTextText).text.toString()


        //INSERT INTO nieobecnosci (id_ucznia,przedmiot,data,czy_usprawiedliwione) VALUES ((SELECT id_ucznia FROM uczniowie WHERE firstname="Jan" AND lastname="Kowalski"),(SELECT przedmiot FROM nauczyciele WHERE username="afizyk"),SYS_DATE(),0)
        val query2 ="INSERT INTO nieobecnosci (id_ucznia,przedmiot,data,usprawiedliwione) VALUES ((SELECT id FROM users WHERE username=\"$imie\" ),(SELECT przedmiot FROM nauczyciel_przedmiot WHERE id_nauczyciela = (select id from users where username =\"${MainActivity.username}\")),now(),0)"
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
                    Toast.makeText(this, "dodano nieobecność", Toast.LENGTH_LONG).show()
                    Log.d("fun onClickWstaw:","Response: $response")
                    findViewById<EditText>(R.id.editTextText).setText("  imię")

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
}