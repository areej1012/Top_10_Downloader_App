package com.example.top10downloaderapp

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL

class XmlParser {
    private val listNews = ArrayList<TopApp>()

    var title: String = ""
    var image: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    fun parse(): List<TopApp> {
        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            val url = URL("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
            parser.setInput(url.openStream(), null)
            var insideEntry = false
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.name.equals("entry",true)) {
                        insideEntry = true;
                    } else if (parser.name.equals("title",true)) {
                        if (insideEntry)
                            title = parser.nextText()
                    }
                    else if (parser.name.equals("im:image", true)) {
                        if (insideEntry)
                            if(parser.getAttributeValue(0).equals("100"))
                                image = parser.nextText()

                    }


                } else if (eventType == XmlPullParser.END_TAG && parser.name.equals("entry",true)) {
                    insideEntry = false;
                    listNews.add(TopApp(title,image))

                }

                eventType = parser.next()
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return listNews
    }
}