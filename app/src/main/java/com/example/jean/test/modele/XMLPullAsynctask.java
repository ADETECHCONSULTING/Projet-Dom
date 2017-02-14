package com.example.jean.test.modele;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jean.test.R;
import com.example.jean.test.adapter.XMLAdapterArray;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by JEAN on 13/02/2017.
 */

public class XMLPullAsynctask extends AsyncTask<String,Integer,ArrayList<Annonce>>{
    private URL url;
    private ArrayList<Annonce> lesAnnonces;
    private InputStream is;
    private Context context;
    private View view;
    private ProgressBar bar;

    public XMLPullAsynctask(Context context, View view){
        this.context = context;
        this.view = view;
    }

    @Override
    protected ArrayList<Annonce> doInBackground(String... params) {
        try {
            url = new URL(params[0]);
            is = url.openConnection().getInputStream();
            lesAnnonces = parse();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lesAnnonces;
    }

    public void setBar(ProgressBar bar) {
        this.bar = bar;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(this.bar != null){
            bar.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Annonce> annonces) {
        bar.setVisibility(View.GONE);
        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        XMLAdapterArray xmlAdapter = new XMLAdapterArray(context, lesAnnonces);
        rv.setAdapter(xmlAdapter);
    }

    /**
     * Recupere les données du document RSS
     * @return
     */
    private ArrayList<Annonce> parse() {
        ArrayList<Annonce> lesAnnonces = null;
        XmlPullParser parser = Xml.newPullParser();
        try{
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            Annonce currentAnnonce = null;
            boolean done = false;
            while(eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        lesAnnonces = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if(name.equalsIgnoreCase("ad")){
                            currentAnnonce = new Annonce();
                        }else if(currentAnnonce != null){
                            if(name.equalsIgnoreCase("url")){
                                currentAnnonce.setUrl(parser.nextText());
                            }else if(name.equalsIgnoreCase("title")){
                                currentAnnonce.setTitle(parser.nextText());
                            }else if(name.equalsIgnoreCase("property_type")){
                                currentAnnonce.setType(parser.nextText());
                            }else if(name.equalsIgnoreCase("content")){
                                currentAnnonce.setContent(parser.nextText());
                            }else if(name.equalsIgnoreCase("price")){
                                currentAnnonce.setPrice(parser.nextText());
                                String attr = parser.getAttributeValue(null, "period");
                                Log.d("AttributeValue : ", attr);
                                currentAnnonce.setPriceAttrib(attr);
                            }else if(name.equalsIgnoreCase("country")){
                                currentAnnonce.setCountry(parser.nextText());
                            }else if(name.equalsIgnoreCase("city")){
                                currentAnnonce.setCity(parser.nextText());
                            }else if(name.equalsIgnoreCase("picture_url")){
                                currentAnnonce.setPicture_url(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if(name.equalsIgnoreCase("ad") && currentAnnonce != null){
                            lesAnnonces.add(currentAnnonce);
                        }else if(name.equalsIgnoreCase("trovit")){
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        }catch (Exception e){
            Log.d("XMLPullAsynctask", "Parse : ");
            e.printStackTrace();
        }
        return lesAnnonces;
    }


}
