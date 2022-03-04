package com.vnuk.tuhocandroid.dictionary.ui.practice;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.vnuk.tuhocandroid.dictionary.R;
import com.vnuk.tuhocandroid.dictionary.activity.PracticeSentenceActivity;
import com.vnuk.tuhocandroid.dictionary.activity.PracticeWordActivity;

public class PracticeFragment extends Fragment {

    private PracticeViewModel mPracticeViewModel;
    ImageButton practiceWord, practiceSentence;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mPracticeViewModel =
                ViewModelProviders.of ( getActivity () ).get ( PracticeViewModel.class );
        View root = inflater.inflate ( R.layout.fragment_practice, container, false );
        practiceWord = root.findViewById ( R.id.practice_word );
        practiceWord.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity (), PracticeWordActivity.class );
                startActivity ( intent );
            }
        } );

        practiceSentence = root.findViewById ( R.id.practice_sentence );
        practiceSentence.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity (), PracticeSentenceActivity.class );
                startActivity ( intent );
            }
        } );
        return root;
    }
}
