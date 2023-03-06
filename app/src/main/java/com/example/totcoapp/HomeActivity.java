package com.example.totcoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

//        TextView txt = findViewById(R.id.sample);

        Intent getintent = getIntent();
        String name = getintent.getStringExtra("agent_name");
//        txt.setText(name);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatbtn);
        bottomNavigationView = findViewById(R.id.bottomnav);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt = new Intent(getApplicationContext(),PlaceOrder.class);
                startActivity(nxt);
            }
        });


        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new NewDashboard()).commit();



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfrag = null;
                switch (item.getItemId()){
                    case R.id.dashboard:
                        selectedfrag = new NewDashboard();
                        break;
                    case R.id.sales:
                        selectedfrag = new Sales();
                        break;
                    case R.id.report:
                        selectedfrag = new Report();
                        break;
                    case R.id.account:
                        selectedfrag = new Account();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,selectedfrag).commit();
                return true;
            }
        });

    }
}