package com.example.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {
    private int colorResourceId;


    public WordAdapter(Context context,ArrayList<Word> wordArrayList,int colorResourceId) {
        super(context, 0, wordArrayList);
        this.colorResourceId=colorResourceId;

    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        // ON APPELLE ET ON POSITIONNE NOTRE LISTVIEW QUI SERA GERÉ PAR L ADAPTEUR
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);



        //ON POSITIONNE CHAQUE ELEMENT DE LA LISTVIEW DANS LA LISTVIEW SELON LEUR IDs

        TextView miwork_translation = (TextView) listItemView.findViewById(R.id.miwok_translation);
        miwork_translation.setText(currentWord.getMiwok_translation());

        TextView english_translation = (TextView) listItemView.findViewById(R.id.english_translation);
        english_translation.setText(currentWord.getEnglish_translation());

        ImageView image_illustration=(ImageView)listItemView.findViewById(R.id.image_illustration);
        if(currentWord.hasImage())
            image_illustration.setImageResource(currentWord.getImageResourceId());
        else
            image_illustration.setVisibility(View.GONE);

        // ON APPLIQUE DES COULEURS AU TEXT LAYOUT DE LA LISTVIEW EN METTANT CETTE PARTIE DU CODE
        // DANS LE CONSTRUCTEUR DU WORDADAPTEUR

        View text_holder=listItemView.findViewById(R.id.text_holder);
        int color= ContextCompat.getColor(getContext(),colorResourceId);
        text_holder.setBackgroundColor(color);
        return listItemView;
    }

}

