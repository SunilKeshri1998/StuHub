package com.decimals.stuhub;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by ADARSH ANURAG on 4/2/2018.
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.main_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.home_title));

        final ArrayList<Branch> branch = new ArrayList<Branch>();
        branch.add(new Branch("Computer Science and Engineering", R.mipmap.computer_science_and_engineering));
        branch.add(new Branch("Electrical and Electronics Engineering", R.mipmap.electrical_and_electronics_engineering));
        branch.add(new Branch("Electrical and Communications Engineering", R.mipmap.elctronics_and_communications_engineering));
        branch.add(new Branch("Mechanical Engineering", R.mipmap.mechanical_engineering));
        branch.add(new Branch("Civil Engineering", R.mipmap.civil_engineering));
        branch.add(new Branch("Metallurgical and Materials Engineering", R.mipmap.metallurgical_engineering));
        branch.add(new Branch("Manufacturing Engineering", R.mipmap.manufacturing_engineering));
        branch.add(new Branch("Others", R.mipmap.others));
        BranchAdapter adapter1 = new BranchAdapter(this, branch);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, SubjectActivity.class);
                if (position == 0) {
                    intent.putExtra("branch", 0);
                }
                if (position == 1) {
                    intent.putExtra("branch", 1);
                }
                if (position == 2) {
                    intent.putExtra("branch", 2);
                }
                if (position == 3) {
                    intent.putExtra("branch", 3);
                }
                if (position == 4) {
                    intent.putExtra("branch", 4);
                }
                if (position == 5) {
                    intent.putExtra("branch", 5);
                }
                if (position == 6) {
                    intent.putExtra("branch", 6);
                }
                if (position == 7) {
                    intent.putExtra("branch", 7);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout_btn) {
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        } else if (item.getItemId() == R.id.rules_btn) {
            startActivity(new Intent(this, RulesActivity.class));
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            //super.onBackPressed();
            System.gc();
            System.exit(0); // finish app
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }


    private void sendToStart() {
        Intent startIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void launchProfile(View view) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void launchDownload(View view) {
        startActivity(new Intent(MainActivity.this, SearchActivity.class));
    }

    public void launchUpload(View view) {
        startActivity(new Intent(MainActivity.this, UploadActivity.class));
    }

    public void launchAbout(View view) {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
    }

    public void launchHome(View view) {

    }

    public void launchChat(View v) {
        startActivity(new Intent(MainActivity.this, ChatActivity.class));
    }


}
