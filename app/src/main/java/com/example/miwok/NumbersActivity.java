//  LE PARKAGE DANS LEQUEL JE TRAVAILLE ET SAUVEGARDE TOUT MON TRAVAIL
package com.example.miwok;

//IMPORTATION AUTOMATIQUE DES BIBLIOTHEQUES PAR ANDROID STUDIO

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


// LA CLASSE JAVA DE NUMBERS ACTIVITY GENENRÉE AUTOMATIQUEMENT LORS DU WIDZARD DE CREATION DE L'ACTIVITY
public class NumbersActivity extends AppCompatActivity {
    //OBJET PUBLIC MEDIA PLAYER POUR JOUER DES AUDIOS
    private MediaPlayer numbers_audio;

    //OBJET PUBLIC DE LA CLASSE AUDIO MANAGER POUR GERER LES AUDIOS SUR ANDROID
    private AudioManager numbers_audioManager;


    //OBJET AUDIO FOCUS_CHANGE_LISTENER UTILISÉ POUR ECOUTER DES REQUETES AUDIO DANS NOTRE APPLICATION
    AudioManager.OnAudioFocusChangeListener numbers_OnAudioFocusChangeListener=new
            AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {


                    //EN CAS DE PERTE TEMPORAIRE DU FOCUS PAR UNE AUTRE APPLICATION, METTRE PAUSE ET LE REMETTRE A ZERO
                    if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){

                        numbers_audio.pause();
                        numbers_audio.seekTo(0);


                    }

                    //EN CAS DE REPRISE DE L'AUDIO FOCUS, DEMARRER NOTRE AUDIO CAD
                    // LORQU'IL N'YA PLUS D'APPLI QUI JOUEN DE MUSIQUE
                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                        numbers_audio.start();
                    }

                    //EN CAS DE PERTE COMPLETE DU FOCUS, NETTOYER LA MEDIA PLAYER
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                        // Stop playback and clean up resources
                        releaseMediaPlayer();
                    }
                }
            };



    //OBJET PUBLIC ONCOMPLETION LISTENER POUR DETECTER LA FIN D'UN AUDIO DANS L'APPLICATION PUIS QUI NETTOIE LA MEDIA PLAYER
    //AVEC LA METHODE RELEASE DANS SA REDEFINITION
    MediaPlayer.OnCompletionListener onCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();

        }
    };

