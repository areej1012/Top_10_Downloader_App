package com.example.top10downloaderapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
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
    var url = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listTopApp = arrayListOf()
        adpter = RecycleTopApp(listTopApp , this)
        binding.rv.adapter = adpter
        binding.rv.layoutManager = LinearLayoutManager(this)
        title = "Top 10 Apps"
        parseRSS()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.top10 ->{
                url = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml"
                title = "Top 10 Apps"
                parseRSS()

            }
            R.id.top100 ->{
                url = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=100/xml"
                title = "Top 100 Apps"
                parseRSS()
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun parseRSS() {
        CoroutineScope(IO).launch {
            val data = async {
                val parser = XmlParser()
                parser.parse(url)
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