package com.example.lmont.adventurecreator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;

public class LoginActivity extends AppCompatActivity {

    APIHelper apiHelper;
    EditText userNameEditText;
    EditText passwordEditText;
    Button submitButton;
    ProgressBar progressBar;
    CheckBox newAccountCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setup();
    }

    private void setup() {
        apiHelper = APIHelper.getInstance(this);
        userNameEditText = (EditText) findViewById(R.id.login_username_edittext);
        passwordEditText = (EditText) findViewById(R.id.login_password_edittext);
        submitButton = (Button) findViewById(R.id.login_submit_button);
        progressBar = (ProgressBar) findViewById(R.id.login_progressbar);
        newAccountCheckBox = (CheckBox) findViewById(R.id.login_newaccount_checkbox);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                final String username = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.contains(" ") || password.contains(" ")) {
                    Toast.makeText(LoginActivity.this, "No spaces allowed", Toast.LENGTH_SHORT).show();
                    hideLoading();
                    return;
                }

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    hideLoading();
                    return;
                }

                if (newAccountCheckBox.isChecked()) {
                    createNewAccount(username, password);
                    return;
                } else {
                    checkLoginCredentials(username, password);
                    return;
                }
            }
        });
    }

    private void login(String username) {
        Player.getInstance().setUsername(username);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void checkLoginCredentials(final String username, String password) {
        apiHelper.checkLoginCredentials(username, password, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                if (Boolean.valueOf(response.toString())) {
                    login(username);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                    hideLoading();
                }
            }
        }, null);
    }

    private void createNewAccount(final String username, String password) {
        apiHelper.addNewAccount(username, password, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                if (response) {
                    login(username);
                } else {
                    hideLoading();
                    Toast.makeText(LoginActivity.this, "That name is not available", Toast.LENGTH_SHORT).show();
                }
            }
        }, null);
    }

    private void showLoading() {
        userNameEditText.setVisibility(View.GONE);
        passwordEditText.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);
        newAccountCheckBox.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        userNameEditText.setVisibility(View.VISIBLE);
        passwordEditText.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.VISIBLE);
        newAccountCheckBox.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
