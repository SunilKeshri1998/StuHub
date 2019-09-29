package com.decimals.stuhub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Shubham on 18-04-2018.
 */

public class RulesActivity extends AppCompatActivity {

    private TextView textView;
    private android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        //tootlbar
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.rules_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Rules/Regulations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView) findViewById(R.id.rule_textview);
        String rules[] = getResources().getStringArray(R.array.rules);
        textView.setText(rules[0] + "\n\n" + rules[1] + "\n\n" + rules[2] + "\n\n" + rules[3] + "\n\n" + rules[4] + "\n\n" + rules[5] + "\n\n" + rules[6] + "\n\n" + rules[7] + "\n");


    }
}
