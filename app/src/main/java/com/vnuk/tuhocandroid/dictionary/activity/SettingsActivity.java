package com.vnuk.tuhocandroid.dictionary.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.vnuk.tuhocandroid.dictionary.R;
import com.vnuk.tuhocandroid.dictionary.database.DataAccessAnhViet;


public class SettingsActivity extends AppCompatActivity {
    Toolbar toolbar;
    DataAccessAnhViet mDataAccessAnhViet;
    TextView clearHistory, share;
    TextView mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_settings );

        anhXa();

        // save dark mode in settings
        SharedPreferences sharedPreferences = getSharedPreferences( "sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean( "isDarkModeOn", false);
        
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_YES);
            mode.setText( "Light Mode");
        }
        else {
            AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO);
            mode.setText( "Dark Mode");
        }
         toolbar.setNavigationIcon ( R.drawable.ic_arrow_back );
        setSupportActionBar ( toolbar );
        getSupportActionBar ().setTitle ( "Settings" );
        

        clearHistory.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                mDataAccessAnhViet = new DataAccessAnhViet ( SettingsActivity.this );
                mDataAccessAnhViet.open ();
                showAlertDialog();
            }
        } );
        
        share.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_SEND);
                intent.setType ( "text/plain" );
                String shareBody = "This is a perfect dictionary application, you should try it";
                String shareTitle = "Dictionary App";

                intent.putExtra ( intent.EXTRA_SUBJECT, shareTitle );
                intent.putExtra ( intent.EXTRA_TEXT, shareBody );

                startActivity ( Intent.createChooser ( intent, "Share Via" ) );
            }
        } );
        
        // dark mode
        mode.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (isDarkModeOn) {
                    AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean( "isDarkModeOn", false);
                    editor.apply();
                    mode.setText( "Dark Mode");
                }
                else {
                    AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean( "isDarkModeOn", true);
                    editor.apply();
                    mode.setText( "Light Mode");
                }
            }
        } );

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId () == android.R.id.home){
            onBackPressed ();
        }
        return super.onOptionsItemSelected ( item );
    }

    private void anhXa() {
        share = findViewById ( R.id.share );
        mode = findViewById ( R.id.mode );
        clearHistory = findViewById ( R.id.clear_history );
        toolbar = findViewById ( R.id.toolbar_settings );
    }


    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder ( SettingsActivity.this );
        builder.setTitle ( "Are you sure?" );
        builder.setMessage ( "All the history will be deleted" );

        String yes = "Yes";
        builder.setPositiveButton ( yes, new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDataAccessAnhViet.clearHistoryYourWord ();
            }
        } );

        String no = "No";
        builder.setNegativeButton ( no, new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        } );

        AlertDialog dialog = builder.create ();
        dialog.show ();
    }
}
