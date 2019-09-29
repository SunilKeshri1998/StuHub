package com.decimals.stuhub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by ADARSH ANURAG on 02-04-2018.
 */

public class SubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        TextView branchName = (TextView) findViewById(R.id.branch_name);
        final ListView listView = (ListView) findViewById(R.id.sub_list_view);
        Bundle bundle = getIntent().getExtras();
        final int branch = bundle.getInt("branch");
        String bName[] = getResources().getStringArray(R.array.branch);


        if (branch == 0) {
            branchName.setText(bName[0]);
            final String cse[] = getResources().getStringArray(R.array.branch_cse);
            final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cse);
            listView.setAdapter(arrayAdapter);
        }
        if (branch == 1) {
            branchName.setText(bName[1]);
            final String eee[] = getResources().getStringArray(R.array.branch_eee);
            final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, eee);
            listView.setAdapter(arrayAdapter);
//
        }
        if (branch == 2) {
            branchName.setText(bName[2]);
            final String ece[] = getResources().getStringArray(R.array.branch_ece);
            final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ece);
            listView.setAdapter(arrayAdapter);
        }
        if (branch == 3) {
            branchName.setText(bName[3]);
            final String me[] = getResources().getStringArray(R.array.branch_me);
            final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, me);
            listView.setAdapter(arrayAdapter);

        }
        if (branch == 4) {
            branchName.setText(bName[4]);
            final String ce[] = getResources().getStringArray(R.array.branch_ce);
            final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ce);
            listView.setAdapter(arrayAdapter);

        }
        if (branch == 5) {
            branchName.setText(bName[5]);
            final String mme[] = getResources().getStringArray(R.array.branch_mme);
            final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mme);
            listView.setAdapter(arrayAdapter);
        }
        if (branch == 6) {
            branchName.setText(bName[6]);
            final String mfe[] = getResources().getStringArray(R.array.branch_mfe);
            final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mfe);
            listView.setAdapter(arrayAdapter);

        }
        if (branch == 7) {
            branchName.setText(bName[7]);
            final String others[] = getResources().getStringArray(R.array.branch_others);
            final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, others);
            listView.setAdapter(arrayAdapter);

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String courseName = (String) listView.getItemAtPosition(position);
                Intent intent = new Intent(SubjectActivity.this, SubjectSearchActivity.class);
                intent.putExtra("selectedBranch", branch);
                intent.putExtra("selectedCourse", courseName);
                startActivity(intent);
            }
        });

    }
//    @Override
//    public void onBackPressed() {
//        Intent intent =new Intent(SubjectActivity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }

    public void launchProfile(View view) {
        Intent intent = new Intent(SubjectActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void launchDownload(View view) {
        startActivity(new Intent(SubjectActivity.this, SearchActivity.class));
    }

    public void launchUpload(View view) {
        startActivity(new Intent(SubjectActivity.this, UploadActivity.class));
    }

    public void launchAbout(View view) {
        startActivity(new Intent(SubjectActivity.this, AboutActivity.class));
    }

    public void launchHome(View view) {
        startActivity(new Intent(SubjectActivity.this, MainActivity.class));
    }
}
