package com.example.ad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.ad.Result.end;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class LogInActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 123;
    FirebaseUser firebaseUser;
    List<AuthUI.IdpConfig> providers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),//email register
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        showSignInOption();

    }

    private void showSignInOption() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder() //intent to the sign in FIrebase register page
                        .setAvailableProviders(providers) //set the provider array into register page
                        .setTheme(R.style.AppTheme).build(), MY_REQUEST_CODE // set the color background, and set the request code

        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data); //
            if (resultCode == RESULT_OK) {
                //get user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // show email on toast
                Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();
                // SignOutButton.setEnabled(true);
                Intent LoginIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(LoginIntent);
                finish();

            } else {
                Toast.makeText(this, "" + response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * get the firebase current user
     * if the user login before will skip the login process
     */

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("Onstart:", "mainact");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            // If user is already logged in upon opening, go to home intent
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else {
        }


    }
}
