package com.example.kotlinlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val spinner: Spinner = findViewById(R.id.spinner)
// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            this,
            R.array.role,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            spinner.adapter = adapter
        }
    }

    fun onClickSwitchToLogin(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    fun onClickRegister(v: View) {

        val  user = findViewById<EditText>(R.id.registerUserEditText).text.toString()

        val  email = findViewById<EditText>(R.id.registerEmailEditText).text.toString()
        val  password = findViewById<EditText>(R.id.registerPasswordlEditText).text.toString()
        val  password2 = findViewById<EditText>(R.id.registerConfirmPasswordlEditText).text.toString()
        val selectedRole = findViewById<Spinner>(R.id.spinner).getSelectedItem().toString()
        var roleid = 0
        if(selectedRole == "nauczyciel"){
            roleid = 2
        }
        else if(selectedRole == "rodzic"){
            roleid = 3
        }
        else{
            roleid = 1
        }

        if (!password.equals(password2)) {
            Toast.makeText(this, "Hasla nie pasuja!", Toast.LENGTH_LONG).show()
            return
        }

        //val url = "http://10.0.2.2/androiddb/"
        val url = getString(R.string.db_url)

        // Post parameters
        val jsonObject = JSONObject()
        Log.i("JSONObject", "putting user=$user")
        jsonObject.put("username",user)
        jsonObject.put("password",password)
        jsonObject.put("email",email)
        jsonObject.put("roleid",roleid)
        jsonObject.put("query","")

        Log.i("onClick", "About sending request JSON=$jsonObject URL=$url")

        // Volley post request with parameters
        val requestPOST = JsonObjectRequest(Request.Method.POST,url,jsonObject,
            Response.Listener { response ->
                // Process the json
                try {
                    Log.i("JSON", "received response=$response")
                    processResponse(response)
                    Log.d("fun onClickRegister:","Response: $response")
                }catch (e:Exception){
                    Log.d("fun onClickRegister:","Exception: $e")
                }

            }, Response.ErrorListener{
                // Error in request
                Log.d("fun onClickRegister:","Volley error: $it")
            })


        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)

    }

    fun processResponse(response: JSONObject) {
        Log.d("processResponse", "Enter")
        if (response["success"]==1) {
        Toast.makeText(this, response["message"].toString(), Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        }
        if (response["success"]==0) {
            Toast.makeText(this, response["message"].toString(), Toast.LENGTH_LONG).show()
        }

    }
}