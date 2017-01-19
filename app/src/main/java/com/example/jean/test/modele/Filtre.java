package com.example.jean.test.modele;

/**
 * Created by JEAN on 17/01/2017.
 */

public class Filtre {
    private int id;
    private String filtre;
    private String specification;

    public Filtre(int id, String filtre, String specification){
        this.id = id;
        this.filtre = filtre;
        this.specification = specification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFiltre() {
        return filtre;
    }

    public void setFiltre(String filtre) {
        this.filtre = filtre;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
