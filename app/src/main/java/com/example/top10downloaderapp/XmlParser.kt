package com.example.top10downloaderapp

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL

class XmlParser {
    private val listNews = ArrayList<TopApp>()

    var title: String = ""
    var image: String = ""
    var name = ""

    fun parse(url: String): List<TopApp> {
        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            val url = URL(url)
            parser.setInput(url.openStream(), null)
            var insideEntry = false
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.name.equals("entry", true)) {
                        insideEntry = true;
                    } else if (parser.name.equals("title", true)) {
                        if (insideEntry)
                            title = parser.nextText()
                    } else if (parser.name.equals("im:name", true)) {
                        if (insideEntry)
                            name = parser.nextText()
                    } else if (parser.name.equals("im:image", true)) {
                        if (insideEntry)
                            if (parser.getAttributeValue(0).equals("100"))
                                image = parser.nextText()


                    }


                } else if (eventType == XmlPullParser.END_TAG && parser.name.equals(
                        "entry",
                        true
                    )
                ) {
                    insideEntry = false;
                    listNews.add(TopApp(title, image, name))

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