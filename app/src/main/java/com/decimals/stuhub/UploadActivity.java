package com.decimals.stuhub;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class UploadActivity extends AppCompatActivity {


    private static final int PICK_FILE_REQUEST = 1;
    AutoCompleteTextView branch_text;
    AutoCompleteTextView subject_text;
    TextView textview_branch;//uploading file layout
    TextView textview_subject;//uploading file layout
    EditText description;
    String description_text;
    String selected_branch;
    String selected_subject;
    String downloadlink;
    String fileName;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference Ref = rootRef.child("files").push();
    String user_name;
    String user_uid;
    double progress_percent;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private android.support.v7.widget.Toolbar mToolbar;
    private LinearLayout mainlayout;
    private LinearLayout fileselectlayout;
    private Uri fileUri;
    private Button select_file_btn;
    private Button file_pause_btn;
    private Button file_cancel_btn;
    private StorageTask storageTask;
    private TextView filename_label;
    private ProgressBar progress;
    private TextView size_label;
    private TextView progess_label;
    private StorageReference mStorageRef;
    private DatabaseReference branchRef = rootRef.child("branch");
    private String subject_arr[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //currentuser
        if (user != null) {
            user_name = user.getDisplayName();
            user_uid = user.getUid();
        }

        //tootlbar
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.uploadActivity_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Upload");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String branch_arr[] = getResources().getStringArray(R.array.branch);

        branch_text = (AutoCompleteTextView) findViewById(R.id.text_branch);
        final ArrayAdapter<String> branch_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, branch_arr);
        branch_text.setAdapter(branch_adapter);
        branch_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    // on focus off
                    String str = branch_text.getText().toString();

                    ListAdapter listAdapter = branch_text.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); i++) {
                        String temp = listAdapter.getItem(i).toString();
                        if (str.compareTo(temp) == 0) {
                            return;
                        }
                    }

                    branch_text.setText(null);

                }
            }
        });


        // if (selected_branch != null && !selected_branch.isEmpty()) {
        branch_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected_branch = branch_adapter.getItem(i).toString();
                if (selected_branch.equals("Computer Science and Engineering")) {
                    subject_arr = getResources().getStringArray(R.array.branch_cse);
                } else if (selected_branch.equals("Electrical and Communication Engineering")) {
                    subject_arr = getResources().getStringArray(R.array.branch_ece);
                } else if (selected_branch.equals("Electrical and Electronics Engineering")) {
                    subject_arr = getResources().getStringArray(R.array.branch_eee);
                } else if (selected_branch.equals("Mechanical Engineering")) {
                    subject_arr = getResources().getStringArray(R.array.branch_me);
                } else if (selected_branch.equals("Civil Engineering")) {
                    subject_arr = getResources().getStringArray(R.array.branch_ce);
                } else if (selected_branch.equals("Metallurgical and Materials Engineering")) {
                    subject_arr = getResources().getStringArray(R.array.branch_mme);
                } else if (selected_branch.equals("Manufacturing Engineering")) {
                    subject_arr = getResources().getStringArray(R.array.branch_mfe);
                } else if (selected_branch.equals("Others")) {
                    subject_arr = getResources().getStringArray(R.array.branch_others);
                }


                subject_text = (AutoCompleteTextView) findViewById(R.id.text_subject);
                final ArrayAdapter<String> subject_adapter = new ArrayAdapter<String>(UploadActivity.this, android.R.layout.simple_list_item_1, subject_arr);
                subject_text.setAdapter(subject_adapter);
                subject_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (!b) {
                            // on focus off
                            String str = subject_text.getText().toString();

                            ListAdapter listAdapter = subject_text.getAdapter();
                            for (int i = 0; i < listAdapter.getCount(); i++) {
                                String temp = listAdapter.getItem(i).toString();
                                if (str.compareTo(temp) == 0) {
                                    return;
                                }
                            }

                            branch_text.setText(null);
                            subject_text.setText(null);

                        }
                    }
                });

                subject_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selected_subject = subject_adapter.getItem(i).toString();
                    }
                });

            }
        });


        // }


        mStorageRef = FirebaseStorage.getInstance().getReference();

        select_file_btn = (Button) findViewById(R.id.select_file_btn);
        select_file_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
