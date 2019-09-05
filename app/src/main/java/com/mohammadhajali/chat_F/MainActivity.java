package com.mohammadhajali.chat_F;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohammadhajali.lost_thing1.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private TapsAccessorAdapter myTabsAccessorAdapter;

    private FirebaseUser CurrentUser;

    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();


        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Lost Thing");

        myViewPager = findViewById(R.id.main_tabs_pager);
        myTabsAccessorAdapter = new TapsAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessorAdapter);

        myTabLayout = findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);

        CurrentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //FirebaseUser CurrentUser =mAuth.getCurrentUser();

        if (CurrentUser == null){
             SendUserToLoginActivity();
        }
        else{

            VerifyUserExistance();
        }
    }

    private void VerifyUserExistance() {


        String currentUserID = mAuth.getCurrentUser().getUid();
        RootRef.child(currentUserID).addValueEventListener(new ValueEventListener() {   // child("users")  "14"
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if ((dataSnapshot.child("name").exists())){
                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                }
                else{

                    //SendUserToSettingsActivity();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_logout_option){

            //updateUserStatus("offline");
            mAuth.signOut();
            SendUserToLoginActivity();
            //SendUserTopostActivity();

        }
        if (item.getItemId() == R.id.main_settings_option){

            //x = FirebaseInstanceId.getInstance().getToken();

            //Log.e(TAG,"Token is :" + x);

            SendUserToSettingsActivity();

        }
        if (item.getItemId() == R.id.main_create_group_option){

            // RequestNewGroup();
        }
        if (item.getItemId() == R.id.main_find_frinds_option){

            SendUserToFindFriendsActivity();
        }
        return true;
    }

    private void SendUserToFindFriendsActivity() {

        Intent FindFriendsIntent = new Intent(MainActivity.this,FindFriendsActivity.class);
        startActivity(FindFriendsIntent);

    }


    private void SendUserToLoginActivity() {

        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    private void SendUserToSettingsActivity() {
        Intent SettingsIntent = new Intent(MainActivity.this, SettingActivity.class);
        SettingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);   //put "14" remove  "43"
        startActivity(SettingsIntent);
        finish();
    }
}
