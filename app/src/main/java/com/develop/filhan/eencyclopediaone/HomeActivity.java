package com.develop.filhan.eencyclopediaone;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getFragmentManager();

    //Initiate Fragment
    Fragment home = new HomeFragment();
    Fragment encylopedia = new ListFragment();
    Fragment search = new SearchFragment();
    Fragment fav = new FavoriteFragment();
    Fragment profile = new AuthFragment();

    //Aksi jika menu Bottom View Navigation dipilih
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                default: selectedFragment=home; break;
                case R.id.navigation_home:
                    selectedFragment=home;
                    break;
                case R.id.navigation_encyclopedia:
                    selectedFragment=encylopedia;
                    break;
//                case R.id.navigation_search:
//                    selectedFragment=gsearch;
//                    break;
                case R.id.navigation_favorite:
                    selectedFragment=fav;
                    break;
                case R.id.navigation_profile:
                    selectedFragment=profile;
                    //fragmentTransaction.replace(R.id.containerHome, fav).commit();
                    break;
            }
            fragmentTransaction.replace(R.id.containerHome, selectedFragment).commit();
            return true;
        }
    };

    //Method yang dijalankan saat OnCreate Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //mTextMessage = (TextView) findViewById(R.id.message);
        loadFirstFragment();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //Method yang dijalankan saat menu dibuat action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_listitem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Method yang untuk aksi tiap item menu action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //Method untuk set Nama pada Fragment
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    //Method yang dijalankan untuk membuat Fragment Default
    private void loadFirstFragment(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerHome, new HomeFragment()).commit();
    }
}
