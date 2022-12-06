package com.example.app_body_mass_index;

public class Imc
{
    int id;
    String login;
    String datei;
    int poid;
    int taille;
    String imc;

    public Imc()
    {

    }

    public Imc(int id, String login, String datei, int poid, int taille, String imc) {
        this.id = id;
        this.login = login;
        this.datei = datei;
        this.poid = poid;
        this.taille = taille;
        this.imc = imc;
    }

    public Imc(String login, String datei, int poid, int taille, String imc) {
        this.login = login;
        this.datei = datei;
        this.poid = poid;
        this.taille = taille;
        this.imc = imc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDate() {
        return datei;
    }

    public void setDate(String datei) {
        this.datei = datei;
    }

    public int getPoid() {
        return poid;
    }

    public void setPoid(int poid) {
        this.poid = poid;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }
}

