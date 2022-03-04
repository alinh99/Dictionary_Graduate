package com.vnuk.tuhocandroid.dictionary.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.vnuk.tuhocandroid.dictionary.R;
import com.vnuk.tuhocandroid.dictionary.adapter.ViewPagerPracticeWordAdapter;
import com.vnuk.tuhocandroid.dictionary.database.DataAccessAnhViet;

import java.util.ArrayList;
import java.util.Locale;

public class PracticeWordActivity extends AppCompatActivity {
    public String s;
    ViewPager vpVocabulary;
    ViewPagerPracticeWordAdapter adapter;
    ImageButton arrowBack;
    ArrayList<String> mlist;
    ImageButton ibSpeak, ibVoice;
    int mCurrentPage;
    TextView tvResult;
    TextToSpeech ttsTextToSpeech;
    ArrayList<String> anhViet;
    TextView tvScore;
    DataAccessAnhViet dataAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_practice_word );
        anhXa();
        openAndCloseDatabase ();
        adapter = new ViewPagerPracticeWordAdapter ( PracticeWordActivity.this, R.layout.item_practice_word, anhViet );
        vpVocabulary.setAdapter(adapter);
        vpVocabulary.setCurrentItem(mCurrentPage);

        // view pager để lướt ngang
        vpVocabulary.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                tvScore.setText ( "0" );
                tvResult.setText ( "Your Word" );
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // tính điểm từ nói được
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 456) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            s = result.get (0);
            adapter.notifyDataSetChanged();
            String word = anhViet.get ( mCurrentPage );
            tvResult.setText ( s );
            s = s.replace ( " \" ", "" );
            if (s.equalsIgnoreCase ( word )){
                tvScore.setText ( "100" );
            }else {
                tvScore.setText ( "0" );
            }
        }
    }

    // quay lại trang trước đó
    public void clickOnBackWord(View view){
        onBackPressed ();
    }

    // speak to text - kết nối với điện thoại để sử dụng
    public void clickOnSpeakWord(View view){
        listenOut ();
    }

    // text to speak
    public void clickOnSoundWord(View view){
        ttsTextToSpeech = new TextToSpeech ( PracticeWordActivity.this, new TextToSpeech.OnInitListener () {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = ttsTextToSpeech.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {
                        String toSpeak = anhViet.get ( mCurrentPage );
                        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                        ttsTextToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }

        });
    }

    // findViewById
    private void anhXa() {
        arrowBack = findViewById ( R.id.arrow_back_practice_word );
        vpVocabulary = findViewById ( R.id.view_pager_practice_word );
        ibSpeak = findViewById ( R.id.ib_speak_practice_word );
        ibVoice = findViewById ( R.id.ib_voice_practice_word );
        tvResult = findViewById ( R.id.tv_result_practice_word );
        tvScore = findViewById ( R.id.tv_score_practice_word );
        mlist = new ArrayList<String> ();
        anhViet = new ArrayList<> ();
        dataAccess = DataAccessAnhViet.getInstance ( this );
    }

    private void openAndCloseDatabase(){
        dataAccess.open();
        anhViet = dataAccess.getRandomWords ();
        dataAccess.close();
    }

    private void listenOut(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Mời phát âm");
        try {
            startActivityForResult(intent,456);
        }catch (Exception e){

        }
    }
}
