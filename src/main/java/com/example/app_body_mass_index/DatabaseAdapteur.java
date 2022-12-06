package com.example.app_body_mass_index;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;
public class DatabaseAdapteur
{
private static final int BASE_VERSION = 1;
private static final String BASE_NOM = "User.db";
private SQLiteDatabase bd;
private MaBaseOpenHelper baseHelper;
public DatabaseAdapteur(Context ctx)
        {
        baseHelper = new MaBaseOpenHelper(ctx, BASE_NOM, null, BASE_VERSION);

        }
public SQLiteDatabase open()
        {
        bd = baseHelper.getWritableDatabase();
        return bd;
        }

        public void close()
        {
        bd.close();
        }

        public SQLiteDatabase getBaseDonnees()
        {
        return bd;
        }
        public Utilisateur getUtilisateur(String login)
        {

        Cursor c = this.bd.query("user", new String[] {"id", "login","fname", "lname","password"}, "login = '" + login+"'", null, null, null, null);
        return cursorToUtilisateur(c);
        }

        public Cours getCours(String login)
        {

        Cursor c = this.bd.query("cours",new String[] {"id", "login","nomc", "timed","timef","datec"}, "login = '" + login+"'", null, null, null, null);

        return cursorToCours(c);
        }
        public Imc getImc(String login)
        {

        Cursor c = this.bd.query("imc",new String[] {"id", "login","datei" ,"poid", "taille","imc"}, "login = '" + login+"'", null, null, null, null);
        return imcToImc(c);
        }
        public Cours getCoursLoginDate(String login, String datec)
        {

        Cursor c = this.bd.query("cours",new String[] {"id", "login","nomc", "timed","timef","datec"}, "login = '" + login+"' and datec = '"+datec+"'", null, null, null, null);
        return cursorToCours(c);
        }
        public Cours VerifDataCourc(String login, String datec, String timed, String timef)
        {

        Cursor c = this.bd.query("cours",new String[] {"id", "login","nomc", "timed","timef","datec"}, "login ='" + login+"' AND datec ='"+datec+"' AND (timed ='"+timed+"' OR timef ='"+timef+"')", null, null, null, null);
        return cursorToCours(c);
        }
        public Cours getCoursLoginNom(String login, String nom)
        {

        Cursor c = this.bd.query("cours",new String[] {"id", "login","nomc", "timed","timef","datec"}, "login ='" + login+"' and nomc ='"+nom+"'", null, null, null, null);
        return cursorToCours(c);
        }

        private Utilisateur cursorToUtilisateur(Cursor c)
        {
        if (c.getCount() == 0) {return null;}
        c.moveToFirst();
        Utilisateur retUtilisateur = new Utilisateur();
        retUtilisateur.setID(c.getInt(0));
        retUtilisateur.setLogin(c.getString(1));
        retUtilisateur.setFname(c.getString(2));
        retUtilisateur.setLname(c.getString(3));
        retUtilisateur.setPassword(c.getString(4));
        c.close();
        return retUtilisateur;
        }
        private Cours cursorToCours(Cursor c)
        {


        if (c.getCount() == 0) {return null;}
        c.moveToFirst();
        Cours retCours = new Cours();
        retCours.setId(c.getInt(0));
        retCours.setLogin(c.getString(1));
        retCours.setNomc(c.getString(2));
        retCours.setTimed(c.getString(3));
        retCours.setTimef(c.getString(4));
        retCours.setDatec(c.getString(5));
        c.close();
        return retCours;
        }
        private Imc imcToImc(Cursor c)
        {
        //id integer, login text, datei , poid int , taille int , imc text not null

        if (c.getCount() == 0) {return null;}
        c.moveToFirst();
        Imc retIms = new Imc();
        retIms.setId(c.getInt(0));
        retIms.setLogin(c.getString(1));
        retIms.setDate(c.getString(2));
        retIms.setPoid(c.getInt(3));
        retIms.setTaille(c.getInt(4));
        retIms.setImc(c.getString(5));
        c.close();
        return retIms;
        }
        public long insertUtilisateur(Utilisateur utilisateur)
        {
        ContentValues valeurs = new ContentValues();
        valeurs.put("login", utilisateur.getLogin());
        valeurs.put("fname", utilisateur.getFname());
        valeurs.put("lname", utilisateur.getLname());
        valeurs.put("password", utilisateur.getPassword());
        return bd.insert("user", null, valeurs);
        }
        public long insertCours(Cours cour)
        {

        ContentValues valeurs = new ContentValues();
        valeurs.put("login", cour.getLogin());
        valeurs.put("nomc", cour.getNomc());
        valeurs.put("timed", cour.getTimed());
        valeurs.put("timef", cour.getTimef());
        valeurs.put("datec",cour.getDatec());
        return bd.insert("cours", null, valeurs);
        }
        public long insertImc(Imc imc)
        {
        ContentValues valeurs = new ContentValues();
        valeurs.put("login", imc.getLogin());
        valeurs.put("datei", imc.getDate());
        valeurs.put("poid", imc.getPoid());
        valeurs.put("taille", imc.getTaille());
        valeurs.put("imc", imc.getImc());
        return bd.insert("Imc", null, valeurs);
        }

        public int getAllLastidCours()
        {
        int id=0;
        Cursor cursor = bd.query("cours",null,null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
        id++;
        cursor.moveToNext();
        }
        return id;

        }
        public Vector<String[]> getAllCoursstring(String loginn)
        {
        Vector<String[]> lcours = new Vector<String[]>();
        Cursor cursor = bd.query("cours",null,"login = '" + loginn+"'", null, null, null, null);
        for(int i=0;i<=cursor.getCount();i++)
        {

        if(cursor.moveToPosition(i)) {
        String[] data = new String[6];
        data[0] = "" + cursor.getInt(0);
        data[1] = cursor.getString(1);
        data[2] = cursor.getString(2);
        data[3] = cursor.getString(3);
        data[4] = cursor.getString(4);
        data[5] = cursor.getString(5);
        lcours.add(data);
        }
        }
        return lcours;
        }
        public Vector<String[]> getAllImcstring(String loginn)
        {
        Vector<String[]> limc = new Vector<String[]>();
        Cursor cursor = bd.query("imc",null,"login = '" + loginn+"'", null, null, null, null);
        for(int i=0;i<=cursor.getCount();i++)
        {
        if(cursor.moveToPosition(i)) {
        String[] data = new String[6];
        data[0] = "" + cursor.getInt(0);
        data[1] = cursor.getString(1);
        data[2] = cursor.getString(2);
        data[3] = "" +cursor.getInt(3);
        data[4] = "" +cursor.getString(4);
        data[5] = cursor.getString(5);
        limc.add(data);
        }
        }
        return limc;
        }
        public int updateCourc(Cours cup)
        {
        //id integer  login text  nomc text   timed text, timef text, datec text

        ContentValues valeurs = new ContentValues();
        valeurs.put("login", cup.getLogin());
        valeurs.put("nomc", cup.getNomc());
        valeurs.put("timed", cup.getTimed());
        valeurs.put("timef", cup.getTimef());
        valeurs.put("datec", cup.getDatec());
        return bd.update("cours", valeurs, "id =" +cup.getId(),null);
        }
        public int Deletecours(int id)
        {
        int i =bd.delete("cours", "id =" + id, null);
        return i;
        }


        }
