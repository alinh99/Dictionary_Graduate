package com.vnuk.tuhocandroid.dictionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.vnuk.tuhocandroid.dictionary.R;

import java.util.ArrayList;

public class ViewPagerPracticeSentenceAdapter extends PagerAdapter {
    private Context mContextPracticeSentence;
    private int mLayoutPracticeSentence;
    private ArrayList<String> mListPracticeSentence;
    View layoutPracticeSentence;

    public ViewPagerPracticeSentenceAdapter(Context mContextPracticeSentence, int mLayoutPracticeSentence, ArrayList<String> mListPracticeSentence) {
        this.mContextPracticeSentence = mContextPracticeSentence;
        this.mLayoutPracticeSentence = mLayoutPracticeSentence;
        this.mListPracticeSentence = mListPracticeSentence;
    }

    @Override
    public int getCount() {
        return mListPracticeSentence.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContextPracticeSentence);
        layoutPracticeSentence = inflater.inflate(mLayoutPracticeSentence,container,false);
        TextView tvPracticeSentence = layoutPracticeSentence.findViewById( R.id.tv_practice_sentence);
        String sentence = mListPracticeSentence.get(position);
        tvPracticeSentence.setText(sentence);
        container.addView(layoutPracticeSentence);
        return layoutPracticeSentence;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
