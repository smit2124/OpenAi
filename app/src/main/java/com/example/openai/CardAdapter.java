package com.example.openai;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.util.List;

public class CardAdapter extends BaseAdapter {

    private  final Context mcontext;

    private final List<CardData> mcardDataList;

    public CardAdapter(Context context,List<CardData> cardDataList){
        this.mcontext = context;
        this.mcardDataList = cardDataList;

    }

    @Override
    public int getCount() {
        return mcardDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mcardDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.grid_item,null);

        }

        ImageView iconImageView = convertView.findViewById(R.id.icon_image);
        TextView titleTextView = convertView.findViewById(R.id.title_text);
        TextView discTextView = convertView.findViewById(R.id.disc_text);
        CardView cardView=convertView.findViewById(R.id.card_view);




        CardData cardData = mcardDataList.get(position);
        iconImageView.setImageResource(cardData.getIcon());
        titleTextView.setText(cardData.getTitle());
        discTextView.setText(cardData.getDisc());
        cardView.setCardBackgroundColor(Color.parseColor(cardData.getColour()));



        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent i = new Intent(mcontext, MainActivity2.class);
                        i.putExtra("card_id", cardData.getId());
                        mcontext.startActivity(i);
                    }
                });

        return convertView;
    }
}
