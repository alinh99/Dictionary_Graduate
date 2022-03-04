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

public class ViewPagerPracticeWordAdapter extends PagerAdapter {
    private Context mContextPracticeWord;
    private int mLayoutPracticeWord;
    private ArrayList<String> mListPracticeWord;
    View layoutPracticeWord;

    public ViewPagerPracticeWordAdapter(Context mContext, int mLayoutPracticeWord, ArrayList<String> mList) {
        this.mContextPracticeWord = mContext;
        this.mLayoutPracticeWord = mLayoutPracticeWord;
        this.mListPracticeWord = mList;
    }

    @Override
    public int getCount() {
        return mListPracticeWord.size ();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContextPracticeWord);
        layoutPracticeWord = inflater.inflate(mLayoutPracticeWord,container,false);
        TextView tvPracticeWord = layoutPracticeWord.findViewById ( R.id.tv_practice_word );
        String word = mListPracticeWord.get ( position );
        tvPracticeWord.setText ( word );
        container.addView ( layoutPracticeWord );
        return layoutPracticeWord;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
