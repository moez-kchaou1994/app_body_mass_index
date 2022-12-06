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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
public class Acour extends AppCompatActivity {
    private PendingIntent pendingIntent,pendingIntent2;
    AlarmManager alarmManager;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajoute_cours);
        final DatabaseAdapteur datasource = new DatabaseAdapteur(this);
        datasource.open();
        final SharedPreferences sharedpreferences = getSharedPreferences(USER_SERVICE, Context.MODE_PRIVATE);
        final String user =sharedpreferences.getString("moez","moez");
        final EditText nc=(EditText)findViewById(R.id.editText);
        final DatePicker date=(DatePicker)findViewById(R.id.datePicker);
        date.setMinDate(System.currentTimeMillis()-1000);
        final TimePicker heure=(TimePicker)findViewById(R.id.timePicker);
        heure.setIs24HourView(true);
        final TimePicker heurf;
        heurf=(TimePicker)findViewById(R.id.timePicker2);
        heurf.setIs24HourView(true);
        Button insertc=(Button)findViewById(R.id.insertc);
        Button retour=(Button)findViewById(R.id.retour);

        insertc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                int dd =date.getDayOfMonth();
                int mm =date.getMonth();
                int yy =date.getYear();
                String datec= dd+"/"+mm+"/"+yy;
                int hd=heure.getCurrentHour();
                int mind =heure.getCurrentMinute();
                String timed=hd+":"+mind;
                int hf=heurf.getCurrentHour();
                int minf =heurf.getCurrentMinute();
                String timef=hf+":"+minf;
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH,mm);
                calendar.set(Calendar.YEAR,yy);
                calendar.set(Calendar.DAY_OF_MONTH,dd);
                calendar.set(Calendar.HOUR_OF_DAY, hd);
                calendar.set(Calendar.MINUTE, mind);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.set(Calendar.MONTH,mm);
                calendar2.set(Calendar.YEAR,yy);
                calendar2.set(Calendar.DAY_OF_MONTH,dd);
                calendar2.set(Calendar.HOUR_OF_DAY, hf);
                calendar2.set(Calendar.MINUTE, minf);
                if(nc.getText().toString().isEmpty())
                {
                    Toast.makeText(v.getContext(), "field text is required", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Cours countCv=new Cours();
                    countCv =datasource.getCoursLoginNom(user,nc.getText().toString());
                    if (countCv != null)
                    {
                        Toast.makeText(v.getContext(), "Name Cours already existe", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        if((calendar2.getTimeInMillis()<calendar.getTimeInMillis())||(calendar.getTimeInMillis()< System.currentTimeMillis())||(calendar.getTimeInMillis()== System.currentTimeMillis())||(calendar2.getTimeInMillis()==calendar.getTimeInMillis()))
                        {
                            Toast.makeText(v.getContext(), "Date not valid", Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            Cours cva;
                            cva=datasource.getCoursLoginDate(user,datec);
                            if (cva == null)
                            {

                                int id =datasource.getAllLastidCours();
                                Cours cour =new Cours(user,nc.getText().toString(),timed,timef,datec);
                                long insertCoursn = datasource.insertCours(cour);
                                if(insertCoursn != 0)
                                {
                                    Toast.makeText(v.getContext(), "add success", Toast.LENGTH_LONG).show();
                                    setAlarm(Acour.this,id+1,calendar.getTimeInMillis(),"Salam Mr "+user+" Cours"+nc.getText().toString()+"first please  save IMC",0);
                                    setAlarm(Acour.this,id+1000,calendar2.getTimeInMillis(),"Salam Mr "+user+"  Cours"+nc.getText().toString()+"final please  save IMC",1);
                                }
                                else
                                {
                                    Toast.makeText(v.getContext(), "add failed", Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {


                                Cours nncv =datasource.VerifDataCourc(user,datec,timed,timef);
                                if (nncv == null)
                                {
                                    int id =datasource.getAllLastidCours();
                                    Cours cour =new Cours(user,nc.getText().toString(),timed,timef,datec);
                                    long i = datasource.insertCours(cour);
                                    if(i!=0)
                                    {
                                        Toast.makeText(v.getContext(), "add success", Toast.LENGTH_LONG).show();
                                        setAlarm(Acour.this,id+1,calendar.getTimeInMillis(),"Hello Mr "+user+" Le Cours"+nc.getText().toString()+"est debut Please Lorsqut Fin Curs Enregistré Le IMC",0);
                                        setAlarm(Acour.this,id+1000,calendar2.getTimeInMillis(),"Hello Mr "+user+" Le Cours"+nc.getText().toString()+"est fin Please  Enregistré Le IMC",1);
                                    }
                                    else
                                    {
                                        Toast.makeText(v.getContext(), "add failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(v.getContext(), "add abother datetime", Toast.LENGTH_LONG).show();

                                }
                            }
                        }
                    }



                }
            }
        });
        //Fin Traitements
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),Afterverif.class);
                startActivity(intent);
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
    //pk ===id
    public void cancelAlarm(Context context, int pk)
    {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, pk, alarmIntent, PendingIntent.FLAG_NO_CREATE);
        manager.cancel(pendingIntent);
    }
}

