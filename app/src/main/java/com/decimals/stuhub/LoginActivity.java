package com.decimals.stuhub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by SUNIL KUMAR KESHRI on 3/30/2018.
 */

public class LoginActivity extends AppCompatActivity {

    private final int RC_SIGN_IN = 2;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        // check out current user
        user = firebaseAuth.getCurrentUser();


        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button_facebook);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Log.d("fb :", "facebook:onSuccess:" + loginResult);
                Toast.makeText(LoginActivity.this, "Logging you in, Please wait !", Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("fb", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("fb", "facebook:onError", error);
                // ...
            }
        });

// ...


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Google SignIn
        findViewById(R.id.sign_in_button_Google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (user != null) {
            finish();
            // goto HomeActivity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        }

    }

    // google sign-in activity
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                Toast.makeText(LoginActivity.this, "Logging you in, Please wait !", Toast.LENGTH_SHORT).show();
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

                // Google Sign In failed, update UI appropriately
                Log.w("Google Auth :", "Google sign in failed", e);
                // ...

            }
        }
    }


    // google
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("Auth", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {


                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("Auth Credential :", "signInWithCredential:success");
//                            Toast.makeText(LoginActivity.this,"Logging you in, Please wait !",Toast.LENGTH_SHORT).show();

                            //finish LoginActivity goto HomeActivity
                            finish();

                            //  start HomeActivity here after SignIn Successful
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Auth :", "signInWithCredential:failure", task.getException());

                            // if SignIn failure user will remain in LoginActivity
                            Toast.makeText(LoginActivity.this, "User Sign Failure.. Plz try again..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }

                        // ...
                    }
                });
    }

    // facebook
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("fb :", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            // goto HomeActivity
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("fb :", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }

                    }
                });
    }


}
