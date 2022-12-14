package com.example.project_group_6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CookMenu extends AppCompatActivity implements MenuAdaptor.ItemClickListener {
    String cookID;
    DatabaseReference databaseReference;
    ArrayList<String> listOfMenu;
    RecyclerView recyclerView;
    MenuAdaptor adapter;
    LinearLayoutManager layoutManager;
    ArrayList<String> listOfId;
    boolean suspend;

    public static class Menu {

        public String name;
        public String type;
        public Menu(){}

    }


    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        SharedPreferences prefs = getSharedPreferences("UserID", MODE_PRIVATE);
        cookID = prefs.getString("CookID", "");
//        Retrive list of menu
        listOfMenu = new ArrayList<String>();
        listOfId = new ArrayList<String>();

        adapter = new MenuAdaptor(listOfMenu);
        adapter.setClickListener(this);

        recyclerView = findViewById(R.id.listOfMenu);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-7629811168995371601-default-rtdb.firebaseio.com/");

        databaseReference.child("cook_accounts").child(cookID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suspend = dataSnapshot.child("_suspend").getValue(boolean.class);
                setUpMenu();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB expection", "Failed to read value.", error.toException());
            }
        });
        databaseReference.child("Menu").child(cookID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listOfMenu.clear();
                listOfId.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Menu cur = child.getValue(Menu.class);
                    listOfMenu.add(cur.name);
                    listOfId.add(child.getKey());
                    Log.d("DB out",child.getKey());
                }
                setUpMenu();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB expection", "Failed to read value.", error.toException());
            }
        });
        Log.d("COOK",cookID);
    }

    @Override
    public void onItemClick(View view, int position) {
        if(view.getId() == R.id.purchase_text){
            return;
        }

        String id = listOfId.get(position);
        databaseReference.child("Menu").child(cookID).child(id).removeValue();
//        ComplaintPop popUpClass = new ComplaintPop();
//        popUpClass.showPopupWindow(view,listOfComplaints.get(position),listOfCooks.get(position),listOfClient.get(position),databaseReference);
        Toast.makeText(this, "You removed " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
    }
    public void refresh(){
        databaseReference.child("Menu").child(cookID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listOfMenu.clear();
                listOfId.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Menu cur = child.getValue(Menu.class);
                    listOfMenu.add(cur.name);
                    listOfId.add(child.getKey());
                    Log.d("DB out",child.getKey());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB expection", "Failed to read value.", error.toException());
            }
        });

    }


    public void setUpMenu(){
        Button add = (Button) findViewById(R.id.addBtn);
        TextView title = (TextView) findViewById(R.id.Menu_title);
        title.setText("OFFERED MEALS");
        add.setText("Back");
        findViewById(R.id.meal_text).setVisibility(View.INVISIBLE);
        findViewById(R.id.meal_text).setEnabled(false);
        findViewById(R.id.type_text).setVisibility(View.INVISIBLE);
        findViewById(R.id.type_text).setEnabled(false);
        add.setEnabled(!suspend);
        adapter.setEnable(!suspend);
        if(suspend){
            listOfMenu.clear();
            listOfMenu.add("You are suspended");
            adapter.notifyDataSetChanged();
            return;
        }
        else{
            refresh();
            adapter.notifyDataSetChanged();
        }
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(CookMenu.this,CookMain.class));
            }
        });
        }

}
