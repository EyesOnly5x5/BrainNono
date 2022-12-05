package io.github.eyesonly5x5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.eyesonly5x5.brainnono.R;

public class MainActivity extends AppCompatActivity {

    Globals daten = Globals.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main );
        daten.setMyContext( this );
        daten.setMetrics(getResources());

        TextView AusG = findViewById(R.id.Kopf);
        AusG.setText(getString(R.string.title1));
        AusG.setTextSize( daten.getMetrics().pxToDp((int)(AusG.getTextSize()*daten.getMetrics().getFaktor())) );
        AusG = findViewById(R.id.NonoGram);
        AusG.setTextSize( daten.getMetrics().pxToDp((int)(AusG.getTextSize()*daten.getMetrics().getFaktor())) );
        daten.setSoundBib(true,new Globals.SoundBib( true,this));
        daten.setSoundBib(false,new Globals.SoundBib( false,this));

        Button NonoG5 = findViewById(R.id.NonoG5);
        NonoG5.setTextSize( daten.getMetrics().pxToDp((int)(NonoG5.getTextSize()*daten.getMetrics().getFaktor())) );
        Button NonoG6 = findViewById(R.id.NonoG6);
        NonoG6.setTextSize( daten.getMetrics().pxToDp((int)(NonoG6.getTextSize()*daten.getMetrics().getFaktor())) );
        Button NonoG7 = findViewById(R.id.NonoG7);
        NonoG7.setTextSize( daten.getMetrics().pxToDp((int)(NonoG7.getTextSize()*daten.getMetrics().getFaktor())) );
        Button NonoG8 = findViewById(R.id.NonoG8);
        NonoG8.setTextSize( daten.getMetrics().pxToDp((int)(NonoG8.getTextSize()*daten.getMetrics().getFaktor())) );
        Button NonoG9 = findViewById(R.id.NonoG9);
        NonoG9.setTextSize( daten.getMetrics().pxToDp((int)(NonoG9.getTextSize()*daten.getMetrics().getFaktor())) );
        Button NonoG10 = findViewById(R.id.NonoG10);
        NonoG10.setTextSize( daten.getMetrics().pxToDp((int)(NonoG10.getTextSize()*daten.getMetrics().getFaktor())) );

        NonoG5.setOnClickListener(view -> {
            NonoG5.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_nonogram);
            daten.setWoMischen( "NonoG5" );
            daten.setGameData(5);
            startActivity(new Intent(getApplicationContext(),NonoGramActivity.class));
            NonoG5.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        NonoG6.setOnClickListener(view -> {
            NonoG6.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_nonogram);
            daten.setWoMischen( "NonoG6" );
            daten.setGameData(6);
            startActivity(new Intent(getApplicationContext(),NonoGramActivity.class));
            NonoG6.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        NonoG7.setOnClickListener(view -> {
            NonoG7.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_nonogram);
            daten.setWoMischen( "NonoG7" );
            daten.setGameData(7);
            startActivity(new Intent(getApplicationContext(),NonoGramActivity.class));
            NonoG7.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        NonoG8.setOnClickListener(view -> {
            NonoG8.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_nonogram);
            daten.setWoMischen( "NonoG8" );
            daten.setGameData(8);
            startActivity(new Intent(getApplicationContext(),NonoGramActivity.class));
            NonoG8.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        NonoG9.setOnClickListener(view -> {
            NonoG9.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_nonogram);
            daten.setWoMischen( "NonoG9" );
            daten.setGameData(9);
            startActivity(new Intent(getApplicationContext(),NonoGramActivity.class));
            NonoG9.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        NonoG10.setOnClickListener(view -> {
            NonoG10.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_nonogram);
            daten.setWoMischen( "NonoG10" );
            daten.setGameData(10);
            startActivity(new Intent(getApplicationContext(),NonoGramActivity.class));
            NonoG10.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
    }
}