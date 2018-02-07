package com.uom.ryan.potholes.application.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uom.ryan.potholes.R;

public class ProfileActivity extends AppCompatActivity {

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String id = database.push().getKey();
        setContentView(R.layout.activity_profile);
        getAndSetTextNameValues();
        getAndSetEmailValues();
        setSpinnerValues();
    }

    public void setSpinnerValues() {
        Spinner spinner = (Spinner) findViewById(R.id.dynamic_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                database.child("users").child(user.getUid()).child("reportType")
                        .setValue(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    public void getAndSetTextNameValues() {
        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("displayName");
        final EditText editTextName = (EditText) findViewById(R.id.profileEditTextName);

        // Get
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                editTextName.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        // Set
        editTextName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    database.child("users").child(user.getUid()).child("displayName")
                            .setValue(editTextName.getText().toString());
                }
            }
        });
    }

    public void getAndSetEmailValues() {
        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("email");
        final EditText editTextEmail = (EditText) findViewById(R.id.profileEditTextEmail);

        // Get
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                editTextEmail.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        //Set
        System.out.println("test" + editTextEmail.getText());
        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    database.child("users").child(user.getUid()).child("email")
                            .setValue(editTextEmail.getText().toString());
                }
            }
        });
    }
}
