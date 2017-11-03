package com.uom.ryan.potholes.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uom.ryan.potholes.R;
import com.uom.ryan.potholes.application.view.SourceActivity;
import com.uom.ryan.potholes.login.presenter.LoginPresenter;
import com.uom.ryan.potholes.login.presenter.LoginPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener, LoginView {

    @BindView(R.id.btnSignIn)
    Button buttonSignIn;

    @BindView(R.id.btnRegister)
    Button buttonRegister;

    @BindView(R.id.emailEditText)
    EditText emailEditText;

    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setContentView(R.layout.activity_login_main);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenterImpl(this);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLoginButtonClicked();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegisterButtonClicked();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Button clicked.", Toast.LENGTH_LONG).show();
        if(v == buttonSignIn)
            handleLoginButtonClicked();
        else if(v == buttonRegister)
            handleRegisterButtonClicked();
    }


    public void handleLoginButtonClicked() {
        String emailText = emailEditText.getText().toString().trim();
        String passwordText = passwordEditText.getText().toString().trim();

        if((!TextUtils.isEmpty(emailText)) && (!TextUtils.isEmpty(passwordText))) {
            loginPresenter.handleLogin(emailText, passwordText);
        } else {
            Toast.makeText(this, "Please enter both your email address and password.", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void handleRegisterButtonClicked() {
        String emailText = emailEditText.getText().toString().trim();
        String passwordText = passwordEditText.getText().toString().trim();

        if((!TextUtils.isEmpty(emailText)) && (!TextUtils.isEmpty(passwordText))) {
            loginPresenter.handleRegister(emailText, passwordText);
        } else {
            Toast.makeText(this, "Please enter both your email address and password.", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void navigateToSourceActivity() {
        Intent intent = new Intent(getBaseContext(), SourceActivity.class);
        startActivity(intent);
    }
}
