package org.me.gcu.abedelkriem_hamid_s2027185;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RoadWorksParser {

    // We don't use namespaces
    private static final String ns = null;

    public RoadWorksParser() {
    }

    /** Given a string representation of a URL, sets up a connection and gets
     * an input stream.
     */
    private InputStream getUrlStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(20000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }

    public Channel parse(String urlString) throws XmlPullParserException, IOException {
        InputStream in = null;
        try {
            in = getUrlStream(urlString);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();

            return readFeed(parser);

        } finally {
            if ( in != null ) in.close();
        }
    }


    private Channel readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        Channel channel = new Channel();
        channel.setItems( new ArrayList<Item>() );

        parser.require(XmlPullParser.START_TAG, ns, "rss");//main tag
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if ( name.equals("channel") ) {
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    name = parser.getName();
                    // Starts by looking for the item tag
                    if (name.equals("title")) {//Channel title
                        channel.setTitle( getValue(parser, "title") );
                    } else if (name.equals("description")) { // description
                        channel.setDescription( getValue(parser, "description") );
                    } else if (name.equals("link")) { // link
                        channel.setLink( getValue(parser, "link") );
                    } else if (name.equals("lastBuildDate")) { // lastBuildDate
                        channel.setLastBuildDate( getValue(parser, "lastBuildDate") );
                    } else if (name.equals("docs")) { // docs
                        channel.setDocs( getValue(parser, "docs") );
                    } else if (name.equals("generator")) { // generator
                        channel.setGenerator( getValue(parser, "generator") );
                    } else if (name.equals("webMaster")) { // generator
                        channel.setWebMaster( getValue(parser, "webMaster") );

                    } else if (name.equals("item")) {
                        channel.getItems().add( readItem(parser) );
                        //todo: limit number of items

                    } else {
                        skip(parser);
                    }
                }
            }
        }
        return channel;
    }

    // Parses the contents of an entry. If it encounters a title, desc, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private Item readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        Item item = new Item();
        /*String title = null;
        String description = null;
        String link = null;*/

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {//title
               item.setTitle( getValue(parser, "title") );

            } else if (name.equals("description")) {//description
               item.setDescription( getValue(parser, "description") );

            } else if (name.equals("link")) {//link
               item.setLink( getValue(parser, "link") );

            } else if (name.equals("pubDate")) {//pubDate
                String dateValue = getValue(parser, "pubDate");
                //String date = Utility.getDate (dateValue, Utility.ITEM_PUB_DATE_FORMAT);
                item.setPubDate( dateValue );

            } else {
                skip(parser);
            }
        }
        return item;//new Item(title, description, link);
    }

    // Processes generic for a given parser and tage .
    private String getValue(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String value = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return value;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
