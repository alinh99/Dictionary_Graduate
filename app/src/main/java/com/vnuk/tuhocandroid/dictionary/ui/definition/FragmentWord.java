package com.vnuk.tuhocandroid.dictionary.ui.definition;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vnuk.tuhocandroid.dictionary.R;
import com.vnuk.tuhocandroid.dictionary.database.DataAccessAnhViet;

public class FragmentWord extends Fragment {
    private TextView tvTitle;
    DataAccessAnhViet mDataAccessAnhViet;
    public FragmentWord(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_word,container,false );
        tvTitle = view.findViewById ( R.id.tv_title );
        mDataAccessAnhViet = new DataAccessAnhViet ( getActivity () );
        final Intent intent = getActivity ().getIntent();
        String word = intent.getStringExtra("word");
        tvTitle.setText ( word );
        return view;
    }
}
