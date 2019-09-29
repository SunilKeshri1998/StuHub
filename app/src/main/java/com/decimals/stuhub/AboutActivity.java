package com.decimals.stuhub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by ADARSH ANURAG on 4/2/2018.
 */
public class AboutActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.about_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.about));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AboutActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void launchProfile(View view) {
        Intent intent = new Intent(AboutActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void launchDownload(View view) {
        startActivity(new Intent(AboutActivity.this, SearchActivity.class));
    }

    public void launchUpload(View view) {
        startActivity(new Intent(AboutActivity.this, UploadActivity.class));
    }

    public void launchAbout(View view) {

    }

    public void launchHome(View view) {
        Intent intent = new Intent(AboutActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void sendEmail(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setData(Uri.parse("email"));
        String[] str = {"stuhub6.9@gmail.com"};
        i.putExtra(Intent.EXTRA_EMAIL, str);
        i.setType("message/rfc822");
        Intent chooser = Intent.createChooser(i, "Launch Email");
        startActivity(chooser);
    }

    public void openFbPage(View view) {
        Intent fbIntent = new Intent(Intent.ACTION_VIEW);
        fbIntent.setData(Uri.parse("https://www.facebook.com/StuHub6.9/"));
        startActivity(fbIntent);
    }
}
