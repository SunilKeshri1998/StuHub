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

public class SearchActivity extends AppCompatActivity {

    public int itemCount;
    public String sItemCount;
    int l;
    TextView searchView;
    private Toolbar mToolbar;
    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;
    private DatabaseReference mfilesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //tootlbar
        mToolbar = (Toolbar) findViewById(R.id.searchActivity_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All Downloads");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mfilesDatabase = FirebaseDatabase.getInstance().getReference("files");


        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
        searchView = (TextView) findViewById(R.id.searchResultText);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerAdapter<files, filesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<files, filesViewHolder>(

                files.class,   //passed_the_defining_class
                R.layout.list_layout, //passed_the_layout_file
                filesViewHolder.class,
                mfilesDatabase   //passed_the_database_reference

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    private void firebaseUserSearch(String searchText) {

        Toast.makeText(SearchActivity.this, "Searching...", Toast.LENGTH_SHORT).show();
        searchView.setText("0 results");

        Query firebaseSearchQuery = mfilesDatabase.orderByChild("displayName").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<files, filesViewHolder> firebaseRecyclerAdapter;
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<files, filesViewHolder>(

                files.class,
                R.layout.list_layout,
                filesViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            public void populateViewHolder(filesViewHolder viewHolder, files model, int position) {
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
        Intent intent = new Intent(SearchActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void launchDownload(View view) {

    }

    public void launchUpload(View view) {
        startActivity(new Intent(SearchActivity.this, UploadActivity.class));
    }

    public void launchAbout(View view) {
        startActivity(new Intent(SearchActivity.this, AboutActivity.class));
    }

    public void launchHome(View view) {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
