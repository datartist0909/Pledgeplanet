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

public class IntermediateActivity extends AppCompatActivity {


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
        challengeList.add(new Challenge("Challenge 1", " Carry your own bag for shopping"));
        challengeList.add(new Challenge("Challenge 2", " Use reusable cloth towels or napkins instead of paper towels."));
        challengeList.add(new Challenge("Challenge 3", " Opt for products packaged in glass or aluminum, which are more recyclable than plastic."));
        challengeList.add(new Challenge("Challenge 4", " Opt for unpackaged fruits and vegetables from plastic covers"));
        challengeList.add(new Challenge("Challenge 5", " Reduce meat consumption to decrease demand for plastic-packaged meat products."));
        challengeList.add(new Challenge("Challenge 6", " Try buying ebooks or audiobooks instaed of copies with plastic covers"));
        challengeList.add(new Challenge("Challenge 7", " :Assess the plastic pollution in your neighborhood and identify areas for improvement."));



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