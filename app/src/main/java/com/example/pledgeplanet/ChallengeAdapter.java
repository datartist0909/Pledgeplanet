package com.example.pledgeplanet;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;
public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder> {

    private List<Challenge> challengeList;
    private Context context;
    private Uri uploadedImageUri;
    private int uploadedImagePosition = -1;

    public ChallengeAdapter(List<Challenge> challengeList, Context context) {
        this.challengeList = challengeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_item, parent, false);
        return new ChallengeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChallengeViewHolder holder, final int position) {
        Challenge challenge = challengeList.get(position);
        holder.challengeNameTextView.setText(challenge.getName());
        holder.challengeDescriptionTextView.setText(challenge.getDescription());

        holder.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle submit button click
                challengeList.get(position).setCompleted(true);

                // Start the Reward Activity
                Intent intent = new Intent(context, RewardActivity.class);
                context.startActivity(intent);
            }
        });

        holder.uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the image gallery to allow the user to select an image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ((Activity) context).startActivityForResult(intent, 1);
            }
        });

        if (uploadedImageUri!= null && position == uploadedImagePosition) {
            holder.uploadedImageView.setImageURI(uploadedImageUri);
            holder.uploadedImageView.setVisibility(View.VISIBLE);
            holder.uploadImageButton.setVisibility(View.INVISIBLE); // Hide upload button
            holder.submitButton.setVisibility(View.VISIBLE); // Show submit button
        } else {
            holder.uploadedImageView.setVisibility(View.INVISIBLE);
            holder.uploadImageButton.setVisibility(View.VISIBLE); // Show upload button
            holder.submitButton.setVisibility(View.GONE); // Hide submit button
        }
    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }

    public class ChallengeViewHolder extends RecyclerView.ViewHolder {
        public TextView challengeNameTextView;
        public TextView challengeDescriptionTextView;
        public Button submitButton;
        public Button uploadImageButton;
        public ImageView uploadedImageView;

        public ChallengeViewHolder(@NonNull View itemView) {
            super(itemView);
            challengeNameTextView = itemView.findViewById(R.id.challenge_name_text_view);
            challengeDescriptionTextView = itemView.findViewById(R.id.challenge_description_text_view);
            submitButton = itemView.findViewById(R.id.submit_button);
            uploadImageButton = itemView.findViewById(R.id.upload_image_button);
            uploadedImageView = itemView.findViewById(R.id.uploaded_image_view);
        }
    }

    public void setUploadedImageUri(Uri uri, int position) {
        uploadedImageUri = uri;
        uploadedImagePosition = position;
        notifyDataSetChanged();
    }
}



