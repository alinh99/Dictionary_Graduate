package com.vnuk.tuhocandroid.dictionary.ui.definition;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.vnuk.tuhocandroid.dictionary.R;
import com.vnuk.tuhocandroid.dictionary.database.DataAccessAnhViet;

public class FragmentDefinition extends Fragment {
    TextView tvDefinition;
    public FragmentDefinition(){
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_definition,container,false );
        tvDefinition = view.findViewById ( R.id.tv_definition );
        final Intent intent = getActivity ().getIntent();
        String word = intent.getStringExtra("word");
        DataAccessAnhViet dbAccess = DataAccessAnhViet.getInstance(getContext () );
        dbAccess.open();
        String definition = dbAccess.getDefinition(word);
        dbAccess.close();
        //Hiển thị trên textView
        tvDefinition.setText( Html.fromHtml(definition));
        return view;
    }
}
