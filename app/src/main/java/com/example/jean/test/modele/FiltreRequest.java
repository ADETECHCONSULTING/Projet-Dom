package com.example.jean.test.modele;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JEAN on 17/01/2017.
 */

public class FiltreRequest extends StringRequest{
    private static final String SERVADR = "http://192.168.0.12/test/filtre.php";
    private Map<String, String> params;


    public FiltreRequest(String option, String type, Response.Listener<String> listener){
        super(Method.POST, SERVADR, listener, null);
        params = new HashMap<>();
        params.put("valeur", "unType");
        params.put("option", option);
        params.put("type", type);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
