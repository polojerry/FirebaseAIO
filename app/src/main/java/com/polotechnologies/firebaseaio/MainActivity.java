package com.polotechnologies.firebaseaio;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.polotechnologies.firebaseaio.adapters.BottomNavigationBehaviour;
import com.polotechnologies.firebaseaio.fragments.FirestoreFragment;
import com.polotechnologies.firebaseaio.fragments.RealtimeFragment;
import com.polotechnologies.firebaseaio.fragments.StorageFragment;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView mBottomNavigationView;
    ActionBar actionBar;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

        mBottomNavigationView = findViewById(R.id.mainBottNav);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehaviour());


        loadFragment(new StorageFragment());


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.actionFirecast:
                fragment = new FirestoreFragment();
                break;
            case R.id.actionRealtime:
                fragment = new RealtimeFragment();
                break;
            case R.id.actionStorage:
                fragment = new StorageFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
