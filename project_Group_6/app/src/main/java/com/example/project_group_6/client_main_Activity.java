package com.example.project_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class client_main_Activity extends AppCompatActivity {


    Button btn_logout_client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_main);
        initView();
    }

    public void initView(){
        btn_logout_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(client_main_Activity.this, SelectToLogin.class);
                startActivity(intent);
            }
        });
    }













}
