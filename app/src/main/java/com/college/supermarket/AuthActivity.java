package com.college.supermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.college.supermarket.domain.Product;
import com.college.supermarket.domain.User;
import com.college.supermarket.service.ProductService;
import com.college.supermarket.service.UserService;

public class AuthActivity extends AppCompatActivity {
    public static User userActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.purple));
    }

    public void onAuth(View view){
        User user = new User();
        Product product = new Product();
        product.setCodeProduct(870926875);
        product.setNameProduct("Томат");
        product.setPriceProduct(155.0);

        UserService userService = null;
        try {
            userService = new UserService(this);
            ProductService productService = new ProductService(this);
            productService.create(product);

        } catch (Exception e) {
            e.printStackTrace();
        }
        EditText editTextLogin = (EditText) findViewById(R.id.edit_username_auth);
        user.setNumberPhone(editTextLogin.getText().toString());
        EditText editTextPassword = (EditText) findViewById(R.id.edit_password_auth);
        user.setPassword(editTextPassword.getText().toString());
        try {
            if (userService.getUser(user)){
                Intent intent;
                intent = new Intent(this, UserActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onReg(View view){
        Intent intent;
        intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}