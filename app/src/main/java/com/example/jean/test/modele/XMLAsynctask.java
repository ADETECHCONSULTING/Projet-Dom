package com.example.jean.test.modele;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by JEAN on 06/02/2017.
 */

public class XMLAsynctask extends AsyncTask<String,Integer,Document>{

    public interface DocumentConsumer{
        void setXMLDocument(Document document);
    }

    private ProgressBar bar;
    private DocumentConsumer consumer;

    public XMLAsynctask(DocumentConsumer documentConsumer){
        this.consumer = documentConsumer;
    }

    public void setBar(ProgressBar bar){
        this.bar = bar;
    }

    @Override
    protected Document doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            //ouverture de la connexion
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // récupération de l'InputStream
            InputStream stream = connection.getInputStream();
            try{
                return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
            }finally {
                stream.close();
            }
        } catch (Exception e) {
            Log.e("XMLAsynctask : ","Error... "+e);
            throw new RuntimeException();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(this.bar != null){
            bar.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Document result) {
        Log.e("XMLAsynctask : ", "Finished");
        bar.setVisibility(View.GONE);
        consumer.setXMLDocument(result);
    }
}
