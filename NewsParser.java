package com.cst2335.finalproject;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class NewsParser {

    final String TAG = "NEWS_PARSER";


    public NewsParser() {

    }



    public class News {

        String title;
        String link;
        List<Item> itemList;

        public News() {
            itemList = new ArrayList<Item>();
        }

    }

    public class Item {
        public Item() {
        }

        String title;
        String link;
        String guid;
        String pubDate;
        String author;
        String category;
        String description;

    }

    public News parseNews(InputStream is, String content) {

        String tagname = null, text = "";
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;

        Item item = null;

        News news = new News();

        //https://androidpala.com/android-xml-parser/
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            if (content == null)
                parser.setInput(is, null);
            else
                parser.setInput(new StringReader(content));
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagname = parser.getName();
                        Log.d(TAG, "Event: START_TAG: " + tagname);
                        if (tagname.equals("item")){
                            item = new Item();
                            item.pubDate = parser.getText();
                       }
                        if (tagname.equals("title")){
                            item = new Item();
                            item.title= parser.getAttributeValue(null, "value");
                        }


                        if (tagname.equals("link")){
                            item = new Item();
                            item.link= parser.getAttributeValue(null, "link");
                        }
                        if (tagname.equals("guid")){
                            item = new Item();
                            item.guid= parser.getAttributeValue(null, "guid");
                        }
                        if (tagname.equals("author")){
                            item = new Item();
                           // item.author= parser.getAttributeValue(null, "author");
                             item.author= parser.getText();
                        }
                        if (tagname.equals("description")){
                            item = new Item();
                            item.description= parser.getAttributeValue(null, "description");
                        }

                        break;

                    case XmlPullParser.TEXT:
                        tagname = parser.getName();
                        //text = parser.getName();
                        Log.d(TAG, "Event: TEXT: " + text);

                        if (tagname.equals("title")){
                           news.title = text;
                        }
                        if (tagname.equals("link")){
                           news.link = text;
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        tagname = parser.getName();
                        Log.d(TAG, "Event: END_TAG: " + tagname);
                        if (tagname.equals("item")){
                            news.itemList.add(item);
                        }
                        break;

                    default:
                        Log.d(TAG, "Event: unhandled");
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return news;
    }


}