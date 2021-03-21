package com.geolocatorapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.geolocatorapplication.Fragments.FirstFragment;
import com.geolocatorapplication.Fragments.MapsFragment;
import com.geolocatorapplication.Fragments.SearchFragment;
import com.geolocatorapplication.Fragments.SecondFragment;
import com.geolocatorapplication.Fragments.ThirdFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
private FirebaseUser currentUser;
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    ThirdFragment thirdFragment;
    MapsFragment mapFragment;
    SearchFragment searchFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstFragment = new FirstFragment();
        thirdFragment=new ThirdFragment();
        secondFragment=new SecondFragment();
        searchFragment=new SearchFragment();
        mapFragment=new MapsFragment();
        loadFragment(mapFragment);
        BottomNavigationView bottomNavigationView=findViewById(R.id.navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selected=null;
                        switch (item.getItemId()){

                            case R.id.home:
                                selected=mapFragment;
                                break;
                            case R.id.favorites:
                                selected=secondFragment;
                                break;
                            case R.id.profile:
                                selected=thirdFragment;
                                break;
                            case R.id.search:
                                  selected=searchFragment;
                                 break;
                        }
                        loadFragment(selected);

                        return true;
                    }
                }
        );

    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();

    }
}
