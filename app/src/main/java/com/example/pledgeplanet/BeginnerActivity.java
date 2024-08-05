package com.example.pledgeplanet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class BeginnerActivity extends AppCompatActivity {


    private int currentChallengeIndex;

    private RecyclerView recyclerView;
    private ChallengeAdapter challengeAdapter;
    private List<Challenge> challengeList;
    public static final int REQUEST_IMAGE_PICK = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        challengeList = new ArrayList<>();
        challengeAdapter = new ChallengeAdapter(challengeList, this);
        recyclerView.setAdapter(challengeAdapter);

        // Add some sample challenges to the list
        challengeList.add(new Challenge("Challenge 1", "Take a picture of plastic pollution near your locality"));
        challengeList.add(new Challenge("Challenge 2", "Pack your lunch in reusable containers instead of relying on single-use plastic wrap or baggies"));
        challengeList.add(new Challenge("Challenge 3", "Ditch plastic straws and invest in a reusable stainless steel straw."));
        challengeList.add(new Challenge("Challenge 4", " Opt for unpackaged fruits and vegetables from plastic covers"));
        challengeList.add(new Challenge("Challenge 5", "Help your friends in reusing plastic pollution"));
        challengeList.add(new Challenge("Challenge 6", "Try buying ebooks or audiobooks instaed of copies with plastic covers"));
        challengeList.add(new Challenge("Challenge 7", ":Replace your plastic toothbrush instead of bamboo one"));


        challengeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            challengeAdapter.setUploadedImageUri(selectedImageUri, currentChallengeIndex);
        }
    }
}