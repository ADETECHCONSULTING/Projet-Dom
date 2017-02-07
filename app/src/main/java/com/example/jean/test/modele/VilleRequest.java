package com.example.jean.test.modele;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JEAN on 20/01/2017.
 */

public class VilleRequest extends StringRequest {
    private static final String SERVADR = "http://192.168.0.12/test/villes.php";
    private Map<String, String> params;

    public VilleRequest(Response.Listener<String> listener){
        super(Method.POST, SERVADR, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

