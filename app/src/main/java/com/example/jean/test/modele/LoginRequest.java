package com.example.jean.test.modele;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JEAN on 09/01/2017.
 */

public class LoginRequest extends StringRequest {
    private static final String SERVADR = "http://192.168.0.12/test/login.php";
    private Map<String,String> users;

    public LoginRequest(String email, String mdp, Response.Listener<String> listener) {
        super(Method.POST, SERVADR, listener, null);
        users = new HashMap<>();
        users.put("email", email);
        users.put("mdp", mdp);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return users;
    }
}
