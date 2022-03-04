package com.vnuk.tuhocandroid.dictionary.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.vnuk.tuhocandroid.dictionary.R;
import com.vnuk.tuhocandroid.dictionary.database.DataAccessAnhViet;
import com.vnuk.tuhocandroid.dictionary.ui.definition.FragmentDefinition;
import com.vnuk.tuhocandroid.dictionary.ui.definition.FragmentWord;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DefinitionActivity extends AppCompatActivity {
    private ViewPager viewPager;
    Toolbar toolbar;
    TextToSpeech mTextToSpeech;
    ImageButton mImageButton;
    String englishWord;
    DataAccessAnhViet mDataAccessAnhViet;
    Cursor mCursor = null;
    TabLayout tabLayout;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_definition );

        bundle = getIntent ().getExtras ();
        englishWord = bundle.getString ( "word" );

        anhXa();

        mDataAccessAnhViet = new DataAccessAnhViet ( this );
        mDataAccessAnhViet.open ();
        mCursor = mDataAccessAnhViet.getMeaning ( englishWord );
        mDataAccessAnhViet.insertYourWord ( englishWord );
        
        setSupportActionBar ( toolbar );
        getSupportActionBar ().setTitle ( englishWord );

        mImageButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                mTextToSpeech = new TextToSpeech ( DefinitionActivity.this, new TextToSpeech.OnInitListener () {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS){
                            int speak = mTextToSpeech.setLanguage ( Locale.US );

                            if (speak==TextToSpeech.LANG_MISSING_DATA || speak==TextToSpeech.LANG_NOT_SUPPORTED){
                                Log.e ( "error", "This language is not supported" );
                            }
                            else {
                                Toast.makeText ( DefinitionActivity.this, englishWord, Toast.LENGTH_SHORT ).show ();
                                mTextToSpeech.speak ( englishWord,TextToSpeech.QUEUE_FLUSH,null );
//                                speakOut();
                            }

                        }
                        else {
                            Log.e ( "error", "Failed to speak" );
                        }
                    }
                } );
            }
        } );

        toolbar.setNavigationIcon ( R.drawable.ic_arrow_back );
        
        if (viewPager != null){
            setUpViewPager ( viewPager );
        }
        
        tabLayout.setupWithViewPager ( viewPager );

        tabLayout.addOnTabSelectedListener ( new TabLayout.OnTabSelectedListener () {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem ( tab.getPosition () );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );
    }

    private void speakOut() {
        englishWord = bundle.getString ( "word" );
        Toast.makeText ( this, englishWord + "", Toast.LENGTH_SHORT ).show ();
        int speechStatus=mTextToSpeech.speak(englishWord + "", TextToSpeech.QUEUE_FLUSH, null);
        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<> ();
        private final List<String> mFragmentTitleList = new ArrayList<> ();

        ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        void addFragment(Fragment fragment, String title){
            mFragmentList.add ( fragment );
            mFragmentTitleList.add ( title );
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get ( position );
        }

        @Override
        public int getCount() {
            return mFragmentList.size ();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get ( position );
        }
        
    }


    private void setUpViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter ( getSupportFragmentManager () );
        adapter.addFragment ( new FragmentWord (), "Word" );
        adapter.addFragment ( new FragmentDefinition (), "Definition" );
        viewPager.setAdapter ( adapter );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId () == android.R.id.home){
            onBackPressed ();
        }
        return super.onOptionsItemSelected ( item );
    }

    private void anhXa() {
        toolbar = findViewById ( R.id.toolbar );
        viewPager = findViewById ( R.id.tab_viewpager );
        mImageButton = findViewById ( R.id.btnSpeak );
        tabLayout = findViewById ( R.id.tabLayout );
    }
}
