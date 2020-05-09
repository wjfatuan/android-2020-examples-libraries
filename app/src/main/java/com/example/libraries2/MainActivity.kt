package com.example.libraries2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.libraries2.retrofit.Cat
import com.example.libraries2.retrofit.CatApi
import com.example.libraries2.room.CountriesDatabase
import com.example.libraries2.sqlite.Country
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.ImageViewBitmapInfo
import com.koushikdutta.ion.Ion
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Picasso.get()
                .setIndicatorsEnabled(true)
//        YoYo.with(Techniques.FadeOut)
//                .duration(1000)
//                .repeat(5)
//                .playOn(imgImage)
        btnRefresh.setOnClickListener { _ ->
            refreshImage()
        }

        //prepareRoomDatabase()

    }

    fun refreshImageIon() {
        Ion.with(this)
            .load("https://api.thecatapi.com/v1/images/search")
            .asJsonArray()
            .setCallback(object: FutureCallback<JsonArray> {
                override fun onCompleted(e: Exception?, result: JsonArray?) {
                    val cat = result!![0].asJsonObject
                    val url = cat.get("url").asString

                    /*Picasso.get()
                        .load(url)
                        .into(imgImage)*/
                }

            })
    }

    fun refreshImage() {
        val api = CatApi.getInstance()
        api.search().enqueue(object: retrofit2.Callback<List<Cat>> {
            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                Log.e("RETROFITEX","Error calling API", t)
            }
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                Log.v("RETROFITEX","API call successful: $response")
                for(cat in response.body()!!) {
                    showCat(cat)
                }
            }
        })
    }

    fun showCat(cat: Cat)  {
        val view = ImageView(this)

        Picasso.get()
            .load(cat.url)
            .into(view)
        containerImages.addView(view)
    }

    fun prepareDatabase() {
        val db = openOrCreateDatabase("sample1", Context.MODE_PRIVATE, null)
//        db.execSQL("CREATE TABLE countries (" +
//                "code TEXT PRIMARY KEY," +
//                "name TEXT," +
//                "continent TIXT" +
//                ")")
        val code = "ME2X"
        val name = "Mexico"
        val continent = "'); DELETE * FROM countries;"
        db.execSQL("INSERT INTO countries VALUES('$code','$name','$continent')")

        // sql injection

        val data = db.rawQuery("SELECT * FROM countries", null)
        if(data.moveToFirst()) {
            do {
                val country = Country(
                    data.getString(data.getColumnIndex("code")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("continent"))
                )
                Log.v("DATABASE", "${country}")
            } while(data.moveToNext())
        } else {
            Log.v("DATABASE", "No data")
        }
    }

    fun prepareRoomDatabase() {
        val db = CountriesDatabase.getInstance(this)
        db.countriesDao().insert(com.example.libraries2.room.Country(
            "COL",
            "Colombia",
            "America del Sur"
        ))
    }
}
