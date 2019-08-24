package com.example.mongodb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mongodb.Retrofit.IMyService;
import com.example.mongodb.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class changeEmail extends AppCompatActivity {

    EditText new_email;
    Button add_email;
    TextView mail;
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
        setContentView(R.layout.activity_change_email);

        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        //init view
        new_email = (EditText) findViewById(R.id.new_email);
        mail = (TextView) findViewById(R.id.txt_email);
        mail.setText(email);


        add_email = (Button) findViewById(R.id.add_email);
        ImageView retour = (ImageView)findViewById(R.id.retouremail);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEmail(email,new_email.getText().toString());
                finish();
            }
        });
    }

    private void AddEmail(String email, String email2) {
        if (TextUtils.isEmpty(email2)) {
            Toast.makeText(changeEmail.this, "Email cannont be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }
        compositeDisposable.add(iMyService.updateEmail(email,email2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String response) throws Exception {
                        Toast.makeText(changeEmail.this, response, Toast.LENGTH_SHORT).show();
                        return;
                    }
                })
        );
    }
}
