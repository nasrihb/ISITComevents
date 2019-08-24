package com.example.mongodb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mongodb.Models.Event;
import com.example.mongodb.Models.User;
import com.example.mongodb.Retrofit.IMyService;
import com.example.mongodb.Retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePassword extends AppCompatActivity {

    EditText  new_password,current_password,re_new_password;
    Button  change,cancel;
    TextView txterror;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    String email = Login.id;
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed(); // pas indispensable
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        //init view
        new_password = (EditText) findViewById(R.id.newpass);
        txterror = (TextView) findViewById(R.id.error);
        current_password = (EditText) findViewById(R.id.currentpass);
        re_new_password = (EditText) findViewById(R.id.rnewpass);
        change = (Button) findViewById(R.id.changepass);
        cancel = (Button) findViewById(R.id.cancel);
        ImageView retour = (ImageView)findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(ChangePassword.this,ChangePassword.class);
                startActivity(cancel);
            }
        });

            change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(email,current_password.getText().toString(),new_password.getText().toString());

            }
        });
    }

    private void changePassword(String email, String cpass, String npass) {

        if(npass.equals(re_new_password.getText().toString())) {
            txterror.setText("");
            compositeDisposable.add(iMyService.changePassword(email, cpass, npass)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(final String response) throws Exception {
                            Toast.makeText(ChangePassword.this, response, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    })
            );}
           else {
           /* Toast.makeText(ChangePassword.this, "Password do not match", Toast.LENGTH_SHORT).show();
            return;}*/
           txterror.setText("Password do not match");}
        }



}
