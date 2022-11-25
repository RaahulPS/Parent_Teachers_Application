package com.example.sih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class fac_profile extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Button addstud;
    EditText student_name,roll_no,grade1,parent_name,phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_profile);
        drawerLayout=findViewById(R.id.drawer_layout1);
        addstud= findViewById(R.id.button_add);
        student_name=findViewById(R.id.stud_name);
        roll_no=findViewById(R.id.stud_roll);
        grade1=findViewById(R.id.stud_grade);
        parent_name=findViewById(R.id.stud_par_name);
        phone_number=findViewById(R.id.stud_par_phone);
        addstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("student name", student_name.getText().toString());
                user.put("Roll no", roll_no.getText().toString());
                user.put("grade", grade1.getText().toString());
                user.put("parent name", parent_name.getText().toString());
                user.put("phone number", phone_number.getText().toString());

// Add a new document with a generated ID
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("students")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("student", "DocumentSnapshot added with ID: " + documentReference.getId());
                                student_name.setText("");
                                roll_no.setText("");
                                grade1.setText("");
                                parent_name.setText("");
                                phone_number.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("student", "Error adding document", e);
                            }
                        });


            }
        });
    }
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }
    public void ClickHome(View view){ redirectActivity(this,grade1_home.class);

    }
    public void ClickDashboard(View view){ redirectActivity(this,fac_notice.class);
    }
    public void ClickAboutUs(View view){ redirectActivity(this,fac_complaint.class);
    }
    public void ClickProfile(View view) {recreate();}
    public void ClickParregristration(View view){redirectActivity(this, par_registration.class);}
    public void ClickLogout(View view){
        logout(this);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure want to logout?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public   static void redirectActivity(Activity activity, Class aClass) {
        Intent intent= new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }

}