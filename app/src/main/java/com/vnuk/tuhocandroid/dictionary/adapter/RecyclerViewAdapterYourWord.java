package com.vnuk.tuhocandroid.dictionary.adapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.vnuk.tuhocandroid.dictionary.activity.DefinitionActivity;
import com.vnuk.tuhocandroid.dictionary.R;
import com.vnuk.tuhocandroid.dictionary.oop.YourWord;
import java.util.ArrayList;

public class RecyclerViewAdapterYourWord extends RecyclerView.Adapter<RecyclerViewAdapterYourWord.YourWordViewHolder> {

    private ArrayList<YourWord> mYourWords;
    private Context mContext;

    public RecyclerViewAdapterYourWord(ArrayList<YourWord> yourWords, Context context) {
        mYourWords = yourWords;
        mContext = context;
    }
    
    @Override
    public RecyclerViewAdapterYourWord.YourWordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( parent.getContext ()).inflate(R.layout.your_word_item,parent,false);
        return new YourWordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterYourWord.YourWordViewHolder holder, int position) {
       holder.word.setText ( mYourWords.get ( position ).getWord () );
    }

    @Override
    public int getItemCount() {
        return mYourWords.size ();
    }

    public class YourWordViewHolder extends RecyclerView.ViewHolder {
        TextView word;
        
        public YourWordViewHolder(View itemView) {
            super ( itemView );
            word = itemView.findViewById ( R.id.word );
            itemView.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition ();
                    String text = mYourWords.get ( position ).getWord ();

                    Intent intent = new Intent (mContext, DefinitionActivity.class );
                    Bundle bundle = new Bundle ();
                    bundle.putString ( "word",text );
                    intent.putExtras ( bundle );
                    mContext.startActivity ( intent );
                }
            } );
        }
    }
}
