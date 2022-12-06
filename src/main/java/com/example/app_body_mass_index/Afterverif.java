package com.example.app_body_mass_index;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;


public class Afterverif extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_verif);
        final SharedPreferences sharedpreferences = getSharedPreferences(USER_SERVICE, Context.MODE_PRIVATE);
        final String user =sharedpreferences.getString("moez","moez");
        final DatabaseAdapteur datasource = new DatabaseAdapteur(this);
        datasource.open();
        TextView tvuc =(TextView)findViewById(R.id.textView2);
        tvuc.setText(tvuc.getText().toString()+user);
        Button logout =(Button)findViewById(R.id.logout);
        Button cb =(Button)findViewById(R.id.c_b);
        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Cours countC =new Cours();
                countC =datasource.getCours(user);
                if(countC != null)
                {
                    Vector<String[]> listvcl = datasource.getAllCoursstring(user);
                    for (int i=0; i<listvcl.size();i++)
                    {

                        String[] clog = listvcl.get(i);
                        cancelAlarm(Afterverif.this, Integer.parseInt(clog[0]));
                        cancelAlarm(Afterverif.this, Integer.parseInt(clog[0]) + 1000);

                    }
                }
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        Button listc =(Button)findViewById(R.id.listcours);
        listc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Cours countCl =new Cours();
                countCl =datasource.getCours(user);
                if(countCl != null)
                {
                    Intent intent = new Intent(getApplicationContext(),ListeCours.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(v.getContext(), "Please add first Cours ", Toast.LENGTH_LONG).show();

                }
            }
        });

        Button ajc =(Button)findViewById(R.id.ajoutecours);
        ajc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),Acour.class);
                startActivity(intent);
            }
        });

        Button gimc =(Button)findViewById(R.id.gimc);
        gimc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),Gestionimc.class);
                startActivity(intent);
            }
        });
        cb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),Courbe.class);
                startActivity(intent);
            }
        });

    }
    public void cancelAlarm(Context context, int pk)
    {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, pk, alarmIntent, PendingIntent.FLAG_NO_CREATE);
        manager.cancel(pendingIntent);
    }
}


