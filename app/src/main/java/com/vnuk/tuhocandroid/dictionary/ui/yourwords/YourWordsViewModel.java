package com.vnuk.tuhocandroid.dictionary.ui.yourwords;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class YourWordsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public YourWordsViewModel() {
        mText = new MutableLiveData<> ();
        mText.setValue ( "This is slideshow fragment" );
    }

    public LiveData<String> getText() {
        return mText;
    }
}
