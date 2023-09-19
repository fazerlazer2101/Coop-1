package com.example.coopt_1

//Import these items

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONArray


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Saves the button id to submitAPI.
        /*val submitAPI: Button = findViewById(R.id.btn_searchForImage)*/

        //Set on click event handler
        /*submitAPI.setOnClickListener()
        {
            val resultTextView: TextView = findViewById((R.id.txt_HoldApi))
            resultTextView.text = "Api";
        }*/

        /*submitAPI.setOnClickListener()
        {
            val resultTextView: TextView = findViewById((R.id.txt_HoldApi))
            resultTextView.text = "Api";
        };*/

        findViewById<Button>(R.id.btn_searchForImage).setOnClickListener()
        {
            //Setting up Volley new request
            val queue = Volley.newRequestQueue(this)
            val url = "https://openlibrary.org/isbn/9780140328721.json"
            

            val resultTextView: TextView = findViewById((R.id.txt_HoldApi))

            //Image items
            val imageView:  ImageView = findViewById<ImageView>(R.id.img_Recieved)
            //Retrieves jsonObject
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->


                    //Parse JSON
                    //Gets an array from json
                    val responseJSON: JSONArray = response.getJSONArray("covers")

                    //Sends to text view
                    //Loops through array
                    var i:Int = 0
                    var imageId:String = ""
                    while (i < responseJSON.length())
                    {
                        imageId = responseJSON.getString(i)
                        resultTextView.text = responseJSON.getString(i)
                        i++
                    }


                    //Loads recieved image
                    Glide.with(this).load("https://covers.openlibrary.org/b/id/${imageId}-L.jpg").into(imageView)
                },
                { resultTextView.text = "That didn't work!" })

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest)
        }
    }
}