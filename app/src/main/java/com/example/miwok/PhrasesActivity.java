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

public class PhrasesActivity extends AppCompatActivity {
    MediaPlayer phrases_audio;

    private AudioManager phrases_audioManager;

    AudioManager.OnAudioFocusChangeListener phrases_OnAudioFocusChangeListener=new
            AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {


                    if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){

                        phrases_audio.pause();
                        phrases_audio.seekTo(0);


                    }

                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        phrases_audio.start();
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

        phrases_audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> phraseArrayList =new ArrayList<Word>();

        Word phrase_1= new Word("Where are you going?","minto wuksus"
                ,R.raw.phrase_where_are_you_going);
        Word phrase_2= new Word("What is your name?","tinnә oyaase'nә"
                ,R.raw.phrase_what_is_your_name);
        Word phrase_3= new Word("My name is...","oyaaset...",R.raw.phrase_my_name_is);
        Word phrase_4= new Word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling);
        Word phrase_5= new Word("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good);
        Word phrase_6= new Word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming);
        Word phrase_7= new Word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming);
        Word phrase_8= new Word("I’m coming.","әәnәm",R.raw.phrase_im_coming);
        Word phrase_9= new Word("Let’s go.","yoowutis",R.raw.phrase_lets_go);
        Word phrase_10= new Word("Come here.","әnni'nem",R.raw.phrase_come_here);

        phraseArrayList.add(phrase_1);
        phraseArrayList.add(phrase_2);
        phraseArrayList.add(phrase_3);
        phraseArrayList.add(phrase_4);
        phraseArrayList.add(phrase_5);
        phraseArrayList.add(phrase_6);
        phraseArrayList.add(phrase_7);
        phraseArrayList.add(phrase_8);
        phraseArrayList.add(phrase_9);
        phraseArrayList.add(phrase_10);

        WordAdapter adapter= new WordAdapter(PhrasesActivity.this, phraseArrayList,R.color.category_phrases);
        ListView list =(ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word1=phraseArrayList.get(position);

                releaseMediaPlayer();


                int result =phrases_audioManager.requestAudioFocus(
                        phrases_OnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    phrases_audio = MediaPlayer.create(PhrasesActivity.this, word1.getAudioResourceId());

                    phrases_audio.start();

                    phrases_audio.setOnCompletionListener(onCompletionListener);

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
        if (phrases_audio != null) {

            phrases_audio.release();

            phrases_audio = null;
            phrases_audioManager.abandonAudioFocus(phrases_OnAudioFocusChangeListener);
        }
    }
}
