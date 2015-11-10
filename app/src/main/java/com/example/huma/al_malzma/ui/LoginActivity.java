package com.example.huma.al_malzma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.helper.Utility;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.rey.material.widget.ProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.name_edit_text) EditText mNameEditText;
    @Bind(R.id.password_edit_text) EditText mPasswordEditText;
    @Bind(R.id.signup_text_view) TextView mSignupTextView;
    @Bind(R.id.login_button) Button mLoginButton;
    @Bind(R.id.progress_view) ProgressView mProgressView;


    String mName, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //go to SignupActivity.
        mSignupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        //Start Login proses.
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = mNameEditText.getText().toString().trim().toLowerCase();
                mPassword = mPasswordEditText.getText().toString().trim().toLowerCase();

                //if user leave any thing empty show him AlertDialog.
                if (mName.isEmpty() || mPassword.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle(getString(R.string.generic_error_title))
                            .setMessage(R.string.signup_error_message)
                            .setPositiveButton(android.R.string.ok, null)
                            .create().show();
                }
                //send the data to parse. and if Login information is correct
                //take the user to MainActivity.
                else {
                    if (Utility.isNetworkAvailable(LoginActivity.this)) {
                        mProgressView.setVisibility(View.VISIBLE);

                        ParseUser.logInInBackground(mName, mPassword, new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                mProgressView.setVisibility(View.INVISIBLE);

                                if (e == null) {
                                    // Hooray! Let them use the app now.
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    // Sign up didn't succeed. Look at the ParseException to figure out what went wrong
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setTitle(getString(R.string.generic_error_title))
                                            .setMessage(e.getMessage())
                                            .setPositiveButton(android.R.string.ok, null)
                                            .create().show();
                                }
                            }
                        });
                    } else {
                        //no network.
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle(getString(R.string.generic_error_title))
                                .setMessage(getString(R.string.connection_error))
                                .setPositiveButton(android.R.string.ok, null)
                                .create().show();
                    }
                }
            }
        });

    }

}
