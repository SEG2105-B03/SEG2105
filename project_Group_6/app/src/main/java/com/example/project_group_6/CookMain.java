package com.example.project_group_6;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CookMain extends AppCompatActivity {

    Button btn_logout_cook;
    Button btn_menu;
    Button btnOfferedMealsList;
    ImageView profileButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_main);
        initView();
    }


    public void initView(){
        ImageView imgProfile = (ImageView) findViewById(R.id.Profile_ccok);
        btn_logout_cook = findViewById(R.id.btn_logout_cook);
        btn_menu = findViewById(R.id.btn_menu);
        btnOfferedMealsList = findViewById(R.id.btn_offered_meals_list);
        profileButton = findViewById(R.id.image_purchase_request);

        btn_logout_cook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("UserID", MODE_PRIVATE);
                prefs.edit().remove("CookID").commit();
                Intent intent = new Intent(CookMain.this, SelectToLogin.class);
                startActivity(intent);
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CookMain.this, Cook_profile.class);
                startActivity(intent);
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CookMain.this,  MenuList.class);
                startActivity(intent);
            }
        });
        btnOfferedMealsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CookMain.this,CookMenu.class);
                startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CookMain.this,CookOrder.class);
                startActivity(intent);
            }
        });

    }

}
