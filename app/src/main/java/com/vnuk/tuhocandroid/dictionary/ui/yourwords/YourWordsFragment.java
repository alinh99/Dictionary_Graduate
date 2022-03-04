package com.vnuk.tuhocandroid.dictionary.ui.yourwords;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vnuk.tuhocandroid.dictionary.R;
import com.vnuk.tuhocandroid.dictionary.adapter.RecyclerViewAdapterYourWord;
import com.vnuk.tuhocandroid.dictionary.database.DataAccessAnhViet;
import com.vnuk.tuhocandroid.dictionary.oop.YourWord;

import java.util.ArrayList;

public class YourWordsFragment extends Fragment {

    private YourWordsViewModel mYourWordsViewModel;
    DataAccessAnhViet mDataAccessAnhViet;
    ArrayList<YourWord> yourWordsList;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter yourWordAdapter;
    RelativeLayout yourSearchedWord;
    Cursor mCursorYourWord;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mYourWordsViewModel =
                ViewModelProviders.of ( this ).get ( YourWordsViewModel.class );
        root = inflater.inflate ( R.layout.fragment_your_words, container, false );

        yourSearchedWord = root.findViewById ( R.id.your_searched_word );
        mDataAccessAnhViet = new DataAccessAnhViet ( getActivity () );
        mRecyclerView = root.findViewById ( R.id.recycler_view_your_word );
        mLayoutManager = new LinearLayoutManager ( getActivity () );

        mRecyclerView.setLayoutManager ( mLayoutManager );

        fetchYourWord();
        return root;
    }
    private void fetchYourWord() {
        yourWordsList = new ArrayList<> ();
        yourWordAdapter = new RecyclerViewAdapterYourWord (yourWordsList,getActivity () );
        mRecyclerView.setAdapter ( yourWordAdapter );
        mDataAccessAnhViet = DataAccessAnhViet.getInstance ( getActivity () );
        mDataAccessAnhViet.open ();
        YourWord yourWord;
        if (mDataAccessAnhViet != null){
            mCursorYourWord = mDataAccessAnhViet.getYourWord ();
            if (mCursorYourWord.moveToFirst ()){
                do {
                    yourWord = new YourWord ( mCursorYourWord.getString ( mCursorYourWord.getColumnIndex ( "word" ) ) );
                    yourWordsList.add ( yourWord );
                }while (mCursorYourWord.moveToNext ());
            }
            yourWordAdapter.notifyDataSetChanged ();
            if (yourWordAdapter.getItemCount ()==0){
                yourSearchedWord.setVisibility ( View.VISIBLE );
            }else {
                yourSearchedWord.setVisibility ( View.GONE );
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume ();
        fetchYourWord ();
    }
}
