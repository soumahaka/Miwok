package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
public class ColorsActivity extends AppCompatActivity {
    MediaPlayer colors_audio;

    private AudioManager colors_audioManager;

    AudioManager.OnAudioFocusChangeListener colors_OnAudioFocusChangeListener=new
            AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {


                    if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){

                        colors_audio.pause();
                        colors_audio.seekTo(0);


                    }

                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        colors_audio.start();
                    }

                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                }
            };

    MediaPlayer.OnCompletionListener onCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word);

        colors_audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> colorArrayList =new ArrayList<Word>();

        //Creating 10 instances of Word from the Word Class to store them in the array list above
        Word color_1= new Word(R.drawable.color_red,"red","weṭeṭṭi",R.raw.color_red);
        Word color_2= new Word(R.drawable.color_green,"green","chokokki",R.raw.color_green);
        Word color_3= new Word(R.drawable.color_brown,"brown","ṭakaakki",R.raw.color_brown);
        Word color_4= new Word(R.drawable.color_gray,"gray","ṭopoppi",R.raw.color_gray);
        Word color_5= new Word(R.drawable.color_black,"black","kululli",R.raw.color_black);
        Word color_6= new Word(R.drawable.color_white,"white","kelelli",R.raw.color_white);
        Word color_7= new Word(R.drawable.color_dusty_yellow,"dusty yellow","ṭopiisә",R.raw.color_dusty_yellow);
        Word color_8= new Word(R.drawable.color_mustard_yellow,"mustard yellowr","chiwiiṭә",R.raw.color_mustard_yellow);

        // Storing instances created from the Word Class in the array list
        // created above to store Word Class instances
        colorArrayList.add(color_1);
        colorArrayList.add(color_2);
        colorArrayList.add(color_3);
        colorArrayList.add(color_4);
        colorArrayList.add(color_5);
        colorArrayList.add(color_6);
        colorArrayList.add(color_7);
        colorArrayList.add(color_8);

        WordAdapter adapter= new WordAdapter(ColorsActivity.this, colorArrayList, R.color.category_colors);
        ListView list =(ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word=colorArrayList.get(position);
                releaseMediaPlayer();
                int result =colors_audioManager.requestAudioFocus(
                        colors_OnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    colors_audio = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());

                    colors_audio.start();

                    colors_audio.setOnCompletionListener(onCompletionListener);

                }

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (colors_audio != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            colors_audio.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            colors_audio = null;
            colors_audioManager.abandonAudioFocus(colors_OnAudioFocusChangeListener);
        }
    }
}