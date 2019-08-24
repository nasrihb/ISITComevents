package com.example.mongodb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mongodb.Models.Event;
import com.example.mongodb.Models.User;
import com.example.mongodb.Retrofit.IMyService;
import com.example.mongodb.Retrofit.RetrofitClient;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class change_phone extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    String id_user = Login.id;
    EditText number,phone;
    Button addphone ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);

        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        //init view
        ImageView retour = (ImageView)findViewById(R.id.retourphone);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        number =(EditText) findViewById(R.id.phone);
        number.setEnabled(false);
        getPhone();

        addphone = (Button) findViewById(R.id.add_phone);
        addphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View addphone = LayoutInflater.from(change_phone.this)
                        .inflate(R.layout.activity_add_phone,null);
                new MaterialStyledDialog.Builder(change_phone.this)
                        .setIcon(R.drawable.add_phone)
                        .setCustomView(addphone)
                        .setNegativeText("Cancel")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                       .setPositiveText("Add")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                EditText pass = (EditText)addphone.findViewById(R.id.password);
                                EditText phone = (EditText)addphone.findViewById(R.id.phone);
                                updatephone(id_user,pass.getText().toString(),phone.getText().toString());
                            }
                        })
                        .show();
            }
        });

    }

    private void updatephone(String id_user, String pass, String phone) {
        compositeDisposable.add(iMyService.changePhone(id_user,pass,phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String response) throws Exception {
                        Intent i  = new Intent(change_phone.this,change_phone.class);
                        startActivity(i);
                    }
                })
        );
    }

    private void getPhone() {
        Call<List<User>> call= iMyService.getuser(id_user);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(change_phone.this, "Code : " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<User> users = response.body();
                for (User user : users)
                {
                    number.setText(user.getTel());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.i("Msg",t.getMessage());
            }
        });
    }


}
