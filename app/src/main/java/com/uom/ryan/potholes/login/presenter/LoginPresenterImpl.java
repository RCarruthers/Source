package com.uom.ryan.potholes.login.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uom.ryan.potholes.login.view.LoginView;

import static com.uom.ryan.potholes.util.Constants.TOAST_REGISTRATION_FAILURE;
import static com.uom.ryan.potholes.util.Constants.TOAST_REGISTRATION_SUCCESSFUL;
import static com.uom.ryan.potholes.util.Constants.TOAST_SIGN_IN_FAILURE;

/**
 * Created by Ryan on 03/11/2017.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView view;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference database;

    public LoginPresenterImpl(LoginView v) {
        this.view = v;
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null) {
                    // User signed in.
                } else {
                    // User signed out.
                }
            }
        };
    }

    @Override
    public void handleLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) view, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            view.navigateToSourceActivity();
                        } else {
                            Toast.makeText((Activity) view,
                                    TOAST_SIGN_IN_FAILURE + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void handleRegister(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) view, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            addNewUserToDatabase(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            Toast.makeText((Activity) view, TOAST_REGISTRATION_SUCCESSFUL, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText((Activity) view,
                                    TOAST_REGISTRATION_FAILURE + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void addNewUserToDatabase(String uid, String email){
        database = FirebaseDatabase.getInstance().getReference("users").child(uid);
        database.child("email").setValue(email);
        database.child("displayName").setValue("John Smith");
        database.child("reportType").setValue("Potholes");

    }

    @Override
    public void checkLoginStatus() {
        if(mAuth.getCurrentUser() != null)
            view.navigateToSourceActivity();
    }


}
