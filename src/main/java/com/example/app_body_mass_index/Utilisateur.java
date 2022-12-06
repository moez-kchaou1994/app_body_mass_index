package com.example.app_body_mass_index;

public class Utilisateur
{
    //private variables
    int id;
    String login;
    String fname;
    String lname;
    String password;


    // Empty constructor
    public Utilisateur()
    {

    }
    // constructor
    public Utilisateur(int id, String login, String fname, String lname, String password)
    {
        this.id = id;
        this.login=login;
        this.fname=fname;
        this.lname=lname;
        this.password=password;
    }
    public Utilisateur(String login, String fname, String lname, String password)
    {
        this.login=login;
        this.fname=fname;
        this.lname=lname;
        this.password=password;
    }


    // getting ID
    public int getID(){
        return this.id;
    }

    // setting id
    public void setID(int id){
        this.id = id;
    }

    // getting name
    public String getLogin(){
        return this.login;
    }

    // setting name
    public void setLogin(String login){
        this.login=login;
    }

    // getting
    public String getFname(){
        return this.fname;
    }

    // setting
    public void setFname(String fname){
        this.fname=fname;
    }

    // getting
    public String getLname(){
        return this.lname;
    }

    // setting
    public void setLname(String lname){
        this.lname=lname;
    }
    // getting
    public String getPassword(){
        return this.password;
    }

    // setting
    public void setPassword(String password){
        this.password=password;
    }


}