////                if (selected_branch != null && !selected_branch.isEmpty() && selected_subject != null && !selected_subject.isEmpty()) {
////                    recreate();
//                }
//                else{
                choose_file();
            }
        });

        mainlayout = (LinearLayout) this.findViewById(R.id.upload_file_layout);
        fileselectlayout = (LinearLayout) this.findViewById(R.id.file_select_layout);


        filename_label = (TextView) findViewById(R.id.filename_label);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        size_label = (TextView) findViewById(R.id.size_label);
        progess_label = (TextView) findViewById(R.id.progress_label);
        textview_branch = (TextView) findViewById(R.id.textview_branch);
        textview_subject = (TextView) findViewById(R.id.textview_subject);
        description = (EditText) findViewById(R.id.description);


        file_cancel_btn = (Button) findViewById(R.id.file_cancel_btn);
        file_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                storageTask.cancel();
                Toast.makeText(UploadActivity.this, "Upload Task Canceled!", Toast.LENGTH_SHORT).show();
            }
        });

        file_pause_btn = (Button) findViewById(R.id.file_pause_btn);
        file_pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String btnText = file_pause_btn.getText().toString();
                if (btnText.equals("Pause Upload")) {
                    storageTask.pause();
                    file_pause_btn.setText("Resume Upload");
                } else {
                    storageTask.resume();
                    file_pause_btn.setText("Pause Upload");
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UploadActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void choose_file() {
        if (selected_branch != null && !selected_branch.isEmpty() && selected_subject != null && !selected_subject.isEmpty()) {

            Intent intent = new Intent();
            intent.setType("*/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            try {
                startActivityForResult(
                        intent.createChooser(intent, "select a file"), PICK_FILE_REQUEST);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Please install a file manager", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(this, "Please select branch and subject for which you want to upload the file", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fileName = null;
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            //file_is_selected
            //show_the_hidden_layout
            mainlayout.setVisibility(LinearLayout.VISIBLE);
            fileselectlayout.setVisibility(LinearLayout.GONE);
            getSupportActionBar().setTitle("Uploading");

            //now enable the cancel and pause btn
            file_pause_btn.setEnabled(true);
            file_cancel_btn.setEnabled(true);


            fileUri = data.getData();
            String uristring = fileUri.toString();
            File myfile = new File(uristring);

            fileName = null;
            if (uristring.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = UploadActivity.this.getContentResolver().query(fileUri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)).toLowerCase();
                    }
                } finally {
                    cursor.close();
                }
            } else if (uristring.startsWith("file://")) {
                fileName = myfile.getName().toLowerCase();
            }
            filename_label.setText(fileName);
            textview_branch.setText(selected_branch);
            textview_subject.setText(selected_subject);
            Toast.makeText(this, "Uploading file", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            return;


        }

        description_text = description.getText().toString();
        String filename_key = Ref.getKey();
        final DatabaseReference subRef = branchRef.child(selected_branch + "/" + selected_subject + "/" + filename_key);

        StorageReference riversRef = mStorageRef.child("branch/" + selected_branch + "/" + selected_subject + "/" + filename_key + fileName);


        storageTask = riversRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                        Toast.makeText(UploadActivity.this, "File Uploaded!", Toast.LENGTH_SHORT).show();
                        recreate();
                        branch_text.setText("");
                        subject_text.setText("");
                        description.setText("");
                        //file_is_uploaded
                        //now disable the cancel and pause btn
                        file_pause_btn.setEnabled(false);
                        file_cancel_btn.setEnabled(false);

                        downloadlink = downloadUrl.toString();

                        // String lower_fileName=fileName.toLowerCase();
                        Ref.child("displayName").setValue(fileName);
                        Ref.child("description").setValue(description_text);
                        Ref.child("downloadUrl").setValue(downloadlink);
                        Ref.child("uploaderUid").setValue(user_uid);
                        Ref.child("uploaderName").setValue(user_name);

                        //write in branch section , the details of file
                        subRef.child("displayName").setValue(fileName);
                        subRef.child("description").setValue(description_text);
                        subRef.child("downloadUrl").setValue(downloadlink);
                        subRef.child("uploaderUid").setValue(user_uid);
                        subRef.child("uploaderName").setValue(user_name);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(UploadActivity.this, "File not Uploaded!", Toast.LENGTH_SHORT).show();
                        recreate();
                        branch_text.setText("");
                        subject_text.setText("");
                        description.setText("");
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        mainlayout.setVisibility(LinearLayout.VISIBLE);
                        fileselectlayout.setVisibility(LinearLayout.GONE);
                        progress_percent = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progress.setProgress((int) progress_percent);

                        String progresstext = taskSnapshot.getBytesTransferred() / (1024) + "/" + taskSnapshot.getTotalByteCount() / (1024) + " KB";//1024*1024
                        size_label.setText(progresstext);
                        progess_label.setText((int) progress_percent + "%");


                    }
                });


    }


    public void launchProfile(View view) {
        Intent intent = new Intent(UploadActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void launchDownload(View view) {
        startActivity(new Intent(UploadActivity.this, SearchActivity.class));
    }

    public void launchUpload(View view) {

    }

    public void launchAbout(View view) {
        startActivity(new Intent(UploadActivity.this, AboutActivity.class));
    }

    public void launchHome(View view) {
        Intent intent = new Intent(UploadActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
