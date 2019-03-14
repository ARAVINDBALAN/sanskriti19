package com.blazingapps.asus.sanskriti19;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "TAGZ"
            ;
    TextView team1,team2,team3,team4;
    TextView score1,score2,score3,score4;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        team1 = findViewById(R.id.team1);
        team2 = findViewById(R.id.team2);
        team3 = findViewById(R.id.team3);
        team4 = findViewById(R.id.team4);
        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);
        score3 = findViewById(R.id.score3);
        score4 = findViewById(R.id.score4);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        db.collection("scoreboard").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                List<TeamObj> teams = new ArrayList<>();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.get("name") != null) {
                        String name = doc.getString("name");
                        double score = doc.getDouble("score");
                        teams.add(new TeamObj(name,score));
                    }
                }

                updateScore(teams);
            }
        });
    }

    public void gotoNotification(View view) {

    }

    public void gotoDev(View view) {
        //startActivity(new Intent(this, DeveloperActivity.class));
    }

    public void gotoProfile(View view) {
        startActivity(new Intent(this,ProfileActivity.class));
    }

    public void updateScore(List<TeamObj> teams){

//        List<TeamObj> sortedTeams = new ArrayList<>();
        for (int i = 0; i<teams.size()-1 ; ++i){
            for (int j=i+1; j<teams.size();++j){
                if (teams.get(i).getScore() < teams.get(j).getScore())  {
                    TeamObj temp = new TeamObj(teams.get(i));
                    teams.get(i).setObj(teams.get(j));
                    teams.get(j).setObj(temp);
                }
            }
        }

        for (TeamObj obj : teams){
            Log.d("TAGZ",String.valueOf(obj.getScore()));
        }
        team1.setText(teams.get(0).getName());
        team2.setText(teams.get(1).getName());
        team3.setText(teams.get(2).getName());
        team4.setText(teams.get(3).getName());

        score1.setText(String.valueOf(teams.get(0).getScore()));
        score2.setText(String.valueOf(teams.get(1).getScore()));
        score3.setText(String.valueOf(teams.get(2).getScore()));
        score4.setText(String.valueOf(teams.get(3).getScore()));
    }
}
