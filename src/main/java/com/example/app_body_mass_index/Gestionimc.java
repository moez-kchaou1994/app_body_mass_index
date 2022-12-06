package com.example.app_body_mass_index;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Gestionimc extends AppCompatActivity
{
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tigrabe);
        final DatabaseAdapteur datasource = new DatabaseAdapteur(this);
        datasource.open();
        final SharedPreferences sharedpreferences = getSharedPreferences(USER_SERVICE, Context.MODE_PRIVATE);
        final String user =sharedpreferences.getString("moez","moez");
        final EditText h=(EditText)findViewById(R.id.Taille5);
        final EditText hh=(EditText)findViewById(R.id.poidimc5);
        Button calcule=(Button)findViewById(R.id.calculeimc);
        final DatePicker datehff=(DatePicker)findViewById(R.id.datePicker2imc);
        datehff.setMinDate(System.currentTimeMillis()-1000);
        calcule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int dddf =datehff.getDayOfMonth();
                int mmmf =datehff.getMonth();
                int yyyf =datehff.getYear();
                String datecimc= dddf+"/"+mmmf+"/"+yyyf;
                if((h.getText().toString().isEmpty()) ||(hh.getText().toString().isEmpty()))
                {
                    Toast.makeText(v.getContext(), "verify field text", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int taillech = Integer.parseInt(h.getText().toString());
                    int poidch = Integer.parseInt(hh.getText().toString());
                    double imceh = poidch/(taillech*taillech);
                    Toast.makeText(v.getContext(), "Imc=::"+imceh, Toast.LENGTH_SHORT).show();

                }
            }
        });
        Button verif=(Button)findViewById(R.id.Verifimc);
        verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int ddfv =datehff.getDayOfMonth();
                int mmmv =datehff.getMonth();
                int yyyv =datehff.getYear();
                String datecimc= ddfv+"/"+mmmv+"/"+yyyv;
                if((h.getText().toString().isEmpty()) ||(hh.getText().toString().isEmpty()))
                {
                    Toast.makeText(v.getContext(), "verify field text", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int taillecv = Integer.parseInt(h.getText().toString());
                    int poidcv = Integer.parseInt(hh.getText().toString());
                    double imcev = poidcv/(taillecv*taillecv);
                    if ((imcev>=18.5) && (imcev<=24.9))
                    {
                        Toast.makeText(v.getContext(), "weight normal", Toast.LENGTH_SHORT).show();

                    }
                    else if(imcev<18.5)
                    {
                        Toast.makeText(v.getContext(), "underweight", Toast.LENGTH_SHORT).show();
                    }
                    else if((imcev>=25) && (imcev<=29.9))
                    {
                        Toast.makeText(v.getContext(), "Overweight", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(v.getContext(), "obesity", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        Button ajoute =(Button)findViewById(R.id.Ajouteimc);
        ajoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int ddaa =datehff.getDayOfMonth();
                int mmaa =datehff.getMonth();
                int yyaa =datehff.getYear();
                String datecimc= ddaa+"/"+mmaa+"/"+yyaa;
                if((h.getText().toString().isEmpty()) ||(hh.getText().toString().isEmpty()))
                {
                    Toast.makeText(v.getContext(), "verify field text", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int tailleca = Integer.parseInt(h.getText().toString());
                    int poidca = Integer.parseInt(hh.getText().toString());
                    double imcea = poidca/(tailleca*tailleca);
                    Imc imc = new Imc(user,datecimc,poidca,tailleca,imcea+"");
                    long vi2 =datasource.insertImc(imc);
                    if (vi2 != 0)
                    {
                        Toast.makeText(v.getContext(), "save success", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(v.getContext(), "save failed please try again", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        Button liste=(Button)findViewById(R.id.listeimc);
        liste.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),ListeImc.class);
                startActivity(intent);
            }
        });
        Button retoure =(Button)findViewById(R.id.retoureimc);
        retoure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),Afterverif.class);
                startActivity(intent);

            }
        });
    }
}
