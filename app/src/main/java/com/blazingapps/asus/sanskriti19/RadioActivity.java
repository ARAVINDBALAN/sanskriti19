package com.blazingapps.asus.sanskriti19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RadioActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseUser auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance().getCurrentUser();
        String name = auth.getDisplayName();

        Button b = findViewById(R.id.buttonrad);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("songs").add(new SongObject("hi","hello"));

               }
        });
    }
}
