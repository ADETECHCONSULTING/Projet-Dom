package com.example.jean.test.modele;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JEAN on 09/01/2017.
 */

public class InscriptionRequest extends StringRequest {
    private static final String SERVADR = "http://192.168.0.21/test/register.php";
    private Map<String, String> params;

    public InscriptionRequest(String email, String mdp, String name, Response.Listener<String> listener){
        super(Method.POST, SERVADR, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("mdp", mdp);
        params.put("name", name);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
