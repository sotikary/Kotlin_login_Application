package com.example.myapplication

import android.media.tv.TvContract
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.toolbar))
        var EditText_Person = findViewById<EditText>(R.id.editTextTextPersonName)
        var EditText_Password = findViewById<EditText>(R.id.editTextTextPassword2)
        val API_URL = "https://reqres.in/api/"
        var retrofit: Retrofit? = null

        findViewById<Button>(R.id.login_button).setOnClickListener{
            //override fun onClick(view: View?) {
                    val email=EditText_Person.text.toString().trim();
                   val password= EditText_Password.text.toString().trim();

                if (email.isEmpty()){
                    EditText_Person.error="Email required";
                    EditText_Person.requestFocus();
                    return@setOnClickListener;
                }

                if (password.isEmpty()){
                    EditText_Password.error="Password required";
                    EditText_Password.requestFocus();
                    return@setOnClickListener;

                }



            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header(TvContract.Channels.CONTENT_TYPE, "application/json")
                        .addHeader(
                            "Authorization",
                            "Basic Auth" + Base64.encodeToString(
                                "eve.holt@reqres.in:cityslicka".toByteArray(),
                                Base64.NO_WRAP
                            )
                        )
                        .method(original.method, original.body);

                    val request = requestBuilder.build()
                    chain.proceed(request)
                }.build()

              //  if (retrofit === null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()

            //    }
             val newuser: APIService= retrofit!!.create(APIService::class.java)
            //val userLog : Call<LoginResponse> =  user.login(email, password)
            newuser.login(email, password).enqueue(object: Callback<LoginResponse> {
               override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(getBaseContext(), t.message, Toast.LENGTH_LONG).show();
                   // Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show();
               }

               override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>){
                   Toast.makeText(getBaseContext(), response.body()?.token, Toast.LENGTH_LONG)
                       .show();
               }
           })
    }
        }



}