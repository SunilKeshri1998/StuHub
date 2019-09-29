package com.decimals.stuhub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Shubham on 08-04-2018.
 */

public class SubjectSearchActivity extends AppCompatActivity {

    public int itemCount;
    public String sItemCount;
    TextView searchView;
    private String selected_branch;
    private String selected_subject;
    private Toolbar mToolbar;
    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;
    private DatabaseReference mbranchDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_search);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Bundle bundle = getIntent().getExtras();
        int branch = bundle.getInt("selectedBranch");
        String course = bundle.getString("selectedCourse");

        String branch_arr[] = getResources().getStringArray(R.array.branch);

        //tootlbar

        mToolbar = (Toolbar) findViewById(R.id.sub_searchActivity_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Downloads");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selected_branch = branch_arr[branch];
        selected_subject = course;


        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
        searchView = (TextView) findViewById(R.id.searchResultText);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mbranchDatabase = FirebaseDatabase.getInstance().getReference("branch" + "/" + selected_branch + "/" + selected_subject);

        FirebaseRecyclerAdapter<files, filesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<files, filesViewHolder>(

                files.class,   //passed_the_defining_class
                R.layout.list_layout, //passed_the_layout_file
                filesViewHolder.class,
                mbranchDatabase   //passed_the_database_reference

        ) {
            @Override
            protected void populateViewHolder(filesViewHolder viewHolder, files model, int position) {


                viewHolder.setDetails(getApplicationContext(), model.getdisplayName(), model.getdownloadUrl(), model.getdescription(), model.getuploaderName());

            }

            @Override
            public files getItem(int position) {
                return super.getItem(getItemCount() - 1 - position);
            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString().toLowerCase();
                if (searchText != null && !searchText.isEmpty()) {
                    firebaseUserSearch(searchText);
                } else {
                    recreate();
                }

            }
        });

    }
//    @Override
//    public void onBackPressed() {
//        Intent intent =new Intent(SubjectSearchActivity.this, SubjectActivity.class);
//        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
//    }

    private void firebaseUserSearch(String searchText) {

        Toast.makeText(SubjectSearchActivity.this, "Searching...", Toast.LENGTH_SHORT).show();
        searchView.setText("0 results");

        Query firebaseSearchQuery = mbranchDatabase.orderByChild("displayName").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<files, filesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<files, filesViewHolder>(

                files.class,
                R.layout.list_layout,
                filesViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(filesViewHolder viewHolder, files model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getdisplayName(), model.getdownloadUrl(), model.getdescription(), model.getuploaderName());

                itemCount = getItemCount();
                sItemCount = String.valueOf(itemCount);
                searchView.setText(sItemCount + " results");
            }

            @Override
            public files getItem(int position) {
                return super.getItem(getItemCount() - 1 - position);

            }

        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }

    public void launchProfile(View view) {
        Intent intent = new Intent(SubjectSearchActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void launchDownload(View view) {
        startActivity(new Intent(SubjectSearchActivity.this, SearchActivity.class));
    }

    public void launchUpload(View view) {
        startActivity(new Intent(SubjectSearchActivity.this, UploadActivity.class));
    }

    public void launchAbout(View view) {
        startActivity(new Intent(SubjectSearchActivity.this, AboutActivity.class));
    }

    public void launchHome(View view) {
        startActivity(new Intent(SubjectSearchActivity.this, MainActivity.class));
    }

}
