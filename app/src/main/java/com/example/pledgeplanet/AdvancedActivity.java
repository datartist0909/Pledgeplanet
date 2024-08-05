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

public class AdvancedActivity extends AppCompatActivity {

    private ChallengeAdapter challengeAdapter;
    private int currentChallengeIndex;
    private RecyclerView recyclerView;

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
        challengeList.add(new Challenge("Challenge 1", "Support the development of waste sorting, recycling, and composting facilities."));
        challengeList.add(new Challenge("Challenge 2", "Explore living a plastic-free lifestyle without relying on modern conveniences that often involve plastic packaging for one day"));
        challengeList.add(new Challenge("Challenge 3", "Share your knowledge and experiences with plastic reduction through social media"));
        challengeList.add(new Challenge("Challenge 1", " Conduct research on the environmental impact of plastic pollution and share your findings with the community."));
        challengeList.add(new Challenge("Challenge 1", "Explore innovative solutions to replace plastic with biodegradable or compostable materials."));
        challengeList.add(new Challenge("Challenge 1", "Raise awareness about plastic pollution and its impact on the environment."));
        challengeList.add(new Challenge("Challenge 1", "Analyze the types and quantities of plastic waste in your community to identify problem areas."));


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