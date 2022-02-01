package com.college.supermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.college.supermarket.domain.User;
import com.college.supermarket.service.UserService;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.purple));
    }
    public void onBack(View view){
        Intent intent;
        intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    public void onReg(View view){
        UserService userService = null;
        try {
            userService = new UserService(this);
            User newUser = new User();
            EditText editTextPhone = (EditText) findViewById(R.id.edit_phone);
            EditText editTextPassword = (EditText) findViewById(R.id.edit_password);
            EditText editTextName = (EditText) findViewById(R.id.edit_name);
            EditText editTextLastName = (EditText) findViewById(R.id.edit_lastname);
            newUser.setPassword(editTextPassword.getText().toString());
            newUser.setNumberPhone(editTextPhone.getText().toString());
            newUser.setFirstName(editTextName.getText().toString());
            newUser.setLastName(editTextLastName.getText().toString());
            newUser.setCardCode(String.valueOf(getCode()));
            userService.create(newUser);

        } catch (Exception e) {
            e.printStackTrace();
        }
        onBack(view);
    }

    public int getCode()
    {
        int min = 100000000;
        int max = 999999999;
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

}