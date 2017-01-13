package com.example.jean.test.modele;

/**
 * Created by JEAN on 09/01/2017.
 */

public class Utilisateur {

    private String login;
    private String mdp;

    public Utilisateur(String login, String mdp){
        this.login = login;
        this.mdp = mdp;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
