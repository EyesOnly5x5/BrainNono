package de.eyesonly5x5.brainnono;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NonoGramActivity extends AppCompatActivity {
    Globals daten = Globals.getInstance();
    int[] BUTTON_IDS;
    int[] TEXT_IDS;

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonogram);

        BUTTON_IDS = daten.getButtonIDs();
        TEXT_IDS = daten.getTextIDs();
        TextView AusGtmp = findViewById(R.id.First);
        AusGtmp.setTextSize( daten.getMetrics().pxToDp((int)(AusGtmp.getTextSize()*daten.getMetrics().getFaktor())) );

        SetzeSpielfaeche();
        Button DasIstEs = findViewById( R.id.DasIstEs );
        DasIstEs.setTextSize( daten.getMetrics().pxToDp((int)(DasIstEs.getTextSize()*daten.getMetrics().getFaktor())) );

        DasIstEs.setOnClickListener(view -> {
            if( DasIstEs.getText().equals( getApplicationContext().getString( R.string.Mischa ) ) ){
                // TextView oView = findViewById( R.id.Kopf );
                daten.NonoGMischer();
                DasIstEs.setText( R.string.title9 );
                // Random Ran = new Random();
                // oView.setRotation( Ran.nextInt( 360 ) );
            } else {
                daten.DasIstEs();
                daten.getSoundBib( daten.getGewonnen() ).playSound();
                daten.setGewonnen( true );
                DasIstEs.setText(R.string.Mischa);
                daten.deleNonogram();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first
        daten.saveNonogram();
        if( daten.getGewonnen() ) daten.deleNonogram();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return( true );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        TextView oView = findViewById( R.id.Kopf );
        switch (item.getItemId()){
            case R.id.mischy:
                daten.deleNonogram();
                daten.NonoGMischer();
                Button button = findViewById( R.id.DasIstEs );
                button.setText( R.string.title9 );

                // daten.NonoGView();
                //Random Ran = new Random();
                //oView.setRotation( Ran.nextInt( 360 ) );
                return( true );
            case R.id.AnLeit:
                daten.Anleitung( this, R.string.AnleitNonoG );
                return( true );
        }

        return( super.onOptionsItemSelected( item) );
    }
/*
    private void SetzeSpielfaeche(){

        for(int i = 0, c = 0, r = 0; i < total; i++, c++){
            if( c == column ){ c = 0; r++; }

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            oView = new Button(this);
            if( c == 0 && r == 0 ) {
                oView.setId( 600 );
                oView.setTag( 600 );
                oView.setTextColor(oView.getContext().getResources().getColor(R.color.black));
                oView.setText( ""+(600) );
                oView.setTextSize( 12 );
                oView.setBackgroundColor( oView.getContext().getResources().getColor(R.color.xxx) );
                Button finalOView = oView;
                oView.setOnClickListener(view -> {
                    SetzeTextOben();
                });

 */
    private void SetzeSpielfaeche(){
        Button oView;
        int buttonSize;
        int textSize;
        int margin = 2;
        int column = daten.getAnzahl()+1;
        int row = daten.getAnzahl()+1;
        int total = row*column;
        GridLayout gridLayout = findViewById( R.id.butty );
        gridLayout.removeAllViews();

        textSize = daten.getMetrics().getMinPixels()/4;
        buttonSize = daten.getMetrics().getButtonSize( column, margin*2, textSize );
        gridLayout.setColumnCount( column );
        gridLayout.setRowCount( row );

        for(int i = 0, c = 0, r = 0; i < total; i++, c++){
            if( c == column ){ c = 0; r++; }

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();

            if( i==0 ){
                oView = new Button(this);
                oView.setGravity( Gravity.CENTER );
                oView.setId( R.id.Kopf );
                //param.height = 0;
                //param.width = 0;
                param.height = textSize;
                param.width = textSize;
            } else if( r==0 ){
                oView = new Button(this);
                oView.setTextColor( Color.parseColor("#FF000000" ) );
                oView.setBackgroundColor( Color.parseColor("#FFFFFFFF" ) );
                oView.setTextSize( getResources().getDimension(R.dimen.NonoTxt) );
                oView.setId( TEXT_IDS[c-1] );
                param.height = textSize;
                param.width = buttonSize;
            } else if( c==0 ){
                oView = new Button(this);
                oView.setTextColor( Color.parseColor("#FF000000" ) );
                oView.setBackgroundColor( Color.parseColor("#FFFFFFFF" ) );
                oView.setTextSize( getResources().getDimension(R.dimen.NonoTxt) );
                oView.setId( TEXT_IDS[(daten.getAnzahl()+r-1)] );
                //param.topMargin = 10;
                param.height = buttonSize;
                param.width = textSize;
            } else {
                oView = new Button(this);
                oView.setId( BUTTON_IDS[(c+(daten.getAnzahl()*(r-1)-1))] );
                oView.setTag( BUTTON_IDS[(c+(daten.getAnzahl()*(r-1)-1))] );
                oView.setTextColor(oView.getContext().getResources().getColor(R.color.black));
                oView.setText( "" );
                oView.setTextSize( getResources().getDimension(R.dimen.NonoTxt) );
                oView.setBackgroundColor(oView.getContext().getResources().getColor(R.color.DarkGreen));
                Button finalOView = oView;
                oView.setOnClickListener(view -> {
                    if (!daten.getGewonnen()) {
                        daten.toogleColor((Integer.parseInt(finalOView.getTag().toString()) - 1));
                    }
                });
                param.height = buttonSize;
                param.width = buttonSize;
                // param.topMargin = 10;
            }
            oView.setGravity( Gravity.CENTER );
            oView.setPadding( 0, 0, 0, 0);

            param.topMargin = margin;
            param.rightMargin = margin;
            param.leftMargin = margin;
            param.bottomMargin = margin;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);
            gridLayout.addView( oView, param );
            if( i==0 || r==0 || c==0 ) {
                daten.addText(oView);
            } else {
                daten.addButton(oView);
            }
        }
    }
}