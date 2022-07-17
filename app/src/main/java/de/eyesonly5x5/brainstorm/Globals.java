package de.eyesonly5x5.brainstorm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Globals  extends ListActivity {
    @SuppressLint("StaticFieldLeak")
    private static final Globals instance = new Globals();
    private static MyDisplay metrics;

    // Beispiele für Daten...
    private byte[][] Tast = new byte[100][9];
    private int maxFelder = 0;
    private final boolean[] Flg = new boolean[100];
    List<Integer> Color = new ArrayList<>();
    int[] BUTTON_IDS;
    int[] TEXT_IDS;
    private TextView Ausgabe;
    List<Button> buttons = new ArrayList<>();
    List<TextView> TextV = new ArrayList<>();
    private int Zuege = 0;
    private int Anzahl = 0;
    private int Activity=-1;
    private Context myContext;
    private Resources myRes;
    private boolean gewonnen = true;
    private SoundBib SoundW;
    private SoundBib SoundF;
    private int Buty = 90;
    private int Buty2 = 90;
    private int[][] NonoG;
    private boolean dashEnde;
    private int istGedrueckt = 0;
    private int MemoryPic = 0;
    private ArrayList<Integer> MemoryPics = new ArrayList<>();
    private String woMischen = "Zauber";
    private boolean geMischt = false;

    // private Globals() { }

    public static Globals getInstance() {
        return instance;
    }

    public static void setMetrics( Resources hier ){
        metrics = new MyDisplay( hier );
    }
    public static MyDisplay getMetrics( ){
        return( metrics );
    }

    public void setAusgabe(TextView wert) {
        Ausgabe = wert;
    }

    public SoundBib getSoundBib(boolean s) {
        return( (s)?SoundW:SoundF );
    }
    public void setSoundBib(boolean s, SoundBib wert) {
        if( s ) SoundW = wert;
        else SoundF = wert;
    }

    public boolean getGewonnen() {
        return gewonnen;
    }
    public void setGewonnen( boolean wert) {
        gewonnen = wert;
    }

    public void setActivity(int act){
        Activity = act;
    }
    public void setMyContext( MainActivity c) {
        myContext = c;
        myRes = myContext.getResources();
    }

    public void addButton(Button button) {
        buttons.add(button);
    }
    public void addText(TextView Text) { TextV.add(Text); }

    public int getAnzahl(){ return Anzahl; }

    public void setWoMischen( String wert ){
        woMischen = wert;
        metrics.setWoMischen(wert);
    }
    public String getWoMischen( ){ return( woMischen ); }
    public List<Integer> getColor(){ return( Color ); }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void Mischer() {
        int id, id1, id2, tmp;
        Random r = new Random();
        Zuege = 0;
        gewonnen = false;
        Ausgabe.setText("Züge: " + Zuege);
        NonoG = new int[2][Anzahl*Anzahl];
        geMischt = true;
        for (id = 0; id < maxFelder; id++) {
            Button button = buttons.get(id);
            Flg[id] = true;
            button.setBackgroundColor(myRes.getColor(R.color.DarkGreen));
            button.setTextColor(myRes.getColor(R.color.white));
        }
        for (int i = 0; i < 25; i++) {
            id = r.nextInt(maxFelder);
            for (int idS : Tast[id]) if (idS > 0) changeFlg(idS - 1);
        }
    }

    public void NonoGMischer(){
        Random r = new Random();
        StringBuilder s;
        for( int i = 0; i<NonoG[0].length; i++ ){
            NonoG[0][i] = r.nextInt( 2 );
            NonoG[1][i] = 0;
        }
        loadNonogram();
        for( int i = 0; i<Anzahl; i++ ){
            List<Integer> z = new ArrayList<>();
            z.add(0);
            for( int j = 0; j<Anzahl; j++ ){
                if( NonoG[0][i+(j*Anzahl)] == 1 ){
                    z.set( z.size()-1, z.get( z.size()-1 )+1 );
                } else if( z.get( z.size()-1 ) != 0 ) z.add(0);
            }
            s = new StringBuilder();
            for( int j = 0; j<z.size(); j++ ){
                if( z.get(j) != 0 ) s.append("").append(z.get(j)).append("\n");
            }
            TextV.get(i+1).setText( (s.length()!=0)?s.substring(0,s.length()-1):"0" );
        }
        for( int i = 0; i<Anzahl; i++ ){
            List<Integer> z = new ArrayList<>();
            z.add(0);
            for( int j = 0; j<Anzahl; j++ ){
                if( NonoG[0][j+(i*Anzahl)] == 1 ){
                    z.set( z.size()-1, z.get( z.size()-1 )+1 );
                } else if( z.get( z.size()-1 ) != 0 ) z.add(0);
            }
            s = new StringBuilder();
            for( int j = 0; j<z.size(); j++ ){
                if( z.get(j) != 0 ) s.append("").append(z.get(j)).append("-");
            }
            TextV.get(i+Anzahl+1).setText( (s.length()!=0)?s.substring(0,s.length()-1):"0" );
        }
        for( int i = 0; i<NonoG[0].length; i++ ){
            Button button = buttons.get(i);
            button.setBackgroundColor(myRes.getColor((NonoG[1][i] == 1)?R.color.DarkRed:R.color.DarkGreen));
            button.setText( "" );
        }
        gewonnen = false;
    }

    /*
    @SuppressLint("SetTextI18n")
    public void NonoGView(){
        for( int i = 0; i<NonoG[0].length; i++ ){
            Button button = buttons.get(i);
            button.setText( "" + NonoG[0][i] );
        }
    }
    */

    public void DasIstEs(){
        gewonnen = true;
        for( int i = 0; i<NonoG[0].length; i++ ){
            Button button = buttons.get(i);
            if( NonoG[0][i] == NonoG[1][i] ){
                button.setBackgroundColor( myRes.getColor( (NonoG[0][i]==0)? R.color.Richtig0: R.color.Richtig1 ) );
                button.setTextColor(myRes.getColor(R.color.black));
            } else {
                button.setTextColor(myRes.getColor(R.color.white));
                gewonnen = false;
            }
            button.setText( (NonoG[0][i]==0)? "-": "*" );
        }
    }

    public void saveNonogram( ){
        StringBuilder data = new StringBuilder();
        for( int i = 0; i<NonoG[0].length; i++ ){
            data.append("").append(NonoG[0][i]).append(",");
            data.append("").append(NonoG[1][i]).append("\n");
        }
        speichern( "NonoG"+Anzahl+".txt", data.toString());
    }

    public void loadNonogram(){
        String[] data;
        boolean flg = false;
        int[][] tmp = new int[2][Anzahl*Anzahl];

        data = laden( "NonoG"+Anzahl+".txt", "0,0" );
        for( int i = 0; i<data.length; i++ ) {
            String[] x = data[i].split(",");
            tmp[0][i] = Integer.parseInt( x[0] );
            if( tmp[0][i] > 0 ) flg = true;
            tmp[1][i] = Integer.parseInt( x[1] );
        }
        for( int i = 0; (i<(NonoG[0].length)) && flg; i++ ){
            NonoG[0][i] = tmp[0][i];
            NonoG[1][i] = tmp[1][i];
        }
    }

    public void deleNonogram(){
        loeschen( "NonoG"+Anzahl+".txt" );
    }

// /data/user/0/de.eyesonly5x5.brainstorm/files/Solitar.txt
    private void speichern( String filename, String data ){
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = myContext.openFileOutput(filename, myContext.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loeschen( String filename ){
        File file = new File( myContext.getFilesDir(), filename );
        file.delete();
    }

    private String[] laden( String filename, String vorlage ){
        String[] ret = new String[Anzahl*Anzahl];
        int i;
        for( i = 0; i < ret.length; i++ ) ret[i] = vorlage;
        i = 0;
        try {
            File in = new File( myContext.getFilesDir(), filename );
            Scanner scanner = new Scanner(in);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                ret[i++] = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return( ret );
    }

    @SuppressLint("WrongConstant")
    private int anzahlButtons(){
        int AnzBut = (((metrics.getMaxPixels()) / (int)(this.Buty*metrics.getFaktor()))-3);
        // int dimenX = (int) metrics.getMinPixels() / (column+1);
        if( AnzBut > 11 ) AnzBut = 11;
        AnzBut *= Anzahl;
        return( AnzBut );
    }

    public int[] getButtonIDs() {
        int wer = getWerWoWas();
        int Buttys = (wer==0)?9:(wer==1)?16:(wer==2)?25:(wer==3)?anzahlButtons():(wer==4)?100:(wer>=5)?Anzahl*Anzahl:0;
        int[] ret = new int[Buttys];

        if( wer < 3 ){
            for (int i = 0; i < ret.length; i++) {
                ret[i] = myRes.getIdentifier("b"+(i+1), "id", myContext.getPackageName());
            }
        } else {
            for (int i = 0; i < ret.length; i++) {
                ret[i] = (i + 1);
            }
            if( wer == 5 ){
                NonoG = new int[2][Anzahl*Anzahl];
            }
        }
        BUTTON_IDS = ret;
        maxFelder = BUTTON_IDS.length;
        return (BUTTON_IDS);
    }

    public int[] getTextIDs() {
        int[] ret = new int[Anzahl*2];

        for (int i = 0; i < ret.length; i++) {
            ret[i] = (300 + i);
        }
        TEXT_IDS = ret;
        return (TEXT_IDS);
    }

    @SuppressLint("NonConstantResourceId")
    private int getWerWoWas(){
        int ret = -1;
        if( Activity == R.layout.activity_nonogram ){
            ret = 5;
        }
        return( ret );
    }

    public void setGameData( int anzahl ) {
        Zuege = 0;
        gewonnen = true;
        buttons = null;
        buttons = new ArrayList<>();
        TextV = null;
        TextV = new ArrayList<>();
        Anzahl = anzahl;
        istGedrueckt = 0;
        dashEnde = false;
    }

    @SuppressLint("ResourceAsColor")
    public void changeFlg(int id) {
        Button button = buttons.get(id);
        if (Flg[id]) {
            button.setBackgroundColor(myRes.getColor(R.color.DarkRed));
            button.setTextColor(myRes.getColor(R.color.Gelb));
        } else {
            button.setBackgroundColor(myRes.getColor(R.color.DarkGreen));
            button.setTextColor(myRes.getColor(R.color.white));
        }
        Flg[id] = !Flg[id];
    }


    public void toogleColor( int id ){
        Button button = buttons.get(id);
        NonoG[1][id] = (NonoG[1][id] == 0)?1:0;
        button.setBackgroundColor(myRes.getColor((NonoG[1][id] == 1)?R.color.DarkRed:R.color.DarkGreen));
    }

    static class SoundBib extends AppCompatActivity {
        private SoundPool soundPool;
        List<Integer> sound = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // setContentView(R.layout.activity_main);
        }

        public SoundBib(boolean s, Context context) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();

            if( s ) {
                sound.add(soundPool.load(context, R.raw.won1, 1));
                sound.add(soundPool.load(context, R.raw.won2, 1));
                sound.add(soundPool.load(context, R.raw.won3, 1));
                sound.add(soundPool.load(context, R.raw.won4, 1));
                sound.add(soundPool.load(context, R.raw.won5, 1));
            } else {
                sound.add(soundPool.load(context, R.raw.fail1, 1));
                sound.add(soundPool.load(context, R.raw.fail2, 1));
                sound.add(soundPool.load(context, R.raw.fail3, 1));
                sound.add(soundPool.load(context, R.raw.fail4, 1));
            }
        }

        // When users click on the button "Gun"
        public void playSound() {
            soundPool.autoPause();
            Random r = new Random();
            int id = r.nextInt(sound.size());
            soundPool.play(sound.get(id), 0.25F, 0.25F, 0, 0, 1);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            soundPool.release();
            soundPool = null;
        }
    }

    public void Anleitung( Context dasDA, int Wat ) {
        Dialog customDialog = new Dialog( dasDA );
        customDialog.setContentView(R.layout.anleitung);
        TextView oView = customDialog.findViewById( R.id.Anleitung );
        String str = myRes.getString( Wat, myRes.getString( R.string.Wunschliste ), myRes.getString( R.string.Losung ) );
        oView.setText( str );
        Button bView = customDialog.findViewById( R.id.Warte );
        bView.setOnClickListener(view -> customDialog.dismiss());
        customDialog.setCancelable(false);
        customDialog.show();
    }
}