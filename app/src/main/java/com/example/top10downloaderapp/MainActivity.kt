package com.example.top10downloaderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.top10downloaderapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var listTopApp: List<TopApp>
    lateinit var adpter : RecycleTopApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listTopApp = arrayListOf()
        adpter = RecycleTopApp(listTopApp , this)
        binding.rv.adapter = adpter
        binding.rv.layoutManager = LinearLayoutManager(this)

        parseRSS()
    }

    private fun parseRSS() {
        CoroutineScope(IO).launch {
            val data = async {
                val parser = XmlParser()
                parser.parse()
            }.await()
            val any = try {
                withContext(Main) {
                    adpter.update(data)

                }
            } catch (e: java.lang.Exception) {
                Log.d("MAIN", "Unable to get data")
            }
        }
    }
}