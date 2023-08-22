package com.example.restaurantinventorysystem.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.restaurantinventorysystem.MainActivity;
import com.example.restaurantinventorysystem.R;
import com.example.restaurantinventorysystem.productList.AddItemsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText edtTxtPassword,edtTxtEmail, edtTxtEstablishment;
    Button btnRegister;
    FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();
        edtTxtPassword=findViewById(R.id.editTxtPassword);
        edtTxtEmail=findViewById(R.id.editTxtEmail);
        edtTxtEstablishment=findViewById(R.id.editTxtEstablishment);
        progressBar=findViewById(R.id.progressBar);
        btnRegister=findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email,password,establishment;
                password = String.valueOf(edtTxtPassword.getText());
                email = String.valueOf(edtTxtEmail.getText());
                establishment= String.valueOf(edtTxtEstablishment.getText());


                if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)||TextUtils.isEmpty(establishment)){
                    Toast.makeText(RegisterActivity.this, "Field cannot be left empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseDatabase.getInstance("https://restaurant-inventory-sys-5026b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference()
                        .child("Establishment").child("Name").setValue(establishment);



                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Account Created!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });
    }

    public void goToLogin(View view){
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}