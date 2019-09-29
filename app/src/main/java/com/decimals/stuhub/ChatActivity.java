package com.decimals.stuhub;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    String userMsg;
    EditText mEditText;
    ImageView mButton;
    DatabaseReference myRef;
    private Toolbar toolbar;
    private FirebaseUser chatUser;
    private String userName;
    private String timeStamp;
    private RecyclerView chatList;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference showRef = database.getReference("chat");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.chat_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Global");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chatUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatUser != null) {
            userName = chatUser.getDisplayName();
        }
        mButton = (ImageView) findViewById(R.id.send);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mEditText = (EditText) findViewById(R.id.chatMsg);
                userMsg = mEditText.getText().toString();

                timeStamp = new SimpleDateFormat("hh:mm aa  dd/MM/yyyy").format(new Date()).toString();
                userMsg = userMsg.trim();
                if (userMsg != null && !userMsg.isEmpty()) {

                    myRef = showRef.push();
                    myRef.child("userName").setValue(userName);
                    myRef.child("userMsg").setValue(userMsg);
                    myRef.child("msgTime").setValue(timeStamp);
                    mEditText.setText("");
                }
            }
        });

        chatList = (RecyclerView) findViewById(R.id.chat_list);
        chatList.setHasFixedSize(true);
        chatList.setLayoutManager(new LinearLayoutManager(this));


        final FirebaseRecyclerAdapter<chat, chatViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<chat, chatViewHolder>(

                chat.class,   //passed_the_defining_class
                R.layout.chat_layout, //passed_the_layout_file
                chatViewHolder.class,
                showRef   //passed_the_database_reference

        ) {
            @Override
            protected void populateViewHolder(chatViewHolder viewHolder, chat model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getUserName(), model.getUserMsg(), model.getMsgTime());
            }
        };

        chatList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                chatList.smoothScrollToPosition(firebaseRecyclerAdapter.getItemCount());
            }
        });


    }

    public static class chatViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public chatViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }


        public void setDetails(Context ctx, String userName, String userMsg, String msgTime) {

            TextView user_name = (TextView) mView.findViewById(R.id.user_name);
            TextView user_msg = (TextView) mView.findViewById(R.id.user_msg);
            TextView msg_time = (TextView) mView.findViewById(R.id.msg_time);

            user_name.setText(userName);
            user_msg.setText(userMsg);
            msg_time.setText(msgTime);

        }

    }
}
