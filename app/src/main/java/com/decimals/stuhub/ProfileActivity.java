package com.decimals.stuhub;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Shubham on 10-04-2018.
 */

public class ProfileActivity extends AppCompatActivity {
    String user_name;
    String user_email;
    String user_uid;
    Uri photoUri;
    int itemCount;
    String sItemCount;
    TextView mTextview;
    private android.support.v7.widget.Toolbar toolbar;
    private FirebaseAuth auth;
    private TextView userName, levelCount;
    private TextView userEmail;
    private ImageView userImage;
    private Button contributionBtn;
    private Button downloadBtn;
    private RelativeLayout levelView;

    private RecyclerView mResultList;
    private DatabaseReference mfilesDatabase;
    private TextView xp;
    private ProgressBar pB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.profile_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.profile));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextview = (TextView) findViewById(R.id.con);

        //bottom sheet
        View bottomsheet = findViewById(R.id.design_bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        //mTextview.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        //mTextview.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            user_name = currentUser.getDisplayName();
            user_email = currentUser.getEmail();
            user_uid = currentUser.getUid();
            photoUri = currentUser.getPhotoUrl();
        }


        //user_name = "Name: " + user_name;
        //user_email = "Email: " + user_email;
        userName = (TextView) findViewById(R.id.profile_name);
        userName.setText(user_name);

//        userEmail = (TextView) findViewById(R.id.profile_email);
//        userEmail.setText(user_email);

        userImage = (ImageView) findViewById(R.id.profile_image);
        Glide.with(this).load(photoUri).into(userImage);

        levelView = (RelativeLayout) findViewById(R.id.levelView);
        contributionBtn = (Button) findViewById(R.id.contributionBtn);
        downloadBtn = (Button) findViewById(R.id.myDownloads);
        contributionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
            }
        });


        mfilesDatabase = FirebaseDatabase.getInstance().getReference("files");
        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));


        Query firebaseSearchQuery = mfilesDatabase.orderByChild("uploaderUid").startAt(user_uid).endAt(user_uid + "\uf8ff");

        final FirebaseRecyclerAdapter<files, filesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<files, filesViewHolder>(

                files.class,   //passed_the_defining_class
                R.layout.contribution_list_layout, //passed_the_layout_file
                filesViewHolder.class,
                firebaseSearchQuery   //passed_the_database_reference

        ) {
            @Override
            protected void populateViewHolder(filesViewHolder viewHolder, files model, int position) {


                viewHolder.setDetails(getApplicationContext(), model.getdisplayName(), model.getdescription());

                itemCount = getItemCount();
                sItemCount = String.valueOf(itemCount);
                contributionBtn.setText("Contributions: " + sItemCount);
                levelCount = (TextView) findViewById(R.id.level);
                pB = (ProgressBar) findViewById(R.id.progressBar);
                xp = (TextView) findViewById(R.id.xp);
                double level = ((-1 + Math.sqrt(1 + 8 * itemCount)) / 2.0);
                level = Math.floor(level);
                int iLevel = (int) level;
                String sLevel = String.valueOf(iLevel);
                levelCount.setText(sLevel);
                int diff = itemCount - ((iLevel * (iLevel + 1)) / 2);
                pB.setProgress((int) ((100 * diff) / (iLevel + 1)));
                xp.setText(String.valueOf((((iLevel + 2) * (iLevel + 1)) / 2 - itemCount) * 100) + "xp needed for level " + String.valueOf(iLevel + 1));
            }

            @Override
            public files getItem(int position) {
                return super.getItem(getItemCount() - 1 - position);
            }

        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void launchProfile(View view) {
    }

    public void launchDownload(View view) {
        startActivity(new Intent(ProfileActivity.this, SearchActivity.class));
    }

    public void launchUpload(View view) {
        startActivity(new Intent(ProfileActivity.this, UploadActivity.class));
    }

    public void launchAbout(View view) {
        Intent intent = new Intent(ProfileActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    public void launchHome(View view) {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public static class filesViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public filesViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }


        public void setDetails(Context ctx, String displayName, String description) {

            TextView file_name = (TextView) mView.findViewById(R.id.name_text);
            TextView description_text = (TextView) mView.findViewById(R.id.description_text);

            file_name.setText(displayName);
            description_text.setText(description);

        }

    }


}
