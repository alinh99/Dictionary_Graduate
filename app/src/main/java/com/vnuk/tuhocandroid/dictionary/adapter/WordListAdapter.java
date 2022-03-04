package com.vnuk.tuhocandroid.dictionary.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.vnuk.tuhocandroid.dictionary.activity.DefinitionActivity;
import com.vnuk.tuhocandroid.dictionary.R;

import java.util.ArrayList;

public class WordListAdapter extends BaseAdapter implements Filterable{
    private Context mContext;
    private ArrayList<String> mWordItems;
    public WordListAdapter(Context context, ArrayList<String> wordItems) {
        mContext = context;
        mWordItems = wordItems;
    }


    @Override
    public int getCount() {
        return mWordItems.size ();
    }

    @Override
    public Object getItem(int i) {
        return mWordItems.get ( i );
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void add(String number) {
        mWordItems.add ( number );
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter () {
            @SuppressWarnings ( "unchecked" )
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults ();
                ArrayList<String> filteredList = new ArrayList<> ();
                if (charSequence == null || charSequence.length ()==0){
                    results.values = mWordItems;
                    results.count = mWordItems.size ();
                }else {
                    for (int i = 0; i < mWordItems.size (); i++){
                        String data= mWordItems.get ( i );
                        if (data.toLowerCase ().contains ( charSequence.toString () )){
                            filteredList.add ( data );
                        }
                    }
                    results.values = filteredList;
                    results.count = filteredList.size ();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mWordItems = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged ();
            }
        };
        return filter;
    }


    private class ViewHolder{
        TextView tvWord;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
            view=layoutInflater.inflate (R.layout.word_list,null );
            // anh xa
            viewHolder = new ViewHolder ();
            viewHolder.tvWord = view.findViewById ( R.id.tv_word );

            view.setTag ( viewHolder );
        }else {
            viewHolder = (ViewHolder) view.getTag ();
        }

        //gan gia tri
        viewHolder.tvWord.setText ( mWordItems.get ( i ) );

        viewHolder.tvWord.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext (), DefinitionActivity.class );
                intent.putExtra ( "word", "" + getItem ( i ) );
                mContext.startActivity ( intent );
            }
        } );
        return view;
    }
}
