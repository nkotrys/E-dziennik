package com.example.kotlinlogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class processLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.process_login)
        val query =
            "SELECT role_id FROM users WHERE username = \"${MainActivity.username}\""


        val jsonObject = JSONObject()
        jsonObject.put("username", MainActivity.username)
        jsonObject.put("password", MainActivity.password)
        jsonObject.put("email", "")
        jsonObject.put("roleid", "")
        jsonObject.put("query", query)


        Log.d("query","$query")

        val url = getString(R.string.db_url)

        // Volley post request with parameters
        val requestPOST = JsonObjectRequest(Request.Method.POST, url, jsonObject,
            Response.Listener { response ->
                // Process the json
                try {
                    processResponse(response)
                    Log.d("processLogin:", "Response: $response")
                } catch (e: Exception) {
                    Log.d("processLogin:", "Exception: $e")
                }

            }, Response.ErrorListener {
                // Error in request
                Log.d("fun processLogin:", "Volley error: $it")
            })

        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)

        finish()
    }

    fun processResponse(response: JSONObject) {
        var odp = response.get("message").toString()
        //np: odp = [{"przedmiot":'fizyka'}]
        odp = odp.split(":")[1]
        //odp = 'fizyka'}]
        odp = odp.dropLast(3)
        odp = odp.drop(1)
        Log.d("fun processResponse:","odp=$odp")

        when(odp){
            "1" -> {val intencja = Intent(this, uczenActivity::class.java)
                startActivity(intencja)}
            "2" -> {val intencja = Intent(this, nauczycielActivity::class.java)
                startActivity(intencja)}
            "3" -> {val intencja = Intent(this, rodzicActivity::class.java)
                startActivity(intencja)}
        }

    }
}