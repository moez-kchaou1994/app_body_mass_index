package com.example.app_body_mass_index;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;


public class ListeImc extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imc_list);
        final DatabaseAdapteur datasource = new DatabaseAdapteur(this);
        datasource.open();
        final SharedPreferences sharedpreferences = getSharedPreferences(USER_SERVICE, Context.MODE_PRIVATE);
        final String user =sharedpreferences.getString("moez","moez");
        final ListView list =(ListView)findViewById(R.id.listimc);
        final ArrayAdapter listadabter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        Vector<String[]> listimc=datasource.getAllImcstring(user);
        listadabter.add("Les List de Imc De Mr::"+user);
        for (int i =0; i <listimc.size() ; i++)
        {
            String[] datac=listimc.get(i);
            String ch=datac[2]+"::"+datac[3]+"::"+datac[4]+"::"+datac[5];
            listadabter.add(ch);
        }
        list.setAdapter(listadabter);
        final Button verif=(Button)findViewById(R.id.Veriflist);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                int pos=position;
                final Object itemvalue=list.getItemAtPosition(pos);
                verif.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String[] datalm=itemvalue.toString().split("::");
                        double imcell = Double.parseDouble(datalm[3]);
                        if ((imcell>=18.5) && (imcell<=24.9))
                        {
                            Toast.makeText(ListeImc.this, "weight normal", Toast.LENGTH_SHORT).show();

                        }
                        else if(imcell<18.5)
                        {
                            Toast.makeText(ListeImc.this, "underweight", Toast.LENGTH_SHORT).show();
                        }
                        else if((imcell>=25) && (imcell<=29.9))
                        {
                            Toast.makeText(ListeImc.this, "Overweight", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(ListeImc.this, "obesity", Toast.LENGTH_SHORT).show();
                        }


                    }
                });






            }
        });
        Button courbe=(Button)findViewById(R.id.Courbe);
        courbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Courbe.class);
                startActivity(intent);

            }
        });
        Button retoure=(Button)findViewById(R.id.lretour);
        retoure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),Gestionimc.class);
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
