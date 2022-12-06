package com.example.app_body_mass_index;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Vector;
public class Courbe extends AppCompatActivity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courbe_layout);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        final DatabaseAdapteur datasource = new DatabaseAdapteur(this);
        datasource.open();
        final SharedPreferences sharedpreferences = getSharedPreferences(USER_SERVICE, Context.MODE_PRIVATE);
        final String user =sharedpreferences.getString("username","1");
        String ch="";
        Vector<String[]> vecti=datasource.getAllImcstring(user);
        String[] datahrizantale=new String[vecti.size()];
        String[] datavertivale=new String[vecti.size()];
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        for (int i=0; i<vecti.size();i++)
        {
            String[] data=vecti.get(i);
            datahrizantale[i]=data[5];
            datavertivale[i]=data[2];
        }
        staticLabelsFormatter.setHorizontalLabels(datavertivale);
        staticLabelsFormatter.setVerticalLabels(new String[]{"0","18.5","24.9","25","25.9","35","+35"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        DataPoint[] dpc=new DataPoint[vecti.size()];
        for (int i=0; i<vecti.size();i++)
        {
            String[] data=vecti.get(i);
            double imce = Double.parseDouble(data[5]);
            if(imce==0)
            {
                dpc[i]=new DataPoint(i,0);
            }
            else if(imce<18.5)
            {
                dpc[i]=new DataPoint(i,0.25);
            }
            else if(imce==18.5)
            {
                dpc[i]=new DataPoint(i,0.5);
            }
            else if(imce>18.5 && imce<24.9)
            {
                dpc[i]=new DataPoint(i,0.75);
            }
            else if(imce==24.9)
            {
                dpc[i]=new DataPoint(i,1);
            }
            else if(imce>24.9 && imce<25)
            {
                dpc[i]=new DataPoint(i,1.25);
            }
            else if(imce==25)
            {
                dpc[i]=new DataPoint(i,1.5);
            }
            else if(imce>25 && imce<25.9)
            {
                dpc[i]=new DataPoint(i,1.75);
            }
            else if(imce==25.9)
            {
                dpc[i]=new DataPoint(i,2);
            }
            else if(imce>25.9 && imce<35)
            {
                dpc[i]=new DataPoint(i,2.25);
            }
            else if(imce==35)
            {
                dpc[i]=new DataPoint(i,2.5);
            }
            else
            {
                dpc[i]=new DataPoint(i,3);
            }
        }
        LineGraphSeries series = new LineGraphSeries<>(dpc);

        graph.addSeries(series);
        Button ret=(Button)findViewById(R.id.retourecurbe);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Afterverif.class);
                startActivity(intent);
            }
        });
    }
}