//**************************************************************************************************************//


    //CHARGEMENT DU FICHIER XML DE LA CLASSE NUMBERS ACTIVITY
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word);


        //CODE INDISPENSABLE POUR LE SERVICE AUDIO D'ANDROID POUR LA REQUETTE DU AUDIO FOCUS
        numbers_audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);


        // CREATION DE TABLEAU D'OBJETS DE LA CLASSE WORD LOCALEMENT PUBLIC AVEC LE MOT CLÉ FINAL
        final ArrayList<Word> numberArrayList = new ArrayList<Word>();

        //CREATION D'OBJETS DE LA CLASSE WORD
        Word number_1 = new Word(R.drawable.number_one, "One", "Lutti", R.raw.number_one);
        Word number_2 = new Word(R.drawable.number_two, "Two", "Otiiko", R.raw.number_two);
        Word number_3 = new Word(R.drawable.number_three, "Three", "Tolookosu", R.raw.number_three);
        Word number_4 = new Word(R.drawable.number_four, "Four", "Oyyisa", R.raw.number_four);
        Word number_5 = new Word(R.drawable.number_five, "Five", "Massokka", R.raw.number_five);
        Word number_6 = new Word(R.drawable.number_six, "Six", "Temmokka", R.raw.number_six);
        Word number_7 = new Word(R.drawable.number_seven, "Seven", "Kenekaku", R.raw.number_seven);
        Word number_8 = new Word(R.drawable.number_eight, "Eight", "Kawinta", R.raw.number_eight);
        Word number_9 = new Word(R.drawable.number_nine, "Nine", "Wo’e", R.raw.number_nine);
        Word number_10 = new Word(R.drawable.number_ten, "Ten", "Na’aacha", R.raw.number_ten);

        // AJOUT DES OBJETS CREÉS DE LA CLASSE WORD DANS LE TABLEAU D'OBJETS DE LA MEME CLASSE
        numberArrayList.add(number_1);
        numberArrayList.add(number_2);
        numberArrayList.add(number_3);
        numberArrayList.add(number_4);
        numberArrayList.add(number_5);
        numberArrayList.add(number_6);
        numberArrayList.add(number_7);
        numberArrayList.add(number_8);
        numberArrayList.add(number_9);
        numberArrayList.add(number_10);


        //CREATION D'OBJET DE LA CLASSE PERSONNALISÉE WORDADAPTER
        WordAdapter adapter = new WordAdapter(NumbersActivity.this, numberArrayList, R.color.category_numbers);

        //TRANSFERT DE LA LISTVIEW DEPUIS LE FICHIER XML QUI VA ETRE GÉRÉ PAR LE WORDAPTER
        ListView list = (ListView) findViewById(R.id.list);

        //ON ASSOCIE LA LISTVIEW AVEC L'OBJET WORDADAPTER;
        list.setAdapter(adapter);

        //ON ASSOCIE UN EVENEMENT DE CLICK À LA LISTVIEW
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //ON RECUPERE LA POSITION DES ELEMENTS SUR LESQUELS L'UTILISATEUR CLIQUE QU'ON A STOCKÉ DANS LE TABLEAU
                // // NUMBER ARRAYLIST QUI EST OBJET DE LA CLASSE WORD AVEC
                // LA METHODE GET RESSOURCE AUDIO QUI RETOURNE UN INT DE LA CLASSE WORD EN CREANT UN OBJET WORD
                //POUR LE STOCKER TEMPORAIREMENT
                Word word = numberArrayList.get(position);

                // ON NETTOIE A CHAQUE FOIS LA MEMOIRE DE NOTRE OBJET MEDIA PLAYER AVANT DE CREER DE L'AUDIO POUR S'ASSURER
                // QUE DEUX AUDIOS NE JOUERONT PAS ENSEMBLE. SUPER!!

                releaseMediaPlayer();

                //LANCER LA REQUETTE DU FOCUS AU CAS OU UNE APPLICATION AUDIO EST EN COURS ET LA SOCKER DANS LA VARIABLE
                // RESULT POUR FAIRE LE TESST TRADITIONNEL DES IF
                int result =numbers_audioManager.requestAudioFocus(
                        numbers_OnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                // A NOTE QUE L'ATTRIBU AUDIOFOCUS_GAIN EST UNIQUEMENT POUR LES REQUETES DE LONGUE DUREE D'AUDIO

                //SI L'APPLICATION AUDIO EN COURS ACCEPTE LA REQUETTE DE NOTRE FOCUS POUR
                // UN PETIT TEMPS ALORS ON JOUE NOTRE AUDIO
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    // CREATION D'AUDIO LORSQUE L'UTILISATEUR CLIQUE SUR UN ELEMENT AUDIO DANS LE TABLEAU NUMBER ARRAYLIST
                    // DE LA LISTVIEW ET LA POSITION RECUPERÉE NOUS DONNE LA RESSOURCE DE L'AUDIO AVEC LA METHODE GET AUDIO RESOURCES ID
                    // DE LA CLASSE WORD
                    numbers_audio = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());


                    // ON JOUE L'AUDIO
                    numbers_audio.start();

                    //ON NETTOIE LA MEMOIRE DE L'OBJET MEDIA PLAYER LORSQUE L'AUDIO SE TERMINE
                    // OU LORSQU'ON JOUE UN AUTRE AUDIO POUR EVITER SUBMERGER LA MEMOIRE ET NE PAS JOUER DEU AUDIO
                    // EN MEME TEMPS

                    numbers_audio.setOnCompletionListener(onCompletionListener);

                }

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    // METHODE QUI NETTOIE LA MEDIA PLAYER
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (numbers_audio != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            numbers_audio.release();

            //LORSQU'IL N'YA PLUS D'AUDIO, ON ABANDONNE COMPLETEMENT LE FOCUS
            numbers_audio = null;
            numbers_audioManager.abandonAudioFocus(numbers_OnAudioFocusChangeListener);
        }
    }
}
