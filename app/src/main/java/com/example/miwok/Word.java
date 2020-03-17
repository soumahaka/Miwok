package com.example.miwok;

public class Word {

    private String english_translation;
    private String miwok_translation;
    private static final int IMAGE_GONE = -1;
    private int imageResourceId= IMAGE_GONE;
    private int audioResourceId;


    public Word(String english_translation, String miwok_translation,int audioResourceId){
        this.english_translation=english_translation;
        this.miwok_translation=miwok_translation;
        this.audioResourceId=audioResourceId;
    }

    public int getAudioResourceId() {
        return audioResourceId;
    }

    public Word(int imageResourceId, String english_translation, String miwok_translation, int audioResourceId){
        this.audioResourceId=audioResourceId;
        this.english_translation=english_translation;
        this.miwok_translation=miwok_translation;
        this.imageResourceId=imageResourceId;
    }


    public String getEnglish_translation() {

        return english_translation;
    }

    public String getMiwok_translation() {

        return miwok_translation;
    }
    public int getImageResourceId() {
        return imageResourceId;
    }
    public boolean hasImage(){
        return imageResourceId!=IMAGE_GONE;
    }

    @Override
    public String toString() {
        return "Word{" +
                "english_translation='" + english_translation + '\'' +
                ", miwok_translation='" + miwok_translation + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", audioResourceId=" + audioResourceId +
                '}';
    }
}
