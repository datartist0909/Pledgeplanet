package com.example.pledgeplanet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RewardActivity extends AppCompatActivity {

    private TextView rewardTextView;
    private TextView coinTextView;
    private int coinCount = 0;
    private SharedPreferences sharedPreferences;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        sharedPreferences = getSharedPreferences("pledge_planet_prefs", MODE_PRIVATE);

        rewardTextView = findViewById(R.id.reward_text_view);
        coinTextView = findViewById(R.id.coin_text_view);
        nextButton = findViewById(R.id.next_button);

        // Initialize coin count
        coinCount = getCoinCountFromSharedPreferences();

        // Display a reward message or something
        rewardTextView.setText("Congratulations, you've completed a challenge!");

        // Award 10 coins for completing the challenge
        awardCoins(10);

        // Update the coin count display
        coinTextView.setText("Coins: " + coinCount);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the RewardActivity and go back to the previous activity
                finish();
            }
        });
    }

    private int getCoinCountFromSharedPreferences() {
        return sharedPreferences.getInt("coin_count", 0);
    }

    private void awardCoins(int coins) {
        coinCount += coins;
        sharedPreferences.edit().putInt("coin_count", coinCount).apply();
    }
}