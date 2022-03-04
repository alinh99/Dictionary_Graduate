package com.vnuk.tuhocandroid.dictionary.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;
import com.vnuk.tuhocandroid.dictionary.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // save dark mode in all pages
        SharedPreferences sharedPreferences = getSharedPreferences( "sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean( "isDarkModeOn", false);

        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean ( "isDarkModeOn",true );
            editor.apply ();
        }
        else {
            AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean ( "isDarkModeOn",false );
            editor.apply ();
        }
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        Toolbar toolbar = findViewById ( R.id.toolbar_main );
        setSupportActionBar ( toolbar );
        toolbar.setNavigationIcon ( R.drawable.ic_arrow_back );
        DrawerLayout drawer = findViewById ( R.id.drawer_layout );
        NavigationView navigationView = findViewById ( R.id.nav_view );
        mAppBarConfiguration = new AppBarConfiguration.Builder (
                R.id.nav_eng_viet, R.id.nav_your_word, R.id.nav_practice )
                .setDrawerLayout ( drawer )
                .build ();
        NavController navController = Navigation.findNavController ( this, R.id.nav_host_fragment );
        NavigationUI.setupActionBarWithNavController ( this, navController, mAppBarConfiguration );
        NavigationUI.setupWithNavController ( navigationView, navController );


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate ( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId ();

        if (id == R.id.action_settings){
            Intent intent = new Intent (MainActivity.this, SettingsActivity.class);
            startActivity ( intent );
            return true;
        }

        if (id == R.id.action_exit){
            System.exit ( 0 );
            return true;
        }
        
        return super.onOptionsItemSelected ( item );
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController ( this, R.id.nav_host_fragment );
        return NavigationUI.navigateUp ( navController, mAppBarConfiguration )
                || super.onSupportNavigateUp ();
    }
    
}
