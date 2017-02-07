package com.example.jean.test.modele;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by JEAN on 06/02/2017.
 */

public class XMLAsynctask extends AsyncTask<String,Void,Document>{

    public interface DocumentConsumer{
        void setXMLDocument(Document document);
    }

    private DocumentConsumer consumer;

    public XMLAsynctask(DocumentConsumer documentConsumer){
        this.consumer = documentConsumer;
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
    protected void onPostExecute(Document result) {
        Log.e("XMLAsynctask : ", "Finished");
        consumer.setXMLDocument(result);
    }
}
