package com.uom.ryan.potholes.application.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ryan on 29/01/2018.
 */

// Stores user information to avoid frequent database pulls
public class User {
    protected String uid;
    protected String reportType;
    protected String email;
    private FirebaseUser user;

    public User() {
        pullUserInfoFromFirebase();
    }

    public void pullUserInfoFromFirebase() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        this.uid = user.getUid();
        this.email = user.getEmail();
        readReportType();
    }

    public void readReportType() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users")
                .child(user.getUid()).child("reportType");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reportType = dataSnapshot.getValue(String.class);
                System.out.println("Report type: " + reportType);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public String getUid() {return uid;}

    public String getReportType() {
        return reportType;
    }

    public String getEmail() {
        return email;
    }


}
