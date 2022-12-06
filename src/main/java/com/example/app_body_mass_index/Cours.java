package com.example.app_body_mass_index;

public class Cours
{
    int id;
    String login;
    String nomc;
    String timed;
    String timef;
    String datec;
    public Cours()
    {

    }

    public Cours(int id, String login, String nomc, String timed, String timef, String datec) {
        this.id = id;
        this.login = login;
        this.nomc = nomc;
        this.timed = timed;
        this.timef = timef;
        this.datec = datec;
    }

    public Cours(String login, String nomc, String timed, String timef, String datec) {
        this.login = login;
        this.nomc = nomc;
        this.timed = timed;
        this.timef = timef;
        this.datec = datec;
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

    public String getNomc() {
        return nomc;
    }

    public void setNomc(String nomc) {
        this.nomc = nomc;
    }

    public String getTimed() {
        return timed;
    }

    public void setTimed(String timed) {
        this.timed = timed;
    }

    public String getTimef() {
        return timef;
    }

    public void setTimef(String timef) {
        this.timef = timef;
    }

    public String getDatec() {
        return datec;
    }

    public void setDatec(String datec) {
        this.datec = datec;
    }
}

