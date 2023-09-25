package com.example.coopt_1

//Import these items

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONArray


class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Setting up Volley new request
        val queue = Volley.newRequestQueue(this)
        val resultTextView: TextView = findViewById((R.id.txt_HoldApi))
        //Image items
        val imageView:  ImageView = findViewById(R.id.img_Recieved)

        findViewById<Button>(R.id.btn_searchForImage).setOnClickListener()
        {
            //Declare a EditText variable to hold the user input
            val userInput: EditText
            //Adds the user input to variable
            userInput = findViewById(R.id.txtInput_ISBN)
            //Works with ISBN10 and ISBN13
            val url = "https://openlibrary.org/isbn/${userInput.getText()}.json"

            //Clears the imageView
            Glide.with(this).clear(imageView)
            //Loading image
            val loadIMG = "https://media1.giphy.com/media/6036p0cTnjUrNFpAlr/giphy.gif?cid=ecf05e479j2w1xbpa3tk0fx0b5mo6nax6c74nd8ct4mk6b64&ep=v1_gifs_search&rid=giphy.gif&ct=g"

            val text = "Failed to load image"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(this, text, duration) // in Activity

            //Retrieves jsonObject
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->

                    //Parse JSON
                    //Gets an array from json in the key called "covers"
                    val responseJSON: JSONArray = response.getJSONArray("covers")
                    //Stores the cover ID
                    val imageId: String

                    //If it has a cover sends image to the imageView
                    //Using negative causes a failure
                    imageId = responseJSON.getString(0)


                    //Loads recieved image
                    // With thumbnail url change the jpg if testing for errors
                    Glide.with(imageView)
                        .load("https://covers.openlibrary.org/b/id/${imageId}-L.jpg")
                        .thumbnail(Glide.with(imageView).load(loadIMG))
                        .into(imageView);
                    //Display book cover ID
                    resultTextView.text = response.getString("title")
                },//If an error occurs in fetching the data
                { resultTextView.text = "Failed to fetch the api" })

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest)
        }
    }
}