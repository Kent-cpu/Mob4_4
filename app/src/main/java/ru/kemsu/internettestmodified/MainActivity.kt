package ru.kemsu.internettestmodified

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import okhttp3.*
import java.net.HttpURLConnection
import java.net.URL
import okhttp3.Response
import okhttp3.OkHttpClient
import java.io.IOException



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val link = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1"
        var httpClient: OkHttpClient = OkHttpClient()
        findViewById<Button>(R.id.btnHTTP).setOnClickListener{
            val json = URL(link)
            Thread {
                try{
                    val connection = json.openConnection() as HttpURLConnection
                    val data = connection.inputStream.bufferedReader().readText()
                    connection.disconnect()
                    Log.d("Flickr Cats", data);
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }.start()
        }

        fun getAsyncCall(){
                val request: Request = Request.Builder()
                    .url(link)
                    .build()

                httpClient.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        Log.e(TAG, "error in getting response using async okhttp call")
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call?, response: Response) {
                        val responseBody = response.body()
                        if (!response.isSuccessful) {
                            throw IOException("Error response $response")
                        }
                        Log.i("Flickr OkCats", responseBody!!.string())
                    }
                })
        }


        findViewById<Button>(R.id.btnOkHTTP).setOnClickListener{
            getAsyncCall();
        }
    }

}