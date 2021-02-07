package com.example.photo_every_day

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import coil.api.load
import com.example.photo_every_day.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val retrofitImpl: RetrofitImp = RetrofitImp()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        sendServerRequest()

    }

    private fun sendServerRequest() {
        retrofitImpl.getRequest().getPicture("S98hLRqooYZXbBHv0Cc49D8pNl3X6fgX7Hlv3eeI")
            .enqueue(object :
                Callback<Data> {

                override fun onResponse(
                    call: Call<Data>,
                    response: Response<Data>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        makeData(response.body(), null)
                    } else {
                        makeData(null, Throwable("Ответ от сервера пустой"))
                    }
                }

                override fun onFailure(call: Call<Data>, t: Throwable) {
                    makeData(null, t)
                }
            })
    }

    private fun makeData(data: Data?, error: Throwable?) {
        if (data == null || error != null) {
            //Ошибка
            Toast.makeText(this, error?.message, Toast.LENGTH_SHORT).show()
        } else {
            val url = data.url
            if (url.isNullOrEmpty()) {
                //"Ссылка на фото пустая")
                Toast.makeText(this, "@string/empty_message", Toast.LENGTH_SHORT).show()
            } else if (url.contains("www.youtube.com")) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } else {
                binding.image.load(url)
            }
            val explanation = data.explanation
            if (explanation.isNullOrEmpty()) {
                //"Описание пустое"
                Toast.makeText(this, "Описание пустое", Toast.LENGTH_SHORT).show()
            } else {
                binding.txtExplanation.text = explanation
            }
        }
    }
}



