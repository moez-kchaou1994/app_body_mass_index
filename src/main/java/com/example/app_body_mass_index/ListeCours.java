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
public class ListeCours extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_cours);
        final DatabaseAdapteur datasource = new DatabaseAdapteur(this);
        datasource.open();
        final SharedPreferences sharedpreferences = getSharedPreferences(USER_SERVICE, Context.MODE_PRIVATE);
        final String user =sharedpreferences.getString("moez","moez");
        //Debut De Traitement de Cours
        final ListView list =(ListView)findViewById(R.id.listcours);
        final ArrayAdapter listadabter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        Vector<String[]> listcours=datasource.getAllCoursstring(user);
        listadabter.add("Les List de Cours De Mr::"+user);
        for (int i =0; i <listcours.size() ; i++)
        {
            String[] datac=listcours.get(i);
            String ch=datac[2]+"::"+datac[3]+"::"+datac[4]+"::"+datac[5];
            listadabter.add(ch);
        }
        list.setAdapter(listadabter);
        final Button modife=(Button)findViewById(R.id.Update);
        final Button delete=(Button)findViewById(R.id.Delete);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                int pos=position;
                final Object itemvalue=list.getItemAtPosition(pos);
                modife.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String[] datalm=itemvalue.toString().split("::");
                        Intent intent = new Intent(getApplicationContext(),Update.class);
                        intent.putExtra("nomc",datalm[0]);
                        startActivity(intent);

                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        String[] datalm=itemvalue.toString().split("::");
                        Cours courexiste=new Cours();
                        courexiste =datasource.getCoursLoginNom(user,datalm[0]);
                        final int id=courexiste.getId();
                        int res=datasource.Deletecours(id);
                        if(res>0)
                        {
                            cancelAlarm(ListeCours.this,id);
                            cancelAlarm(ListeCours.this,id+1000);
                            Toast.makeText(ListeCours.this, "Delete success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),ListeCours.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(ListeCours.this, "Delete failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });





            }
        });



        Button retoure=(Button)findViewById(R.id.lretour);
        retoure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),Afterverif.class);
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