package com.example.app_body_mass_index;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences sharedpreferences;
        final DatabaseAdapteur datasource = new DatabaseAdapteur(this);
        datasource.open();
        sharedpreferences = getSharedPreferences(USER_SERVICE, Context.MODE_PRIVATE);
        final EditText vl =(EditText)findViewById(R.id.slogin);
        final EditText vp =(EditText)findViewById(R.id.spassword);
        Button connect =(Button)findViewById(R.id.connect);
        if(sharedpreferences.contains("moez") == true)
        {
            Intent intent = new Intent(getApplicationContext(),Afterverif.class);
            startActivity(intent);
        }
        connect.setOnClickListener(new View.OnClickListener()
        {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v)
            {
                if((vl.getText().toString().isEmpty()) || (vp.getText().toString().isEmpty()))
                {
                    Toast.makeText(v.getContext(), "field text invalid", Toast.LENGTH_LONG).show();
                }
                else
                {


                    Utilisateur util = new Utilisateur();
                    util = datasource.getUtilisateur(vl.getText().toString());

                    if (util == null)
                    {
                        Toast.makeText(v.getContext(), "Please Register", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        if(util.getPassword().equalsIgnoreCase(vp.getText().toString()))
                        {

                            Cours countC =new Cours();
                            countC =datasource.getCours(vl.getText().toString());
                            if(countC != null)
                            {
                                Vector<String[]> listvc = datasource.getAllCoursstring(vl.getText().toString());
                                for (int i=0; i<listvc.size();i++)
                                {

                                    String[] cref=listvc.get(i);
                                    String[] datec = cref[5].split("/");
                                    String[] timed = cref[3].split(":");
                                    String[] timef = cref[4].split(":");
                                    java.util.Calendar calendart = java.util.Calendar.getInstance();

                                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                                    calendar.set(java.util.Calendar.MONTH, Integer.parseInt(datec[1]));
                                    calendar.set(java.util.Calendar.YEAR, Integer.parseInt(datec[2]));
                                    calendar.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(datec[0]));
                                    calendar.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(timed[0]));
                                    calendar.set(java.util.Calendar.MINUTE, Integer.parseInt(timed[1]));
                                    java.util.Calendar calendar2 = java.util.Calendar.getInstance();
                                    calendar2.set(java.util.Calendar.MONTH, Integer.parseInt(datec[1]));
                                    calendar2.set(java.util.Calendar.YEAR, Integer.parseInt(datec[2]));
                                    calendar2.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(datec[0]));
                                    calendar2.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(timef[0]));
                                    calendar2.set(java.util.Calendar.MINUTE, Integer.parseInt(timef[1]));
                                    if((calendar.getTimeInMillis()>calendart.getTimeInMillis()) || (calendar.getTimeInMillis()==calendart.getTimeInMillis()) )
                                    {
                                        setAlarm(MainActivity.this, Integer.parseInt(cref[0]),calendar.getTimeInMillis(),"Hello Mr "+vl.getText().toString()+" Le Cours:: "+cref[2]+"est debut Please Lorsqut Fin Curs Enregistré Le IMC",0);
                                        setAlarm(MainActivity.this, Integer.parseInt(cref[0])+1000,calendar2.getTimeInMillis(),"Hello Mr "+vl.getText().toString()+" Le Cours:: "+cref[2]+"est fin Please  Enregistré Le IMC",1);

                                    }

                                }
                            }
                            //  Fin Traitement*/
                            Intent intent = new Intent(getApplicationContext(),Afterverif.class);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("moez",util.getLogin());
                            editor.commit();
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(v.getContext(), "password incorrect try again", Toast.LENGTH_LONG).show();

                        }
                    }

                }

            }
        });
        final EditText rl=(EditText)findViewById(R.id.logir);
        final EditText rfn=(EditText)findViewById(R.id.fname);
        final EditText rln=(EditText)findViewById(R.id.lname);
        final EditText rp=(EditText)findViewById(R.id.password);
        final EditText rt=(EditText)findViewById(R.id.taille);
        final EditText rpo=(EditText)findViewById(R.id.pids);
        Button register =(Button)findViewById(R.id.registre);
        register.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v)
            {
                if((rl.getText().toString().isEmpty()) || (rfn.getText().toString().isEmpty()) || (rln.getText().toString().isEmpty()) || (rp.getText().toString().isEmpty())|| (rt.getText().toString().isEmpty()) || (rpo.getText().toString().isEmpty()))
                {
                    Toast.makeText(v.getContext(), "Please add your information", Toast.LENGTH_LONG).show();
                }
                else
                {


                    Utilisateur util = new Utilisateur();
                    util = datasource.getUtilisateur(rl.getText().toString());

                    if (util == null)
                    {
                        Utilisateur utili = new Utilisateur(rl.getText().toString(),rfn.getText().toString(),rln.getText().toString(),rp.getText().toString());
                        long vi1= datasource.insertUtilisateur(utili);
                        if(vi1 != 0)
                        {
                            int taille = Integer.parseInt(rt.getText().toString());
                            int poid = Integer.parseInt(rpo.getText().toString());
                            double imce = poid/(taille*taille);
                            long c = System.currentTimeMillis()-1000;
                            String datei = new SimpleDateFormat("dd/mm/yyyy").format(c);
                            Imc imc = new Imc(rl.getText().toString(),datei,poid,taille,imce+"");
                            long vi2 =datasource.insertImc(imc);
                            if (vi2 != 0)
                            {
                                Intent intent = new Intent(getApplicationContext(),Afterverif.class);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("moez",utili.getLogin());
                                editor.commit();
                                startActivity(intent);

                            }
                            else
                            {
                                Toast.makeText(v.getContext(), "save failed", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                    else
                    {
                        Toast.makeText(v.getContext(), "Login already exist", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });


    }
    public void setAlarm(Context context, int pk, long timeinmilis, String ch, int etat) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("alarm", pk);
        alarmIntent.putExtra("ch", ch);
        alarmIntent.putExtra("etat", etat);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, pk, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            manager.setExact(AlarmManager.RTC_WAKEUP, timeinmilis, pendingIntent);
        else
            manager.set(AlarmManager.RTC_WAKEUP, timeinmilis, pendingIntent);
    }

}
